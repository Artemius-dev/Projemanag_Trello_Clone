package com.projemanag.tests

import android.content.Context
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.projemanag.di.ProductionModule
import com.projemanag.factory.TaskFactory
import com.projemanag.factory.UserFactory
import com.projemanag.firebase.FirestoreClass
import com.projemanag.models.factory.IUserFactory
import com.projemanag.robots.splashActivityTestRule
import com.projemanag.robots.splashScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(ProductionModule::class)
@RunWith(AndroidJUnit4ClassRunner::class)
//@Config(application = HiltTestApplication::class)
class SplashActivityTest {

//    @Inject
//    lateinit var firebaseAuth: FirebaseAuth
//
//    @Inject
//    lateinit var userFactory: UserFactory

    @Before
    fun setup() {
//        var component =
//            DaggerTestAppComponent.builder().testModule(TestModule(TestBaseApplication())).build()
//
//        component.inject(this)

    }

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
        fun provideUserFactory(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore, firestoreClass: FirestoreClass): IUserFactory {
            return UserFactory(firebaseAuth, firebaseFirestore, firestoreClass)
        }

    }



//    @get:Rule(order = 1)
    val activityRule = splashActivityTestRule

//    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var rule = RuleChain.outerRule(hiltRule).
    around(activityRule)

    @Test
    fun shouldOpenHomeActivityWhenUserIsAuthenticated() {
        splashScreen {
//            signIn()
            signOut()
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