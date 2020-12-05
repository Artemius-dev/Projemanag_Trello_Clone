package com.projemanag.tests

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.projemanag.BaseTest
import com.projemanag.activities.SplashActivity
import com.projemanag.robots.BaseTestRobot
import com.projemanag.robots.introScreen
import com.projemanag.robots.signUpScreen
import com.projemanag.robots.splashScreen
import com.projemanag.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SignUpScreenTest() : BaseTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

//    @Inject
//    lateinit var userFactory: UserFactory
//
//    @Inject
//    lateinit var firebaseAuth: FirebaseAuth
//
//    @Inject
//    lateinit var firebaseFirestore: FirebaseFirestore

    private val TAG = "SignUpScreenTest"

    @get: Rule
    var chain = RuleChain.outerRule(clearPreferencesRule)
        .around(clearDatabaseRule)
        .around(clearFilesRule)

    @Before
    override fun setup() {

//        val component =
//            DaggerTestAppComponent.builder().testModule(TestModule(BaseApplication())).build()
//
//        component.inject(this)

        Intents.init()
        IdlingRegistry.getInstance().register(EspressoIdlingResource().countingIdlingResource)

//        BaseTestRobot().signOut()
//        BaseTestRobot().deleteFakeUser()
//        BaseTestRobot().signOut()
    }

    @After
    override fun teardown() {
//        BaseTestRobot().signOut()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource().countingIdlingResource)

        Intents.release()
    }

    @Test
    fun signUpErrorNoCredentials() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
            checkIsUserIsNotLoggedIn()
        }
        introScreen {
            tapSignUpButton()
            signUpScreenIsSuccessfullyLoaded()
        }
        signUpScreen {
            tapSignUpButton()
            verifyErrorOccur()
        }
    }

    @Test
    fun signUpErrorEnterName() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
            checkIsUserIsNotLoggedIn()
        }
        introScreen {
            tapSignUpButton()
            signUpScreenIsSuccessfullyLoaded()
        }
        signUpScreen {
            tapSignUpButton()
            verifyErrorEnterName()
        }
    }

    @Test
    fun signUpErrorEnterEmail() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
            checkIsUserIsNotLoggedIn()
        }
        introScreen {
            tapSignUpButton()
            signUpScreenIsSuccessfullyLoaded()
        }
        signUpScreen {
            enterFakeName()
            tapSignUpButton()
            verifyErrorEnterEmail()
        }
    }

    @Test
    fun signUpErrorEnterPassword() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
            checkIsUserIsNotLoggedIn()
        }
        introScreen {
            tapSignUpButton()
            signUpScreenIsSuccessfullyLoaded()
        }
        signUpScreen {
            enterFakeName()
            enterFakeEmailAddress()
            tapSignUpButton()
            verifyErrorEnterPassword()
        }
    }

    @Test
    fun signUpErrorOnServerSide() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
            checkIsUserIsNotLoggedIn()
        }
        introScreen {
            registerFakeUser()
            tapSignUpButton()
            signUpScreenIsSuccessfullyLoaded()
        }
        signUpScreen {
            enterFakeData()
            tapSignUpButton()
            verifyErrorOccur()
        }
    }

    @Test
    fun signUpSuccess() {
        splashScreen {
            signOut()
            waitForSplashScreenIsGone()
            checkIsUserIsNotLoggedIn()
        }
        introScreen {
            tapSignUpButton()
            signUpScreenIsSuccessfullyLoaded()
        }
        signUpScreen {
            enterFakeData()
            tapSignUpButton()
            verifyUserIsSignUp()
            signOut()
        }
    }
}