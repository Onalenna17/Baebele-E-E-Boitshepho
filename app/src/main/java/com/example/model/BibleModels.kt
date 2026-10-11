package com.example.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

enum class Testament {
    OLD, NEW
}

enum class ReadingMode(val labelSetswana: String, val labelEnglish: String) {
    VERSE_BY_VERSE("Temana ka Temana", "Setswana & English Verse by Verse"),
    SETSWANA_ONLY("Setswana Fela", "Setswana Only"),
    ENGLISH_ONLY("Sekgowa Fela", "English Only")
}

enum class ReadingTheme(val title: String) {
    PARCHMENT("Letlalo / Sepia"),
    LIGHT("Lesedi / Light"),
    DARK("Lefifi / Dark"),
    HIGH_CONTRAST("Pharologano e Kgolo / High Contrast")
}

data class BibleBook(
    val id: String,
    val nameSetswana: String,
    val nameEnglish: String,
    val testament: Testament,
    val chaptersCount: Int,
    val order: Int
)

data class BibleVerse(
    val bookId: String,
    val bookNameSetswana: String,
    val bookNameEnglish: String,
    val chapter: Int,
    val verse: Int,
    val textSetswana: String,
    val textEnglish: String
)

data class BookmarkItem(
    val id: String = "",
    val userId: String = "",
    val bookId: String = "",
    val bookNameSetswana: String = "",
    val bookNameEnglish: String = "",
    val chapter: Int = 1,
    val verse: Int = 1,
    val textSetswana: String = "",
    val textEnglish: String = "",
    val tagColor: String = "yellow",
    val note: String? = null,
    val createdAtMillis: Long = System.currentTimeMillis()
)

data class ReadingPlan(
    val id: String,
    val titleSetswana: String,
    val titleEnglish: String,
    val descriptionSetswana: String,
    val descriptionEnglish: String,
    val totalDays: Int,
    val days: List<PlanDay>
)

data class PlanDay(
    val dayNumber: Int,
    val titleSetswana: String,
    val titleEnglish: String,
    val bookId: String,
    val chapter: Int,
    val verseStart: Int = 1,
    val verseEnd: Int = 20
)

@IgnoreExtraProperties
data class FirestoreUserProfile(
    @DocumentId val id: String = "",
    val userId: String = "",
    val displayName: String = "",
    val currentStreak: Long = 0L,
    val longestStreak: Long = 0L,
    val lastActiveDate: String = "",
    val dailyGoalVerses: Long = 10L,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreBookmark(
    @DocumentId val id: String = "",
    val userId: String = "",
    val bookId: String = "",
    val bookNameSetswana: String = "",
    val bookNameEnglish: String = "",
    val chapter: Long = 1L,
    val verse: Long = 1L,
    val textSetswana: String = "",
    val textEnglish: String = "",
    val tagColor: String = "yellow",
    val note: String? = null,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreReadingPlan(
    @DocumentId val id: String = "",
    val userId: String = "",
    val planType: String = "",
    val dayCompletedCount: Long = 0L,
    val totalDays: Long = 30L,
    val completedDayIndexes: List<Long> = emptyList(),
    val lastReadDate: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)
