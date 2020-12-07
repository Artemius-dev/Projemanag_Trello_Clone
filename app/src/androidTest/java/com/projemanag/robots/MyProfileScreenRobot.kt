package com.projemanag.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.projemanag.R
import com.projemanag.activities.IntroActivity
import com.projemanag.activities.MainActivity
import com.projemanag.testHelpers.TestConstants.EMAIL
import com.projemanag.testHelpers.TestConstants.NAME
import com.projemanag.testHelpers.TestConstants.NAME_UPDATED
import kotlinx.android.synthetic.main.activity_my_profile.view.*

fun profileScreen(func: MyProfileScreenRobot.() -> Unit) = MyProfileScreenRobot().apply { func() }

class MyProfileScreenRobot : BaseTestRobot() {

    fun verifyFakeName() {
        onView(withId(R.id.et_name)).check(matches(withText(NAME)))
    }

    fun verifyName(name : String) {
        onView(withId(R.id.et_name)).check(matches(withText(name)))
    }

    fun verifyFakeEmail() {
        onView(withId(R.id.et_email)).check(matches(withText(EMAIL)))
    }

    fun verifyEmail(email : String) {
        onView(withId(R.id.et_email)).check(matches(withText(email)))
    }

    fun verifyPhoneNumber(phoneNumber : String) {
        onView(withId(R.id.et_mobile)).check(matches(withText(phoneNumber)))
    }

    fun changeProfileName() {
        onView(withId(R.id.et_name)).perform(replaceText(NAME_UPDATED))
    }

    fun verifyUpdateName() {
        onView(withId(R.id.et_name)).check(matches(withText(NAME_UPDATED)))
    }

    fun tapUpdateButton() {
        onView(withId(R.id.btn_update)).perform(click())
    }

    fun checkMainActivityVisible() {
        intended(hasComponent(MainActivity::class.java.name))
    }
}