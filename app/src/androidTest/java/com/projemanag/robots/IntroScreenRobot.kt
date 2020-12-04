package com.projemanag.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.projemanag.R
import com.projemanag.activities.MainActivity
import com.projemanag.activities.SignInActivity
import com.projemanag.activities.SignUpActivity
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElementFullyVisible

fun introScreen(func: IntroScreenRobot.() -> Unit) = IntroScreenRobot().apply { func() }

class IntroScreenRobot : BaseTestRobot() {

    fun tapSignInButton() {
        waitForElementFullyVisible(onView(withId(R.id.btn_sign_in_intro)), 10000).perform(click())
    }

    fun tapSignUpButton() {
        waitForElementFullyVisible(onView(withId(R.id.btn_sign_up_intro)), 10000).perform(click())
    }

    fun signInScreenIsSuccessfullyLoaded() {
        intended(hasComponent(SignInActivity::class.java.name))
        intended(hasComponent(SignUpActivity::class.java.name), times(0))
    }

    fun signUpScreenIsSuccessfullyLoaded() {
        intended(hasComponent(SignUpActivity::class.java.name))
        intended(hasComponent(SignInActivity::class.java.name), times(0))
    }
}