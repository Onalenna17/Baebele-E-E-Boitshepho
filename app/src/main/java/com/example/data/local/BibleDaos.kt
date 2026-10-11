package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY createdAtMillis DESC")
    fun observeAllBookmarks(): Flow<List<LocalBookmark>>

    @Query("SELECT * FROM bookmarks WHERE bookId = :bookId AND chapter = :chapter")
    suspend fun getBookmarksForChapter(bookId: String, chapter: Int): List<LocalBookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: LocalBookmark)

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun deleteBookmarkById(id: String)

    @Query("DELETE FROM bookmarks WHERE bookId = :bookId AND chapter = :chapter AND verse = :verse")
    suspend fun deleteBookmarkByVerse(bookId: String, chapter: Int, verse: Int)

    @Query("SELECT * FROM bookmarks WHERE isSynced = 0")
    suspend fun getUnsyncedBookmarks(): List<LocalBookmark>

    @Query("UPDATE bookmarks SET isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: String)
}

@Dao
interface ReadingPlanDao {
    @Query("SELECT * FROM reading_plans WHERE planId = :planId")
    fun observePlan(planId: String): Flow<LocalReadingProgress?>

    @Query("SELECT * FROM reading_plans")
    fun observeAllPlans(): Flow<List<LocalReadingProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(plan: LocalReadingProgress)
}

@Dao
interface UserStreakDao {
    @Query("SELECT * FROM user_streak WHERE userId = :userId LIMIT 1")
    fun observeStreak(userId: String): Flow<LocalUserStreak?>

    @Query("SELECT * FROM user_streak WHERE userId = :userId LIMIT 1")
    suspend fun getStreak(userId: String): LocalUserStreak?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateStreak(streak: LocalUserStreak)
}
