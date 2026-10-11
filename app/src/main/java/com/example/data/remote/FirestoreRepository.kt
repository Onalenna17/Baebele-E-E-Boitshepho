package com.example.data.remote

import android.content.Context
import com.example.R
import com.example.model.FirestoreBookmark
import com.example.model.FirestoreReadingPlan
import com.example.model.FirestoreUserProfile
import com.example.util.OperationType
import com.example.util.handleFirestoreError
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth = Firebase.auth
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        ),
        Firebase.auth
    )

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before accessing Firestore.")
    }

    suspend fun saveUserProfile(profile: FirestoreUserProfile) {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
        try {
            val payload = mapOf(
                "userId" to uid,
                "displayName" to profile.displayName.ifBlank { "Mmadikwalo" },
                "currentStreak" to profile.currentStreak,
                "longestStreak" to profile.longestStreak,
                "lastActiveDate" to profile.lastActiveDate,
                "dailyGoalVerses" to profile.dailyGoalVerses.coerceIn(1, 500),
                "createdAt" to (profile.createdAt ?: FieldValue.serverTimestamp()),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            throw e
        }
    }

    fun observeUserProfile(): Flow<FirestoreUserProfile?> = flow {
        val uid = requireUserId()
        val path = "users/$uid"
        emitAll(
            db.collection("users").document(uid).snapshots()
                .map { snapshot ->
                    if (snapshot.exists()) snapshot.toObject(FirestoreUserProfile::class.java) else null
                }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.GET, path)
                    throw error
                }
        )
    }

    suspend fun saveBookmark(bookmark: FirestoreBookmark) {
        val uid = requireUserId()
        val bookmarkId = bookmark.id.ifBlank { "bm_${System.currentTimeMillis()}" }
        val docRef = db.collection("users").document(uid).collection("bookmarks").document(bookmarkId)
        try {
            val payload = mutableMapOf<String, Any>(
                "userId" to uid,
                "bookId" to bookmark.bookId,
                "bookNameSetswana" to bookmark.bookNameSetswana,
                "bookNameEnglish" to bookmark.bookNameEnglish,
                "chapter" to bookmark.chapter,
                "verse" to bookmark.verse,
                "textSetswana" to bookmark.textSetswana,
                "textEnglish" to bookmark.textEnglish,
                "tagColor" to bookmark.tagColor,
                "createdAt" to (bookmark.createdAt ?: FieldValue.serverTimestamp()),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            if (!bookmark.note.isNullOrBlank()) {
                payload["note"] = bookmark.note
            }
            docRef.set(payload).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            throw e
        }
    }

    suspend fun deleteBookmark(bookmarkId: String) {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid).collection("bookmarks").document(bookmarkId)
        try {
            docRef.delete().await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            throw e
        }
    }

    fun observeBookmarks(): Flow<List<FirestoreBookmark>> = flow {
        val uid = requireUserId()
        val path = "users/$uid/bookmarks"
        emitAll(
            db.collection("users").document(uid).collection("bookmarks")
                .snapshots()
                .map { snapshot -> snapshot.toObjects(FirestoreBookmark::class.java) }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                    throw error
                }
        )
    }

    suspend fun saveReadingPlan(plan: FirestoreReadingPlan) {
        val uid = requireUserId()
        val planId = plan.id.ifBlank { plan.planType.ifBlank { "plan_${System.currentTimeMillis()}" } }
        val docRef = db.collection("users").document(uid).collection("reading_plans").document(planId)
        try {
            val payload = mapOf(
                "userId" to uid,
                "planType" to plan.planType,
                "dayCompletedCount" to plan.dayCompletedCount,
                "totalDays" to plan.totalDays,
                "completedDayIndexes" to plan.completedDayIndexes,
                "lastReadDate" to plan.lastReadDate,
                "createdAt" to (plan.createdAt ?: FieldValue.serverTimestamp()),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            throw e
        }
    }

    fun observeReadingPlans(): Flow<List<FirestoreReadingPlan>> = flow {
        val uid = requireUserId()
        val path = "users/$uid/reading_plans"
        emitAll(
            db.collection("users").document(uid).collection("reading_plans")
                .snapshots()
                .map { snapshot -> snapshot.toObjects(FirestoreReadingPlan::class.java) }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                    throw error
                }
        )
    }
}
