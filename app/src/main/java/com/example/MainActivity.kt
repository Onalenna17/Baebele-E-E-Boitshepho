package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.BibleVerse
import com.example.model.ReadingMode
import com.example.ui.BibleViewModel
import com.example.ui.components.AccessibilitySettingsDialog
import com.example.ui.components.BibleTopBar
import com.example.ui.components.BookChapterPickerDialog
import com.example.ui.components.GoogleSignInDialog
import com.example.ui.components.ShareVerseDialog
import com.example.ui.components.StudyStreakDialog
import com.example.ui.components.VerseActionSheet
import com.example.ui.components.VersePreviewDialog
import com.example.ui.screens.BookmarksScreen
import com.example.ui.screens.ReadingPlanScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.VerseByVerseBibleReader
import com.example.ui.theme.BaebeleTheme

enum class AppNavTab {
    READER, SEARCH, BOOKMARKS, PLANS
}

class MainActivity : ComponentActivity() {

    private val viewModel: BibleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val readingTheme by viewModel.readingTheme.collectAsStateWithLifecycle()

            BaebeleTheme(readingTheme = readingTheme) {
                BaebeleApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun BaebeleApp(viewModel: BibleViewModel) {
    var currentTab by remember { mutableStateOf(AppNavTab.READER) }

    // Dialog & Sheet States
    var showBookPicker by remember { mutableStateOf(false) }
    var showAccessibilitySettings by remember { mutableStateOf(false) }
    var showStreakDialog by remember { mutableStateOf(false) }
    var showAccountSyncDialog by remember { mutableStateOf(false) }
    var verseToShare by remember { mutableStateOf<BibleVerse?>(null) }

    // State collections
    val currentBook by viewModel.currentBook.collectAsStateWithLifecycle()
    val currentChapter by viewModel.currentChapter.collectAsStateWithLifecycle()
    val verses by viewModel.verses.collectAsStateWithLifecycle()
    val readingMode by viewModel.readingMode.collectAsStateWithLifecycle()
    val fontSizeSp by viewModel.fontSizeSp.collectAsStateWithLifecycle()
    val readingTheme by viewModel.readingTheme.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val bookmarks by viewModel.localBookmarks.collectAsStateWithLifecycle()
    val highlightsMap by viewModel.highlightsMap.collectAsStateWithLifecycle()
    val plans = viewModel.readingPlans
    val planProgress by viewModel.planProgressList.collectAsStateWithLifecycle()
    val userStreak by viewModel.userStreak.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val syncStatus by viewModel.syncStatus.collectAsStateWithLifecycle()
    val selectedVerseForAction by viewModel.selectedVerseForAction.collectAsStateWithLifecycle()
    val previewVerse by viewModel.previewVerse.collectAsStateWithLifecycle()
    val isTtsPlaying by viewModel.isTtsPlaying.collectAsStateWithLifecycle()
    val isTtsPaused by viewModel.isTtsPaused.collectAsStateWithLifecycle()
    val currentTtsIndex by viewModel.currentTtsIndex.collectAsStateWithLifecycle()
    val ttsSpeechRate by viewModel.ttsSpeechRate.collectAsStateWithLifecycle()
    val isReminderEnabled by viewModel.isReminderEnabled.collectAsStateWithLifecycle()
    val reminderHour by viewModel.reminderHour.collectAsStateWithLifecycle()
    val reminderMinute by viewModel.reminderMinute.collectAsStateWithLifecycle()

    BackHandler(enabled = currentTab != AppNavTab.READER) {
        currentTab = AppNavTab.READER
    }

    Scaffold(
        topBar = {
            if (currentTab == AppNavTab.READER) {
                BibleTopBar(
                    currentBook = currentBook,
                    currentChapter = currentChapter,
                    currentUser = currentUser,
                    isTtsPlaying = isTtsPlaying,
                    onOpenBookPicker = { showBookPicker = true },
                    onOpenAccessibilityDialog = { showAccessibilitySettings = true },
                    onPlayAudio = {
                        if (isTtsPlaying) {
                            viewModel.stopTts()
                        } else {
                            viewModel.playChapterAudio(true)
                        }
                    },
                    onOpenSearch = { currentTab = AppNavTab.SEARCH },
                    onOpenAccountSync = { showAccountSyncDialog = true }
                )
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = currentTab == AppNavTab.READER,
                    onClick = { currentTab = AppNavTab.READER },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
                    label = { Text("Padi / Read") },
                    modifier = Modifier.testTag("nav_tab_reader")
                )
                NavigationBarItem(
                    selected = currentTab == AppNavTab.SEARCH,
                    onClick = { currentTab = AppNavTab.SEARCH },
                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                    label = { Text("Batla / Search") },
                    modifier = Modifier.testTag("nav_tab_search")
                )
                NavigationBarItem(
                    selected = currentTab == AppNavTab.BOOKMARKS,
                    onClick = { currentTab = AppNavTab.BOOKMARKS },
                    icon = { Icon(Icons.Default.Bookmark, contentDescription = null) },
                    label = { Text("Ditshwayo") },
                    modifier = Modifier.testTag("nav_tab_bookmarks")
                )
                NavigationBarItem(
                    selected = currentTab == AppNavTab.PLANS,
                    onClick = { currentTab = AppNavTab.PLANS },
                    icon = { Icon(Icons.Default.LocalFireDepartment, contentDescription = null) },
                    label = { Text("Thuto / Plans") },
                    modifier = Modifier.testTag("nav_tab_plans")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppNavTab.READER -> {
                    VerseByVerseBibleReader(
                        book = currentBook,
                        chapter = currentChapter,
                        verses = verses,
                        readingMode = readingMode,
                        fontSizeSp = fontSizeSp,
                        highlightsMap = highlightsMap,
                        isTtsPlaying = isTtsPlaying,
                        isTtsPaused = isTtsPaused,
                        currentTtsIndex = currentTtsIndex,
                        speechRate = ttsSpeechRate,
                        onVerseClick = { v -> viewModel.openVerseActionSheet(v) },
                        onQuickHighlight = { v, tag -> viewModel.quickToggleHighlight(v, tag) },
                        onRemoveHighlight = { v ->
                            val tag = highlightsMap["${v.bookId}_${v.chapter}_${v.verse}"] ?: "yellow"
                            viewModel.quickToggleHighlight(v, tag)
                        },
                        onPlayVerseTts = { v, isTn -> viewModel.playVerseTts(v, isTn) },
                        onPlayChapterAudio = { isTn, idx -> viewModel.playChapterAudio(isTn, idx) },
                        onTogglePlayPauseAudio = { isTn -> viewModel.togglePlayPauseAudio(isTn) },
                        onPlayNextVerse = { viewModel.playNextVerseAudio() },
                        onPlayPreviousVerse = { viewModel.playPreviousVerseAudio() },
                        onSetSpeechRate = { rate -> viewModel.setTtsRate(rate) },
                        onNextChapter = { viewModel.nextChapter() },
                        onPreviousChapter = { viewModel.previousChapter() },
                        onStopTts = { viewModel.stopTts() },
                        onShareVerse = { v -> verseToShare = v },
                        onPreviewVerse = { v -> viewModel.setPreviewVerse(v) }
                    )
                }
                AppNavTab.SEARCH -> {
                    SearchScreen(
                        searchQuery = searchQuery,
                        searchResults = searchResults,
                        onQueryChange = { q -> viewModel.search(q) },
                        onSearch = { q, filt -> viewModel.search(q, filt) },
                        onSelectVerse = { b, ch ->
                            viewModel.selectBookAndChapter(b, ch)
                            currentTab = AppNavTab.READER
                        },
                        onPreviewVerse = { v -> viewModel.setPreviewVerse(v) },
                        onPlayVoice = { v, isTn -> viewModel.playVerseTts(v, isTn) },
                        onBack = { currentTab = AppNavTab.READER }
                    )
                }
                AppNavTab.BOOKMARKS -> {
                    BookmarksScreen(
                        bookmarks = bookmarks,
                        onSelectBookmark = { b, ch ->
                            viewModel.selectBookAndChapter(b, ch)
                            currentTab = AppNavTab.READER
                        },
                        onDeleteBookmark = { id -> viewModel.deleteBookmark(id) },
                        onPreviewVerse = { v -> viewModel.setPreviewVerse(v) },
                        onPlayVoice = { v, isTn -> viewModel.playVerseTts(v, isTn) },
                        onBack = { currentTab = AppNavTab.READER }
                    )
                }
                AppNavTab.PLANS -> {
                    ReadingPlanScreen(
                        plans = plans,
                        progressList = planProgress,
                        userStreak = userStreak,
                        onSelectDayReading = { b, ch ->
                            viewModel.selectBookAndChapter(b, ch)
                            currentTab = AppNavTab.READER
                        },
                        onToggleDayCompleted = { pId, dNum -> viewModel.markPlanDayCompleted(pId, dNum) },
                        onOpenStreakDialog = { showStreakDialog = true },
                        onBack = { currentTab = AppNavTab.READER }
                    )
                }
            }
        }
    }

    // Book & Chapter Picker Dialog
    if (showBookPicker) {
        BookChapterPickerDialog(
            currentBook = currentBook,
            currentChapter = currentChapter,
            onDismiss = { showBookPicker = false },
            onSelectBookAndChapter = { b, ch -> viewModel.selectBookAndChapter(b, ch) }
        )
    }

    // Font size & Accessibility Settings Dialog
    if (showAccessibilitySettings) {
        AccessibilitySettingsDialog(
            currentFontSizeSp = fontSizeSp,
            currentTheme = readingTheme,
            currentReadingMode = readingMode,
            onFontSizeChange = { sz -> viewModel.setFontSize(sz) },
            onThemeChange = { th -> viewModel.setReadingTheme(th) },
            onReadingModeChange = { md -> viewModel.setReadingMode(md) },
            onDismiss = { showAccessibilitySettings = false }
        )
    }

    // Verse Action Bottom Sheet
    if (selectedVerseForAction != null) {
        val activeVerse = selectedVerseForAction!!
        val isBookmarked = bookmarks.any { it.bookId == activeVerse.bookId && it.chapter == activeVerse.chapter && it.verse == activeVerse.verse }
        val note = bookmarks.find { it.bookId == activeVerse.bookId && it.chapter == activeVerse.chapter && it.verse == activeVerse.verse }?.note

        VerseActionSheet(
            verse = activeVerse,
            isBookmarked = isBookmarked,
            existingNote = note,
            onSaveBookmark = { color, noteText -> viewModel.saveBookmark(activeVerse, color, noteText) },
            onDeleteBookmark = {
                val bmId = "bm_${activeVerse.bookId}_${activeVerse.chapter}_${activeVerse.verse}"
                viewModel.deleteBookmark(bmId)
            },
            onPlayTts = { isSetswana ->
                viewModel.playVerseTts(activeVerse, isSetswana)
            },
            onShareVerse = {
                verseToShare = activeVerse
            },
            onPreviewVerse = {
                viewModel.setPreviewVerse(activeVerse)
            },
            onDismiss = { viewModel.closeVerseActionSheet() }
        )
    }

    // Verse Preview Dialog
    if (previewVerse != null) {
        val pVerse = previewVerse!!
        VersePreviewDialog(
            verse = pVerse,
            onPlayVoice = { isTn -> viewModel.playVerseTts(pVerse, isTn) },
            onHighlight = { color -> viewModel.quickToggleHighlight(pVerse, color) },
            onShare = {
                verseToShare = pVerse
                viewModel.setPreviewVerse(null)
            },
            onDismiss = { viewModel.setPreviewVerse(null) }
        )
    }

    // Share Verse Dialog
    if (verseToShare != null) {
        ShareVerseDialog(
            verse = verseToShare!!,
            onDismiss = { verseToShare = null }
        )
    }

    // Study Streak & Notification Reminders Dialog
    if (showStreakDialog) {
        StudyStreakDialog(
            userStreak = userStreak,
            isReminderEnabled = isReminderEnabled,
            reminderHour = reminderHour,
            reminderMinute = reminderMinute,
            onUpdateReminder = { enabled, h, m -> viewModel.updateReminderSettings(enabled, h, m) },
            onDismiss = { showStreakDialog = false }
        )
    }

    // Google Sign-In & Cloud Sync Dialog
    if (showAccountSyncDialog) {
        GoogleSignInDialog(
            currentUser = currentUser,
            syncStatus = syncStatus,
            onSyncNow = { viewModel.syncWithCloud() },
            onAuthSuccess = { viewModel.syncWithCloud() },
            onDismiss = { showAccountSyncDialog = false }
        )
    }
}
