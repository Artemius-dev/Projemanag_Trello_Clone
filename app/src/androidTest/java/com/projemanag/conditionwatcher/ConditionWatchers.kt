package com.projemanag.conditionwatcher

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.azimolabs.conditionwatcher.ConditionWatcher
import com.azimolabs.conditionwatcher.Instruction
import org.hamcrest.Matcher

object ConditionWatchers {

    private val TIMEOUT_LIMIT = 20000
    private val WATCH_INTERVAL = 400
    private val exceptionList: List<String>? = null

    init {
        ConditionWatcher.setTimeoutLimit(TIMEOUT_LIMIT)
        ConditionWatcher.setWatchInterval(400)
    }

    @Throws(Exception::class)
    @JvmStatic
    fun waitForElement(
        interaction: ViewInteraction,
        timeout: Int = 5000): ViewInteraction {
        ConditionWatcher.setTimeoutLimit(timeout)
        ConditionWatcher.waitForCondition(object : Instruction() {

            override fun getDescription(): String {
                return "waitForElement"
            }

            override fun checkCondition(): Boolean {
                try {
                    interaction.check(matches(isDisplayed()))
                    return true
                } catch (ex: NoMatchingViewException) {
                    return false
                }

            }
        })
        return interaction
    }

    @Throws(Exception::class)
    fun waitForElementFullyVisible(
            interaction: ViewInteraction,
            timeout: Int): ViewInteraction {
        ConditionWatcher.setTimeoutLimit(timeout)
        ConditionWatcher.waitForCondition(object : Instruction() {

            override fun getDescription(): String {
                return "waitForElementFullyVisible"
            }

            override fun checkCondition(): Boolean {
                return try {
                    interaction.check(matches(isDisplayed()))
                    true
                } catch (ex: NoMatchingViewException) {
                    false
                }

            }
        })
        return interaction
    }

    @Throws(Exception::class)
    @JvmStatic
    fun waitForElementIsGone(
            interaction: ViewInteraction,
            timeout: Int = 5000): ViewInteraction {
        ConditionWatcher.setTimeoutLimit(timeout)
        ConditionWatcher.waitForCondition(object : Instruction() {

            override fun getDescription(): String {
                return "waitForElementIsGone"
            }

            override fun checkCondition(): Boolean {
                try {
                    interaction.check(matches(isDisplayed()))
                    return false
                } catch (ex: NoMatchingViewException) {
                    return true
                }

            }
        })
        return interaction
    }

    @Throws(Exception::class)
    @JvmStatic
    fun waitForElementIsGone(
            viewMatcher: Matcher<View>,
            timeout: Int = 5000) {
        ConditionWatcher.setTimeoutLimit(timeout)
        ConditionWatcher.waitForCondition(object : Instruction() {

            override fun getDescription(): String {
                return "waitForElementIsGone"
            }

            override fun checkCondition(): Boolean {
                try {
                    onView(viewMatcher).check(matches(isDisplayed()))
                    return false
                } catch (ex: NoMatchingViewException) {
                    return true
                }

            }
        })
    }

    @Throws(Exception::class)
    fun waitForElementIsGone(
            interaction: ViewInteraction,
            viewMatcher: Matcher<View>,
            timeout: Int): ViewInteraction {
        ConditionWatcher.setTimeoutLimit(timeout)
        ConditionWatcher.waitForCondition(object : Instruction() {

            override fun getDescription(): String {
                return "waitForElementIsGone"
            }

            override fun checkCondition(): Boolean {
                try {
                    onView(viewMatcher).check(matches(isDisplayed()))
                    return false
                } catch (ex: NoMatchingViewException) {
                    return true
                }

            }
        })
        return interaction
    }
}
