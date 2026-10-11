package com.example.data.repository

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.remote.FirestoreRepository
import com.example.model.FirestoreBookmark
import com.example.model.FirestoreUserProfile
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class FirestoreRepositoryRuleTest : FirestoreEmulatorTestBase() {

    @Test
    fun saveUserProfile_authenticated_succeeds() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        val repository = FirestoreRepository(firestore, auth)

        val profile = FirestoreUserProfile(
            userId = aliceUid,
            displayName = "Alice Motswana",
            currentStreak = 3L,
            longestStreak = 5L,
            lastActiveDate = "2026-10-11",
            dailyGoalVerses = 10L
        )

        withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.saveUserProfile(profile)
        }

        val observed = withTimeout(FLOW_TIMEOUT_MS) {
            repository.observeUserProfile().first()
        }
        assertNotNull(observed)
        assertEquals(aliceUid, observed?.userId)
        assertEquals(3L, observed?.currentStreak)
    }

    @Test
    fun saveBookmark_authenticated_succeeds() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        val repository = FirestoreRepository(firestore, auth)

        val bookmark = FirestoreBookmark(
            id = "bm_test_${System.currentTimeMillis()}",
            userId = aliceUid,
            bookId = "psa",
            bookNameSetswana = "Dipesalema",
            bookNameEnglish = "Psalms",
            chapter = 23L,
            verse = 1L,
            textSetswana = "JEHOFA ke Modisa wa me; ga nkitla ke tlhoka sepe.",
            textEnglish = "The LORD is my shepherd; I shall not want.",
            tagColor = "yellow",
            note = "Favourite verse"
        )

        withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.saveBookmark(bookmark)
        }

        val bookmarks = withTimeout(FLOW_TIMEOUT_MS) {
            repository.observeBookmarks().first()
        }
        assertTrue(bookmarks.any { it.bookId == "psa" && it.chapter == 23L })
    }

    @Test
    fun unauthenticated_saveBookmark_throwsIllegalStateException() = runBlocking {
        auth.signOut()
        val repository = FirestoreRepository(firestore, auth)

        val bookmark = FirestoreBookmark(
            id = "bm_unauth",
            userId = "someone",
            bookId = "gen",
            bookNameSetswana = "Tshimologo",
            bookNameEnglish = "Genesis",
            chapter = 1L,
            verse = 1L,
            textSetswana = "Mo tshimologong...",
            textEnglish = "In the beginning...",
            tagColor = "green"
        )

        try {
            repository.saveBookmark(bookmark)
            fail("Expected IllegalStateException for unauthenticated write")
        } catch (e: IllegalStateException) {
            // expected
        }
    }

    @Test
    fun crossUser_directAccess_failsWithPermissionDenied() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        val aliceRepo = FirestoreRepository(firestore, auth)
        val bookmarkId = "bm_alice_secret_${System.currentTimeMillis()}"

        aliceRepo.saveBookmark(
            FirestoreBookmark(
                id = bookmarkId,
                userId = aliceUid,
                bookId = "jhn",
                bookNameSetswana = "Johane",
                bookNameEnglish = "John",
                chapter = 3L,
                verse = 16L,
                textSetswana = "Gonne Modimo...",
                textEnglish = "For God so loved...",
                tagColor = "rose"
            )
        )

        // Switch user to Bob
        signInTestUser(BOB_EMAIL)

        try {
            withTimeout(DEFAULT_TIMEOUT_MS) {
                firestore.collection("users").document(aliceUid).collection("bookmarks").document(bookmarkId).get().await()
            }
            fail("Expected FirebaseFirestoreException PERMISSION_DENIED")
        } catch (e: FirebaseFirestoreException) {
            assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, e.code)
        }
    }

    private companion object {
        const val ALICE_EMAIL = "alice@test.com"
        const val BOB_EMAIL = "bob@test.com"
        const val DEFAULT_TIMEOUT_MS = 6000L
        const val FLOW_TIMEOUT_MS = 4000L
    }
}
