package com.projemanag.robots

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.projemanag.BaseTest
import com.projemanag.R
import com.projemanag.activities.IntroActivity
import com.projemanag.activities.MainActivity
import com.projemanag.activities.SignInActivity
import com.projemanag.conditionwatcher.ConditionWatchers
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElement
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElementFullyVisible
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElementIsGone
import com.projemanag.matchers.ToastMatcher
import com.projemanag.testHelpers.TestConstants.EMAIL
import com.projemanag.testHelpers.TestConstants.PASSWORD
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.startsWith

fun signInScreen(func: SignInScreenRobot.() -> Unit) = SignInScreenRobot().apply { func() }

class SignInScreenRobot : BaseTestRobot() {

    fun enterEmailAddress(email: String) {
        waitForElementFullyVisible(
            onView(
                ViewMatchers.withId(R.id.et_email_sign_in)
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
                withId(R.id.et_email_sign_in)
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
                withId(R.id.et_password_sign_in)
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
                withId(R.id.et_password_sign_in)
            ),
            10000
        )
            .perform(
                typeText(PASSWORD),
                closeSoftKeyboard()
            )
    }

    fun tapSignInButton() {
        onView(withId(R.id.btn_sign_in)).perform(click())
    }

    fun verifyUserIsSignIn() {
        waitForElementIsGone(onView(withId(R.id.btn_sign_in)), 10000)
        waitForElement(onView(withText(R.string.no_boards_are_available)))
        intended(hasComponent(MainActivity::class.java.name))
        // Commented this because Espresso record all the intents and times(0) means that
        // intent was launched 0 times. In my scenario I'm trying to check whether intent
        // was launched now and never before
//        intended(hasComponent(SignInActivity::class.java.name), times(0))
    }

    fun verifyErrorEmail() {
        waitForElement(
            onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(context.resources.getString(R.string.please_enter_an_email))))
        )
    }

    fun verifyErrorPassword() {
        waitForElement(
            onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(context.resources.getString(R.string.please_enter_a_password))))
        )
    }

    fun verifyErrorAuthentication() {

        onView(
            withText(
                context.resources.getString(R.string.authentication_error)
            )
        )
            .inRoot(ToastMatcher())
            .check(
                matches(isDisplayed())
            )
    }
}