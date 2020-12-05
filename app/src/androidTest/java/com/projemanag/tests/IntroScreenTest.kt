package com.projemanag.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.projemanag.BaseApplication
import com.projemanag.BaseTest
import com.projemanag.robots.introScreen
import com.projemanag.robots.splashActivityTestRule
import com.projemanag.robots.splashScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
class IntroScreenTest() : BaseTest(){

    @Rule
    @JvmField
    var mActivityTestRule = splashActivityTestRule

    @Before
    override fun setup() {

//        val component = DaggerTestAppComponent.builder().testModule(TestModule(BaseApplication())).build()
//
//        component.inject(this)

    }

    @Test
    fun verifySignInScreenWasShown() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
        }
        introScreen {
            tapSignInButton()
            signInScreenIsSuccessfullyLoaded()
        }
    }

    @Test
    fun verifySignUpScreenWasShown() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
        }
        introScreen {
            tapSignUpButton()
            signUpScreenIsSuccessfullyLoaded()
        }
    }

}