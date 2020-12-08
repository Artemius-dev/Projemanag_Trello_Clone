package com.projemanag.robots

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.projemanag.R
import com.projemanag.activities.IntroActivity
import com.projemanag.activities.MainActivity
import com.projemanag.activities.MembersActivity
import com.projemanag.activities.SplashActivity
import com.projemanag.adapters.MemberListItemsAdapter
import com.projemanag.conditionwatcher.ConditionWatchers
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElementIsGone
import com.projemanag.testHelpers.ExhaustiveIntentsTestRule
import com.projemanag.testHelpers.TestConstants
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

fun membersListScreen(func: MembersScreenRobot.() -> Unit) = MembersScreenRobot().apply { func() }

class MembersScreenRobot : BaseTestRobot() {

    fun checkIsMembersActivityOpen() {
        intended(hasComponent(MembersActivity::class.java.name))
    }

    fun openAddMemberMenu() {
        onView(withId(R.id.action_add_member)).perform(click())
    }

    fun verifySearchMemberMenuVisible() {
        onView(withText(R.string.search_member)).check(matches(isDisplayed()));
    }

    fun enterInSearchFieldSecondFakeUser() {
        onView(withId(R.id.et_email_search_member)).perform(replaceText(TestConstants.SECOND_USER_EMAIL), closeSoftKeyboard())
    }

    fun tapOnAddMemberButton() {
        onView(withId(R.id.tv_add)).perform(click());
    }

    fun tapOnCancelMemberButton() {
        onView(withId(R.id.tv_cancel)).perform(click());
    }

    fun checkMemberIsAdded() {
        onView(withId(R.id.rv_members_list)).perform(RecyclerViewActions.actionOnItemAtPosition<MemberListItemsAdapter.MyViewHolder>(
            1, click()))
        onView(allOf(withId(R.id.tv_member_name), withText(TestConstants.SECOND_USER_NAME))).check(
            matches(isDisplayed()))
    }
}