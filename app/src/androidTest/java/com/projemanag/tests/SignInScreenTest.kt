package com.projemanag.tests

import android.content.Context
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.projemanag.BaseApplication
import com.projemanag.BaseTest
import com.projemanag.activities.SignInActivity
import com.projemanag.activities.SplashActivity
import com.projemanag.di.ProductionModule
import com.projemanag.factory.TaskFactory
import com.projemanag.factory.UserFactory
import com.projemanag.firebase.FirestoreClass
import com.projemanag.models.factory.BoardFactory
import com.projemanag.models.factory.IUserFactory
import com.projemanag.robots.BaseTestRobot
import com.projemanag.robots.introScreen
import com.projemanag.robots.signInScreen
import com.projemanag.robots.splashActivityTestRule
import com.projemanag.robots.splashScreen
import com.projemanag.utils.EspressoIdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import java.lang.Thread.sleep
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(ProductionModule::class)
@RunWith(AndroidJUnit4ClassRunner::class)
class SignInScreenTest() : BaseTest() {

    var mActivityTestRule = splashActivityTestRule

    var hiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(ApplicationComponent::class)
    class TestModule() {

        @Provides
        @Singleton
        fun provideContext(@ApplicationContext appContext: Context): Context = appContext

        @Singleton
        @Provides
        fun provideFirestoreSettings(): FirebaseFirestoreSettings {
            return FirebaseFirestoreSettings.Builder()
                .setHost("10.0.2.2:8080")
                .setSslEnabled(false)
                .setPersistenceEnabled(false)
                .build()
        }

        @Singleton
        @Provides
        fun provideFirestoreAuth(): FirebaseAuth {
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.useEmulator("10.0.2.2", 9099)
            return firebaseAuth
        }

        @Singleton
        @Provides
        fun provideFirebaseFirestore(settings: FirebaseFirestoreSettings): FirebaseFirestore {
            val firestore = FirebaseFirestore.getInstance()

            firestore.firestoreSettings = settings

            return firestore
        }

        @Singleton
        @Provides
        fun provideFirestoreClass(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth): FirestoreClass {
            return FirestoreClass(firebaseFirestore, firebaseAuth)
        }

        @Singleton
        @Provides
        fun provideTaskFactory(firestoreClass: FirestoreClass): TaskFactory {
            return TaskFactory(firestoreClass)
        }

        @Singleton
        @Provides
        fun provideBoardFactory(firestoreClass: FirestoreClass): BoardFactory {
            return BoardFactory(firestoreClass)
        }

        @Singleton
        @Provides
        fun provideUserFactory(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore, firestoreClass: FirestoreClass): IUserFactory {
            return UserFactory(firebaseAuth, firebaseFirestore, firestoreClass)
        }

    }

    @Before
     fun setup() {
//        Intents.init()
        IdlingRegistry.getInstance().register(EspressoIdlingResource().countingIdlingResource)
        BaseTestRobot().registerFakeUser()
    }

    @After
     fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource().countingIdlingResource)
//        Intents.release()
    }

    @get:Rule
    var rule = RuleChain.outerRule(hiltRule).
    around(mActivityTestRule)

//    @get: Rule
//    var chain = RuleChain.outerRule(clearPreferencesRule)
//        .around(clearFilesRule)

    @Test
    fun signInErrorEnterEmail() {
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
            enterFakePassword()
            tapSignInButton()
            verifyErrorEmail()
        }
    }
    @Test
    fun signInErrorEnterPassword() {
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
            tapSignInButton()
            verifyErrorPassword()
        }
    }

    @Test
    fun signInErrorAuthentication() {
        splashScreen {
            deleteFakeUser()
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
            verifyErrorAuthentication()
        }
    }

    // TODO Doesn't work as expacted
    // I don't know why byt with production injection It's all right
    // But after injection of module above Dialog can't load
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