package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class LocalBookmark(
    @PrimaryKey val id: String,
    val userId: String,
    val bookId: String,
    val bookNameSetswana: String,
    val bookNameEnglish: String,
    val chapter: Int,
    val verse: Int,
    val textSetswana: String,
    val textEnglish: String,
    val tagColor: String,
    val note: String?,
    val createdAtMillis: Long,
    val isSynced: Boolean = false
)

@Entity(tableName = "reading_plans")
data class LocalReadingProgress(
    @PrimaryKey val planId: String,
    val userId: String,
    val planType: String,
    val dayCompletedCount: Int,
    val totalDays: Int,
    val completedDayIndexesCsv: String,
    val lastReadDate: String,
    val isSynced: Boolean = false
)

@Entity(tableName = "user_streak")
data class LocalUserStreak(
    @PrimaryKey val userId: String,
    val currentStreak: Int,
    val longestStreak: Int,
    val lastActiveDate: String,
    val dailyGoalVerses: Int,
    val versesReadToday: Int
)
