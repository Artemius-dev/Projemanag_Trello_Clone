package com.projemanag.robots

import androidx.test.rule.ActivityTestRule
import com.projemanag.activities.SplashActivity
import com.projemanag.testHelpers.ExhaustiveIntentsTestRule

fun splashScreen(func: SplashScreenRobot.() -> Unit) = SplashScreenRobot().apply { func() }

val splashActivityE2ETestRule = object : ActivityTestRule<SplashActivity>(SplashActivity::class.java, true, false) {
    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
    }
}

val splashActivityMockTestRule = ExhaustiveIntentsTestRule<SplashActivity>(SplashActivity::class.java, true, true)

class SplashScreenRobot : BaseTestRobot() {
    fun displayAsEntryPoint() {
        splashActivityE2ETestRule.launchActivity(null)
    }

    fun displayMockAsEntryPoint() {
        splashActivityMockTestRule.launchActivity(null)
    }
}