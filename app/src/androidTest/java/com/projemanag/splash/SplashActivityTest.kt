package com.projemanag.splash

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.projemanag.MockTest
import com.projemanag.robots.homeScreen
import com.projemanag.robots.prepare
import com.projemanag.robots.introScreen
import com.projemanag.robots.splashActivityMockTestRule
import com.projemanag.robots.splashScreen
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest : MockTest() {

    @Rule
    @JvmField
    val activityRule = splashActivityMockTestRule

    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun shouldOpenHomeActivityWhenUserIsAuthenticated() {
        prepare(testScope) {
            mockAuthorizedUser()
        }
        splashScreen {
            displayMockAsEntryPoint()
        }
        homeScreen {
            isSuccessfullyLoaded()
        }
    }

    @Test
    fun shouldOpenLoginActivityWhenUserIsNotAuthenticated() {
        prepare(testScope) {
            mockNoAuthorizedUser()
        }
        splashScreen {
            displayAsEntryPoint()
        }
        introScreen {
            isSuccessfullyLoaded()
        }
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }
}