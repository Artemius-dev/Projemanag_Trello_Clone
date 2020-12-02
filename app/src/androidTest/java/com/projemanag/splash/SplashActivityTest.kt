package com.projemanag.splash

import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.firebase.auth.FirebaseAuth
import com.projemanag.TestBaseApplication
import com.projemanag.activities.IntroActivity
import com.projemanag.activities.MainActivity
import com.projemanag.di.DaggerTestAppComponent
import com.projemanag.di.TestModule
import com.projemanag.factroy.UserFactory
import com.projemanag.robots.splashActivityMockTestRule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashActivityTest {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var userFactory: UserFactory

    @Before
    fun setup() {
        var component = DaggerTestAppComponent.builder().testModule(TestModule(TestBaseApplication())).build()

        component.inject(this)

        signIn()
    }

    private fun signIn() = runBlocking {
        userFactory.registerFakeUser()
        firebaseAuth.signInWithEmailAndPassword(
            EMAIL,
            PASSWORD
        )
    }

    private fun signOut() = runBlocking {
        firebaseAuth.signOut()
    }
    companion object {
        const val EMAIL = "antonio@gmail.com"
        const val PASSWORD = "123456"
    }

    @Rule
    @JvmField
    val activityRule = splashActivityMockTestRule

    @Test
    fun shouldOpenHomeActivityWhenUserIsAuthenticated() {
        sleep(2500)
        intended(hasComponent(MainActivity::class.java.name))
        intended(hasComponent(IntroActivity::class.java.name), times(0))

    }

    @Test
    fun shouldOpenLoginActivityWhenUserIsNotAuthenticated() {

        intended(hasComponent(IntroActivity::class.java.name))
        intended(hasComponent(MainActivity::class.java.name), times(0))
    }

}