package com.projemanag.robots

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.projemanag.R
import com.projemanag.activities.CreateBoardActivity
import com.projemanag.activities.IntroActivity
import com.projemanag.activities.MainActivity
import com.projemanag.activities.MyProfileActivity
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElement
import com.projemanag.testHelpers.TestConstants
import kotlinx.android.synthetic.main.app_bar_main.view.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import java.lang.Thread.sleep

fun mainScreen(func: MainScreenRobot.() -> Unit) = MainScreenRobot().apply { func() }

class MainScreenRobot : BaseTestRobot() {

    fun tapOnCreateBoardButton() {
        waitForElement(onView(withId(R.id.fab_create_board))).perform(click())
    }

    fun tapOnOpenMenuButton() {
        waitForElement(onView(allOf(instanceOf(AppCompatImageButton::class.java), withParent(withId(R.id.toolbar_main_activity)))))
            .perform(click())
    }

    fun tapOnSignOut() {
        waitForElement(onView(withId(R.id.nav_sign_out)))
            .perform(click())
    }

    fun tapOnOpenProfile() {
        waitForElement(onView(withId(R.id.nav_my_profile)))
            .perform(click())
    }

    fun checkMyProfileActivityShow() {
        intended(hasComponent(MyProfileActivity::class.java.name))
    }

    fun checkMyProfileActivityShowAgain() {
        intended(hasComponent(MyProfileActivity::class.java.name), times(2))
    }

    fun checkIsCreateBoardActivityOpen() {
        intended(hasComponent(CreateBoardActivity::class.java.name))
    }

    fun checkIsMenuShow() {
        waitForElement(onView(withId(R.id.nav_my_profile)))
            .check(matches(isDisplayed()))
        waitForElement(onView(withId(R.id.nav_sign_out)))
            .check(matches(isDisplayed()))
    }

    fun checkIsUserSignOut() {
        intended(hasComponent(IntroActivity::class.java.name))
        waitForElement(onView(withId(R.id.btn_sign_in_intro)))
            .check(matches(isDisplayed()))
        waitForElement(onView(withId(R.id.btn_sign_up_intro)))
            .check(matches(isDisplayed()))
    }

    fun checkFakeBoardWasCreate() {
        onView(allOf(withText(TestConstants.FAKE_BOARD_NAME), withId(R.id.tv_name))).check(matches(
            isDisplayed()))
    }

    fun tapOnFakeBoard() {
        onView(allOf(withText(TestConstants.FAKE_BOARD_NAME), withId(R.id.tv_name))).perform(click())
    }
}