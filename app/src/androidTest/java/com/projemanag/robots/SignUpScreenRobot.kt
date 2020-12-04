package com.projemanag.robots

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.projemanag.R
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElement
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElementFullyVisible
import com.projemanag.matchers.ToastMatcher
import org.hamcrest.CoreMatchers.startsWith
import java.lang.Exception


fun signUpScreen(func: SignUpScreenRobot.() -> Unit) = SignUpScreenRobot().apply { func() }

class SignUpScreenRobot : BaseTestRobot() {

    fun tapSignUpButton() {
        waitForElementFullyVisible(
            onView(
                withId(R.id.btn_sign_up)
            ),
            10000
        )
            .perform(click())
    }

    fun enterFakeData() {
        enterFakeName()
        enterFakeEmailAddress()
        enterFakePassword()
    }

    fun enterName(name: String) {
        waitForElementFullyVisible(
            onView(
                withId(R.id.et_name)
            ),
            10000
        )
            .perform(
                typeText(name),
                closeSoftKeyboard()
            )
    }

    fun enterFakeName() {
        waitForElementFullyVisible(
            onView(
                withId(R.id.et_name)
            ),
            10000
        )
            .perform(
                typeText(NAME),
                closeSoftKeyboard()
            )
    }

    fun enterEmailAddress(email: String) {
        waitForElementFullyVisible(
            onView(
                withId(R.id.et_email)
            ),
            10000
        )
            .perform(
                typeText(email),
                closeSoftKeyboard()
            )
    }

    fun enterFakeEmailAddress() {
        waitForElementFullyVisible(
            onView(
                withId(R.id.et_email)
            ),
            10000
        )
            .perform(
                typeText(EMAIL),
                closeSoftKeyboard()
            )
    }

    fun enterPassword(password: String) {
        waitForElementFullyVisible(
            onView(
                withId(R.id.et_password)
            ),
            10000
        )
            .perform(
                typeText(password),
                closeSoftKeyboard()
            )
    }

    fun enterFakePassword() {
        waitForElementFullyVisible(
            onView(
                withId(R.id.et_password)
            ),
            10000
        )
            .perform(
                typeText(PASSWORD),
                closeSoftKeyboard()
            )
    }

    fun verifyUserIsSignUp() {
        waitForElement(
            onView(withText(R.string.register_success)).inRoot(ToastMatcher())
                .check(matches(isDisplayed()))
        )
    }

    fun verifyErrorOccur() {

        try {
            onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(startsWith("Please"))))
        } catch (ex : Exception) {
            onView(withText(R.string.register_fail)).inRoot(ToastMatcher())
                .check(matches(isDisplayed()))
        }

    }

    fun verifyErrorEnterName() {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(startsWith(context.resources.getString(R.string.please_enter_a_name)))))
    }

    fun verifyErrorEnterEmail() {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(startsWith(context.resources.getString(R.string.please_enter_an_email)))))
    }

    fun verifyErrorEnterPassword() {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(startsWith(context.resources.getString(R.string.please_enter_a_password)))))
    }
}

