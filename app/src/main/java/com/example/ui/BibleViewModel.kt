package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BibleDataProvider
import com.example.data.local.AppDatabase
import com.example.data.local.LocalBookmark
import com.example.data.local.LocalReadingProgress
import com.example.data.local.LocalUserStreak
import com.example.data.remote.FirestoreRepository
import com.example.model.BibleBook
import com.example.model.BibleVerse
import com.example.model.FirestoreBookmark
import com.example.model.FirestoreReadingPlan
import com.example.model.FirestoreUserProfile
import com.example.model.ReadingMode
import com.example.model.ReadingPlan
import com.example.model.ReadingTheme
import com.example.model.Testament
import com.example.util.BibleTtsManager
import com.example.util.ReminderScheduler
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BibleViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val bookmarkDao = db.bookmarkDao()
    private val readingPlanDao = db.readingPlanDao()
    private val streakDao = db.userStreakDao()
    private val ttsManager = BibleTtsManager(application)

    private val auth: FirebaseAuth = Firebase.auth
    private var firestoreRepo: FirestoreRepository? = null

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    // Navigation & Reading State - Default is INTERLINEAR (Setswana English Verse-by-Verse)
    private val _currentBook = MutableStateFlow(BibleDataProvider.books.first { it.id == "psa" })
    val currentBook: StateFlow<BibleBook> = _currentBook.asStateFlow()

    private val _currentChapter = MutableStateFlow(23)
    val currentChapter: StateFlow<Int> = _currentChapter.asStateFlow()

    private val _verses = MutableStateFlow<List<BibleVerse>>(emptyList())
    val verses: StateFlow<List<BibleVerse>> = _verses.asStateFlow()

    private val _readingMode = MutableStateFlow(ReadingMode.VERSE_BY_VERSE)
    val readingMode: StateFlow<ReadingMode> = _readingMode.asStateFlow()

    private val _fontSizeSp = MutableStateFlow(18f)
    val fontSizeSp: StateFlow<Float> = _fontSizeSp.asStateFlow()

    private val _readingTheme = MutableStateFlow(ReadingTheme.PARCHMENT)
    val readingTheme: StateFlow<ReadingTheme> = _readingTheme.asStateFlow()

    // Search State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<BibleVerse>>(emptyList())
    val searchResults: StateFlow<List<BibleVerse>> = _searchResults.asStateFlow()

    private val _searchFilter = MutableStateFlow<Testament?>(null)
    val searchFilter: StateFlow<Testament?> = _searchFilter.asStateFlow()

    // Bookmarks and Highlights
    val localBookmarks: StateFlow<List<LocalBookmark>> = bookmarkDao.observeAllBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val highlightsMap: StateFlow<Map<String, String>> = localBookmarks.map { list ->
        list.associate { "${it.bookId}_${it.chapter}_${it.verse}" to it.tagColor }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    // Reading Plans
    val readingPlans: List<ReadingPlan> = BibleDataProvider.readingPlans

    val planProgressList: StateFlow<List<LocalReadingProgress>> = readingPlanDao.observeAllPlans()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Streak and Goals
    private val _userStreak = MutableStateFlow(
        LocalUserStreak(
            userId = "local_user",
            currentStreak = 4,
            longestStreak = 7,
            lastActiveDate = getTodayDateString(),
            dailyGoalVerses = 10,
            versesReadToday = 6
        )
    )
    val userStreak: StateFlow<LocalUserStreak> = _userStreak.asStateFlow()

    // Cloud Sync State
    private val _syncStatus = MutableStateFlow("Offline Mode (Local Storage Active)")
    val syncStatus: StateFlow<String> = _syncStatus.asStateFlow()

    // Selected verse for action / preview
    private val _selectedVerseForAction = MutableStateFlow<BibleVerse?>(null)
    val selectedVerseForAction: StateFlow<BibleVerse?> = _selectedVerseForAction.asStateFlow()

    private val _previewVerse = MutableStateFlow<BibleVerse?>(null)
    val previewVerse: StateFlow<BibleVerse?> = _previewVerse.asStateFlow()

    // Audio & Voice State
    val isTtsPlaying: StateFlow<Boolean> = ttsManager.isPlaying
    val isTtsPaused: StateFlow<Boolean> = ttsManager.isPaused
    val currentTtsIndex: StateFlow<Int?> = ttsManager.currentPlayingIndex
    val ttsVoiceLanguage: StateFlow<String> = ttsManager.currentVoiceLanguage
    val ttsSpeechRate: StateFlow<Float> = ttsManager.speechRate

    private var isContinuousChapterReading = false
    private var continuousVoiceLanguageSetswana = true

    private val _isReminderEnabled = MutableStateFlow(true)
    val isReminderEnabled: StateFlow<Boolean> = _isReminderEnabled.asStateFlow()

    private val _reminderHour = MutableStateFlow(7)
    val reminderHour: StateFlow<Int> = _reminderHour.asStateFlow()

    private val _reminderMinute = MutableStateFlow(0)
    val reminderMinute: StateFlow<Int> = _reminderMinute.asStateFlow()

    init {
        loadChapterVerses(_currentBook.value.id, _currentChapter.value)
        setupAuthStateListener()
        loadLocalStreak()

        // Auto-advance verse playback during continuous chapter reading
        ttsManager.onVerseCompletedListener = { completedIndex ->
            if (isContinuousChapterReading) {
                val nextIndex = completedIndex + 1
                val allVerses = _verses.value
                if (nextIndex < allVerses.size) {
                    val nextVerse = allVerses[nextIndex]
                    val textToSpeak = if (continuousVoiceLanguageSetswana) nextVerse.textSetswana else nextVerse.textEnglish
                    ttsManager.speak(textToSpeak, isSetswana = continuousVoiceLanguageSetswana, index = nextIndex)
                } else {
                    isContinuousChapterReading = false
                }
            }
        }
    }

    private fun setupAuthStateListener() {
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            _currentUser.value = user
            if (user != null) {
                firestoreRepo = FirestoreRepository(getApplication())
                _syncStatus.value = "Cloud Sync Enabled: ${user.displayName ?: user.email ?: "Account Connected"}"
                syncWithCloud()
            } else {
                firestoreRepo = null
                _syncStatus.value = "Offline Mode (Local Storage Active)"
            }
        }
    }

    private fun loadLocalStreak() {
        viewModelScope.launch {
            val uid = _currentUser.value?.uid ?: "local_user"
            val stored = streakDao.getStreak(uid)
            if (stored != null) {
                _userStreak.value = stored
            } else {
                val initial = LocalUserStreak(
                    userId = uid,
                    currentStreak = 3,
                    longestStreak = 7,
                    lastActiveDate = getTodayDateString(),
                    dailyGoalVerses = 10,
                    versesReadToday = 4
                )
                streakDao.insertOrUpdateStreak(initial)
                _userStreak.value = initial
            }
        }
    }

    fun selectBookAndChapter(book: BibleBook, chapter: Int) {
        stopTts()
        _currentBook.value = book
        _currentChapter.value = chapter.coerceIn(1, book.chaptersCount)
        loadChapterVerses(book.id, _currentChapter.value)
        recordChapterRead()
    }

    fun nextChapter() {
        stopTts()
        val current = _currentChapter.value
        val book = _currentBook.value
        if (current < book.chaptersCount) {
            selectBookAndChapter(book, current + 1)
        } else {
            val nextBookIndex = BibleDataProvider.books.indexOf(book) + 1
            if (nextBookIndex < BibleDataProvider.books.size) {
                val nextBook = BibleDataProvider.books[nextBookIndex]
                selectBookAndChapter(nextBook, 1)
            }
        }
    }

    fun previousChapter() {
        stopTts()
        val current = _currentChapter.value
        val book = _currentBook.value
        if (current > 1) {
            selectBookAndChapter(book, current - 1)
        } else {
            val prevBookIndex = BibleDataProvider.books.indexOf(book) - 1
            if (prevBookIndex >= 0) {
                val prevBook = BibleDataProvider.books[prevBookIndex]
                selectBookAndChapter(prevBook, prevBook.chaptersCount)
            }
        }
    }

    private fun loadChapterVerses(bookId: String, chapter: Int) {
        _verses.value = BibleDataProvider.getVersesForChapter(bookId, chapter)
    }

    fun setReadingMode(mode: ReadingMode) {
        _readingMode.value = mode
    }

    fun setFontSize(sizeSp: Float) {
        _fontSizeSp.value = sizeSp.coerceIn(14f, 32f)
    }

    fun setReadingTheme(theme: ReadingTheme) {
        _readingTheme.value = theme
    }

    fun search(query: String, filter: Testament? = null, bookId: String? = null) {
        _searchQuery.value = query
        _searchFilter.value = filter
        if (query.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = BibleDataProvider.searchVerses(query, filter, bookId)
        }
    }

    fun openVerseActionSheet(verse: BibleVerse) {
        _selectedVerseForAction.value = verse
    }

    fun closeVerseActionSheet() {
        _selectedVerseForAction.value = null
    }

    fun setPreviewVerse(verse: BibleVerse?) {
        _previewVerse.value = verse
    }

    fun quickToggleHighlight(verse: BibleVerse, tagColor: String) {
        val key = "${verse.bookId}_${verse.chapter}_${verse.verse}"
        val currentTag = highlightsMap.value[key]
        if (currentTag == tagColor) {
            // Remove highlight
            val id = "bm_${verse.bookId}_${verse.chapter}_${verse.verse}"
            deleteBookmark(id)
        } else {
            // Apply highlight
            saveBookmark(verse, tagColor = tagColor, note = null)
        }
    }

    fun saveBookmark(verse: BibleVerse, tagColor: String = "yellow", note: String? = null) {
        viewModelScope.launch {
            val uid = _currentUser.value?.uid ?: "local_user"
            val id = "bm_${verse.bookId}_${verse.chapter}_${verse.verse}"
            val local = LocalBookmark(
                id = id,
                userId = uid,
                bookId = verse.bookId,
                bookNameSetswana = verse.bookNameSetswana,
                bookNameEnglish = verse.bookNameEnglish,
                chapter = verse.chapter,
                verse = verse.verse,
                textSetswana = verse.textSetswana,
                textEnglish = verse.textEnglish,
                tagColor = tagColor,
                note = note,
                createdAtMillis = System.currentTimeMillis(),
                isSynced = false
            )
            bookmarkDao.insertBookmark(local)

            val repo = firestoreRepo
            if (repo != null && _currentUser.value != null) {
                try {
                    repo.saveBookmark(
                        FirestoreBookmark(
                            id = id,
                            userId = uid,
                            bookId = verse.bookId,
                            bookNameSetswana = verse.bookNameSetswana,
                            bookNameEnglish = verse.bookNameEnglish,
                            chapter = verse.chapter.toLong(),
                            verse = verse.verse.toLong(),
                            textSetswana = verse.textSetswana,
                            textEnglish = verse.textEnglish,
                            tagColor = tagColor,
                            note = note
                        )
                    )
                    bookmarkDao.markAsSynced(id)
                } catch (e: Exception) {
                    // Cached locally
                }
            }
        }
    }

    fun deleteBookmark(id: String) {
        viewModelScope.launch {
            bookmarkDao.deleteBookmarkById(id)
            val repo = firestoreRepo
            if (repo != null && _currentUser.value != null) {
                try {
                    repo.deleteBookmark(id)
                } catch (e: Exception) {
                    // Cached deletion
                }
            }
        }
    }

    fun markPlanDayCompleted(planId: String, dayNumber: Int) {
        viewModelScope.launch {
            val uid = _currentUser.value?.uid ?: "local_user"
            val existingProgress = planProgressList.value.find { it.planId == planId }
            val completedList = existingProgress?.completedDayIndexesCsv
                ?.split(",")
                ?.filter { it.isNotBlank() }
                ?.mapNotNull { it.toIntOrNull() }
                ?.toMutableSet() ?: mutableSetOf()

            completedList.add(dayNumber)
            val total = existingProgress?.totalDays ?: 30
            val newProgress = LocalReadingProgress(
                planId = planId,
                userId = uid,
                planType = planId,
                dayCompletedCount = completedList.size,
                totalDays = total,
                completedDayIndexesCsv = completedList.joinToString(","),
                lastReadDate = getTodayDateString(),
                isSynced = false
            )
            readingPlanDao.insertOrUpdate(newProgress)
            recordVerseRead(5)

            val repo = firestoreRepo
            if (repo != null && _currentUser.value != null) {
                try {
                    repo.saveReadingPlan(
                        FirestoreReadingPlan(
                            id = planId,
                            userId = uid,
                            planType = planId,
                            dayCompletedCount = completedList.size.toLong(),
                            totalDays = total.toLong(),
                            completedDayIndexes = completedList.map { it.toLong() },
                            lastReadDate = getTodayDateString()
                        )
                    )
                } catch (e: Exception) {
                    // Saved locally
                }
            }
        }
    }

    private fun recordChapterRead() {
        recordVerseRead(10)
    }

    fun recordVerseRead(versesCount: Int = 1) {
        viewModelScope.launch {
            val current = _userStreak.value
            val today = getTodayDateString()
            val newVersesToday = if (current.lastActiveDate == today) {
                current.versesReadToday + versesCount
            } else {
                versesCount
            }

            val newStreak = if (current.lastActiveDate != today) {
                current.currentStreak + 1
            } else {
                current.currentStreak
            }

            val longest = maxOf(newStreak, current.longestStreak)
            val updated = current.copy(
                currentStreak = newStreak,
                longestStreak = longest,
                lastActiveDate = today,
                versesReadToday = newVersesToday
            )
            _userStreak.value = updated
            streakDao.insertOrUpdateStreak(updated)

            val repo = firestoreRepo
            if (repo != null && _currentUser.value != null) {
                try {
                    repo.saveUserProfile(
                        FirestoreUserProfile(
                            userId = updated.userId,
                            displayName = _currentUser.value?.displayName ?: "Mmadikwalo",
                            currentStreak = updated.currentStreak.toLong(),
                            longestStreak = updated.longestStreak.toLong(),
                            lastActiveDate = updated.lastActiveDate,
                            dailyGoalVerses = updated.dailyGoalVerses.toLong()
                        )
                    )
                } catch (e: Exception) {
                    // Sync failed silently
                }
            }
        }
    }

    fun syncWithCloud() {
        viewModelScope.launch {
            val repo = firestoreRepo
            val user = _currentUser.value
            if (repo == null || user == null) {
                _syncStatus.value = "Offline Mode (Local Storage Active)"
                return@launch
            }

            _syncStatus.value = "Syncing with Cloud..."
            try {
                val unsynced = bookmarkDao.getUnsyncedBookmarks()
                for (bm in unsynced) {
                    repo.saveBookmark(
                        FirestoreBookmark(
                            id = bm.id,
                            userId = user.uid,
                            bookId = bm.bookId,
                            bookNameSetswana = bm.bookNameSetswana,
                            bookNameEnglish = bm.bookNameEnglish,
                            chapter = bm.chapter.toLong(),
                            verse = bm.verse.toLong(),
                            textSetswana = bm.textSetswana,
                            textEnglish = bm.textEnglish,
                            tagColor = bm.tagColor,
                            note = bm.note
                        )
                    )
                    bookmarkDao.markAsSynced(bm.id)
                }

                _syncStatus.value = "Cloud Synced (${SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())})"
            } catch (e: Exception) {
                _syncStatus.value = "Sync Error: ${e.message?.take(30) ?: "Network issue"}"
            }
        }
    }

    // Audio & Voice Functions
    fun playVerseTts(verse: BibleVerse, isSetswana: Boolean = true) {
        isContinuousChapterReading = false
        val vIndex = _verses.value.indexOf(verse)
        val textToSpeak = if (isSetswana) verse.textSetswana else verse.textEnglish
        ttsManager.speak(textToSpeak, isSetswana = isSetswana, index = if (vIndex >= 0) vIndex else null)
    }

    fun playChapterAudio(isSetswana: Boolean = true, startIndex: Int = 0) {
        val allVerses = _verses.value
        if (allVerses.isEmpty()) return
        isContinuousChapterReading = true
        continuousVoiceLanguageSetswana = isSetswana
        val safeIndex = startIndex.coerceIn(0, allVerses.size - 1)
        val targetVerse = allVerses[safeIndex]
        val textToSpeak = if (isSetswana) targetVerse.textSetswana else targetVerse.textEnglish
        ttsManager.speak(textToSpeak, isSetswana = isSetswana, index = safeIndex)
    }

    fun pauseTts() {
        ttsManager.pause()
    }

    fun resumeTts() {
        ttsManager.resume()
    }

    fun togglePlayPauseAudio(isSetswana: Boolean = continuousVoiceLanguageSetswana) {
        if (isTtsPlaying.value) {
            ttsManager.pause()
        } else if (isTtsPaused.value) {
            ttsManager.resume()
        } else {
            val startIdx = currentTtsIndex.value ?: 0
            playChapterAudio(isSetswana, startIdx)
        }
    }

    fun playNextVerseAudio() {
        val allVerses = _verses.value
        if (allVerses.isEmpty()) return
        val current = currentTtsIndex.value ?: 0
        val next = (current + 1).coerceAtMost(allVerses.size - 1)
        val targetVerse = allVerses[next]
        val textToSpeak = if (continuousVoiceLanguageSetswana) targetVerse.textSetswana else targetVerse.textEnglish
        ttsManager.speak(textToSpeak, isSetswana = continuousVoiceLanguageSetswana, index = next)
    }

    fun playPreviousVerseAudio() {
        val allVerses = _verses.value
        if (allVerses.isEmpty()) return
        val current = currentTtsIndex.value ?: 0
        val prev = (current - 1).coerceAtLeast(0)
        val targetVerse = allVerses[prev]
        val textToSpeak = if (continuousVoiceLanguageSetswana) targetVerse.textSetswana else targetVerse.textEnglish
        ttsManager.speak(textToSpeak, isSetswana = continuousVoiceLanguageSetswana, index = prev)
    }

    fun setTtsRate(rate: Float) {
        ttsManager.setSpeechRate(rate)
    }

    fun setTtsVoiceLanguage(isSetswana: Boolean) {
        continuousVoiceLanguageSetswana = isSetswana
        if (isTtsPlaying.value) {
            val idx = currentTtsIndex.value ?: 0
            val allVerses = _verses.value
            if (idx in allVerses.indices) {
                val verse = allVerses[idx]
                val text = if (isSetswana) verse.textSetswana else verse.textEnglish
                ttsManager.speak(text, isSetswana = isSetswana, index = idx)
            }
        }
    }

    fun stopTts() {
        isContinuousChapterReading = false
        ttsManager.stop()
    }

    fun updateReminderSettings(enabled: Boolean, hour: Int, minute: Int) {
        _isReminderEnabled.value = enabled
        _reminderHour.value = hour
        _reminderMinute.value = minute
        if (enabled) {
            ReminderScheduler.scheduleDailyReminder(getApplication(), hour, minute)
        } else {
            ReminderScheduler.cancelReminder(getApplication())
        }
    }

    private fun getTodayDateString(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}
