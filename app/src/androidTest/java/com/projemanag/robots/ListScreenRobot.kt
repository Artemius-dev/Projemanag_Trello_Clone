package com.projemanag.robots

import android.app.Activity
import android.content.Context
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.projemanag.R
import com.projemanag.activities.CardDetailsActivity
import com.projemanag.adapters.TaskListItemsAdapter
import com.projemanag.testHelpers.TestConstants
import com.projemanag.testHelpers.clickItemWithId
import com.projemanag.testHelpers.clickOnCardListView
import com.projemanag.testHelpers.enterTextOnViewWithId
import com.projemanag.testHelpers.withViewAtPosition
import kotlinx.android.synthetic.main.activity_task_list.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Test

fun listScreen(func: ListScreenRobot.() -> Unit) = ListScreenRobot().apply { func() }

class ListScreenRobot : BaseTestRobot() {

    fun enterFakeListName() {
//        onView(withId(R.id.rv_task_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
//            withId(R.id.et_task_list_name), replaceText(TestConstants.FAKE_LIST_NAME)))

        // TODO Custom View Action for CardView
        onView(allOf(withId(R.id.et_task_list_name), withParent(withParent(withId(R.id.cv_add_task_list_name))))).perform(
            replaceText(TestConstants.FAKE_LIST_NAME_ONE))
    }

    fun enterListName(listName : String) {
        onView(withId(R.id.cv_add_task_list_name)).perform(replaceText(listName))
    }

    fun tapOnCreateListButton() {
        clickOnView(R.id.tv_add_task_list)
    }

    fun tapOnSubmitButtonCreateList() {
        onView(allOf(withId(R.id.ib_done_list_name), withParent(withParent(withId(R.id.cv_add_task_list_name))))).perform(
            click())
    }

    fun tapOnCreateCardButton() {
        onView(withId(R.id.rv_task_list)).perform(RecyclerViewActions
            .actionOnItemAtPosition<TaskListItemsAdapter.MyViewHolder>(0, clickItemWithId(R.id.tv_add_card)))
    }

    fun checkNameOfFakeList() {
//        onView(allOf(withId(R.id.tv_task_list_title), withText(TestConstants.FAKE_LIST_NAME_ONE),
//            withParent(withId(R.id.tv_task_list_title)))).check(
//            matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.rv_task_list)).check(matches(withViewAtPosition(0, hasDescendant(allOf(
            withText(TestConstants.FAKE_LIST_NAME_ONE), isDisplayed())))))
    }

    fun enterCardName() {
        onView(withId(R.id.rv_task_list)).perform(RecyclerViewActions
            .actionOnItemAtPosition<TaskListItemsAdapter.MyViewHolder>(0,
                enterTextOnViewWithId(
                    TestConstants.FAKE_CARD_NAME,
                    R.id.et_card_name)
            ))
    }

    fun tapOnDoneCreateCardButton() {
        onView(withId(R.id.rv_task_list)).perform(RecyclerViewActions
            .actionOnItemAtPosition<TaskListItemsAdapter.MyViewHolder>(0, clickItemWithId(R.id.ib_done_card_name)))
    }

    fun checkFakeCardCreated() {
//        onView(withId(R.id.rv_task_list)).perform(RecyclerViewActions
//            .actionOnItemAtPosition<TaskListItemsAdapter.MyViewHolder>(0, clickItemWithId(R.id.ib_done_card_name)))
        onView(withId(R.id.rv_task_list)).check(matches(withViewAtPosition(0, hasDescendant(allOf(
            withId(R.id.rv_card_list), isDisplayed())))))
    }

    fun tapOnFakeCard() {
        onView(withId(R.id.rv_task_list)).perform(RecyclerViewActions
            .actionOnItemAtPosition<TaskListItemsAdapter.MyViewHolder>(
                0, clickOnCardListView(R.id.rv_card_list, 0)))
    }

    fun checkCardDetailsActivityShow() {
        intended(hasComponent(CardDetailsActivity::class.java.name))
    }
    fun tapOnEditButtonWithFakeList() {

    }

    fun tapOnEditButtonWithList() {

    }

    fun tapOnBackButton() {
        val upButton = onView(allOf(instanceOf(ImageButton::class.java), withParent(withId(R.id.toolbar_task_list_activity))))

        upButton.perform(click())
    }

    fun tapOnAddMembers() {
        openActionBarOverflowOrOptionsMenu(
            ApplicationProvider.getApplicationContext<Context>())

        // Click the item.
        onView(withText(R.string.members))
            .perform(click())
    }
}

