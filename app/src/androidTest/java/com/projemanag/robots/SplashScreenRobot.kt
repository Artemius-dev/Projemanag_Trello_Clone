package com.projemanag.robots

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.projemanag.R
import com.projemanag.activities.IntroActivity
import com.projemanag.activities.MainActivity
import com.projemanag.activities.SplashActivity
import com.projemanag.conditionwatcher.ConditionWatchers
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElementIsGone
import com.projemanag.testHelpers.ExhaustiveIntentsTestRule

fun splashScreen(func: SplashScreenRobot.() -> Unit) = SplashScreenRobot().apply { func() }

val splashActivityTestRule = ExhaustiveIntentsTestRule(SplashActivity::class.java, true, true)

class SplashScreenRobot : BaseTestRobot() {

    fun waitForSplashScreenIsGone() {
        waitForElementIsGone(onView(withId(R.id.tv_app_name)))
    }

    fun checkIsUserIsLoggedIn() {
        intended(hasComponent(MainActivity::class.java.name))
        intended(hasComponent(IntroActivity::class.java.name), times(0))
    }

    fun checkIsUserIsNotLoggedIn() {
        intended(hasComponent(IntroActivity::class.java.name))
        intended(hasComponent(MainActivity::class.java.name), times(0))
    }
}