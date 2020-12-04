package com.projemanag.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.firebase.auth.FirebaseAuth
import com.projemanag.TestBaseApplication
import com.projemanag.di.DaggerTestAppComponent
import com.projemanag.di.TestModule
import com.projemanag.factory.UserFactory
import com.projemanag.robots.splashActivityTestRule
import com.projemanag.robots.splashScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashActivityTest {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var userFactory: UserFactory

    @Before
    fun setup() {
//        var component =
//            DaggerTestAppComponent.builder().testModule(TestModule(TestBaseApplication())).build()
//
//        component.inject(this)

    }

    @Rule
    @JvmField
    val activityRule = splashActivityTestRule

    @Test
    fun shouldOpenHomeActivityWhenUserIsAuthenticated() {
        splashScreen {
            signIn()
            waitForSplashScreenIsGone()
            checkIsUserIsLoggedIn()
        }
    }

    @Test
    fun shouldOpenLoginActivityWhenUserIsNotAuthenticated() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
            checkIsUserIsNotLoggedIn()
        }
    }

}