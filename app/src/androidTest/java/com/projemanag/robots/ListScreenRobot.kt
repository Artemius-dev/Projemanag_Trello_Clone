package com.projemanag.robots

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.projemanag.R
import com.projemanag.testHelpers.TestConstants
import kotlinx.android.synthetic.main.activity_task_list.*
import org.hamcrest.CoreMatchers.allOf

fun listScreen(func: ListScreenRobot.() -> Unit) = ListScreenRobot().apply { func() }

class ListScreenRobot : BaseTestRobot() {

    fun enterFakeListName() {

    }

    fun enterListName(listName : String) {
        onView(withId(R.id.cv_add_task_list_name)).perform(replaceText(TestConstants.FAKE_CARD_NAME))
    }

    fun tapOnCreateListButton() {
        clickOnView(R.id.tv_add_task_list)
    }

    fun tapOnSubmitButtonCreateList(position: Int) {

        onView(withId(R.id.rv_task_list))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                withId(R.id.ib_done_list_name), click()))
    }

    fun tapSubmitButtonWithList(listName : String) {

    }

    fun tapOnEditButtonWithFakeList() {

    }

    fun tapOnEditButtonWithList() {

    }

}

