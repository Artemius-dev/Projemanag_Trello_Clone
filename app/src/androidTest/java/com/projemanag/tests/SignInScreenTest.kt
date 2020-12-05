package com.projemanag.tests

import androidx.test.espresso.intent.Intents
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.projemanag.BaseApplication
import com.projemanag.activities.SplashActivity
import com.projemanag.robots.introScreen
import com.projemanag.robots.signInScreen
import com.projemanag.robots.splashScreen
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SignInScreenTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Before
    fun setup() {

//        val component =
//            DaggerTestAppComponent.builder().testModule(TestModule(BaseApplication())).build()
//
//        component.inject(this)

        Intents.init()

    }

    @After
    fun after() {
        Intents.release()
    }

    @Test
    fun signInError() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
            checkIsUserIsNotLoggedIn()
        }
        introScreen {
            tapSignInButton()
            signInScreenIsSuccessfullyLoaded()
        }
        signInScreen {
            tapSignInButton()
            verifyErrorOccur()
        }
    }

    @Test
    fun signInSuccess() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
            checkIsUserIsNotLoggedIn()
        }
        introScreen {
            tapSignInButton()
            signInScreenIsSuccessfullyLoaded()
        }
        signInScreen {
            enterFakeEmailAddress()
            enterFakePassword()
            tapSignInButton()
            verifyUserIsSignIn()
        }
    }
}