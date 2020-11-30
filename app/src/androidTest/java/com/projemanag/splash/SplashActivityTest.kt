package com.projemanag.splash

import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projemanag.TestBaseApplication
import com.projemanag.di.TestAppComponent
import com.projemanag.robots.splashActivityMockTestRule
import com.projemanag.utils.await
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashActivityTest {

    @Inject
    lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseFirestore: FirebaseFirestore

    init {
        signIn()
    }

    private fun signIn() = runBlocking {
        firebaseAuth.signInWithEmailAndPassword(
            EMAIL,
            PASSWORD
        ).await()
    }
    companion object {
        const val EMAIL = "artem@gmail.com"
        const val PASSWORD = "123456"
    }

    @Rule
    @JvmField
    val activityRule = splashActivityMockTestRule

    @Test
    fun shouldOpenHomeActivityWhenUserIsAuthenticated() {
        assert(::firebaseFirestore.isInitialized)
    }

    @Test
    fun shouldOpenLoginActivityWhenUserIsNotAuthenticated() {
    }

}