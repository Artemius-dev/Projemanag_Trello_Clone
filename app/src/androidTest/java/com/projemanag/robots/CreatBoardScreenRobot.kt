package com.projemanag.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.projemanag.R
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElement
import com.projemanag.testHelpers.TestConstants
import com.projemanag.testHelpers.TestConstants.FAKE_BOARD_NAME
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.startsWith


fun createBoardScreen(func: CreatBoardScreenRobot.() -> Unit) = CreatBoardScreenRobot().apply { func() }

class CreatBoardScreenRobot : BaseTestRobot() {

    fun tapOnCreateButton() {
        clickOnView(R.id.btn_create)
    }

    fun enterNameOfBoard(boardName : String) {
        onView(withId(R.id.et_board_name)).perform(replaceText(boardName))
    }

    fun enterFakeNameOfBoard() {
        onView(withId(R.id.et_board_name)).perform(replaceText(FAKE_BOARD_NAME))
    }

    fun verifyErrorSameName() {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(context.resources.getString(R.string.please_enter_another_name_for_a_board))))
    }

    fun verifyErrorBlankName() {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(context.resources.getString(R.string.board_name_is_blank))))
    }

}