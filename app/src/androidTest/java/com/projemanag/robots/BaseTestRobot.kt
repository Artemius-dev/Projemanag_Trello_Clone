package com.projemanag.robots

import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.projemanag.matchers.RecyclerViewMatchers

open class BaseTestRobot {

    fun enterText(viewId: Int, text: String) {
        val view = KEditText { withId(viewId) }
        view.perform {
            replaceText(text)
            closeSoftKeyboard()
        }
    }

    fun clickOnView(viewId: Int) {
        val view = KView { withId(viewId) }
        view.click()
    }

    fun clickOnViewWithText(textId: Int) {
        val view = KView {
            withText(textId)
            isDisplayed()
        }
        view.click()
    }

    fun changeSelectedSpinnerItemPosition(text: String) {
        val view = KView { withText(text) }
        view.click()
    }

    fun isTextDisplayed(textId: Int) {
        val view = KView { withText(textId) }
        view.isDisplayed()
    }

    fun isViewDisplayed(viewId: Int) {
        val view = KView { withId(viewId) }
        view.isDisplayed()
    }

    fun isViewWithTextDisplayed(viewId: Int, text: String) {
        val view = KView {
            withId(viewId)
            withText(text)
        }
        view.isDisabled()
    }

    fun isViewWithTextDisplayed(viewId: Int, textId: Int) {
        val view = KView {
            withId(viewId)
            withText(textId)
        }
        view.isDisplayed()
    }

    fun isViewHintDisplayed(viewId: Int, textId: Int) {
        val view = KView {
            withId(viewId)
            withHint(textId)
        }
        view.isDisplayed()
    }

    fun isSpinnerHasText(viewId: Int, textId: Int) {
        val view = KView {
            withId(viewId)
            withSpinnerText(textId)
        }
        view.isDisplayed()
    }

    fun isViewEnabled(viewId: Int) {
        val view = KView { withId(viewId) }
        view.isEnabled()
    }

    fun isViewDisabled(viewId: Int) {
        val view = KView { withId(viewId) }
        view.isDisabled()
    }

//    fun isRecyclerViewHasItemWithText(viewId: Int, text: String) {
//        val view = KView { withId(viewId) }
//        view.matches { RecyclerViewMatchers.withItemText(text) }
//    }
//
//    fun isRecyclerViewItemCount(viewId: Int, itemCount: Int) {
//        val view = KView {
//            withId(viewId)
//        }
//        view.matches {
//            RecyclerViewMatchers.withItemCount(itemCount)
//        }
//    }

}