package com.projemanag.conditionwatcher

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.azimolabs.conditionwatcher.ConditionWatcher
import com.azimolabs.conditionwatcher.Instruction
import com.projemanag.R
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.Matcher

/**
 * Single place for all the [ConditionWatcher] functions used in sample project.
 */
object ConditionWatchers {

    private val TIMEOUT_LIMIT = 20000
    private val WATCH_INTERVAL = 400
    private val exceptionList: List<String>? = null

    init {
        ConditionWatcher.setTimeoutLimit(TIMEOUT_LIMIT)
        ConditionWatcher.setWatchInterval(400)
    }

    /**
     * Waits until the [] finishes search query.
     * Check if expectedAmountOfResults is shown.
     */
    @Throws(Exception::class)
    @JvmStatic
    fun snackbarGone() {
        ConditionWatcher.waitForCondition(object : Instruction() {
            override fun getDescription(): String {
                return "Condition todoListSnackbarGone"
            }

            override fun checkCondition(): Boolean {
                try {
                    onView(anyOf(withText(startsWith("Please enter")), withText(startsWith("No such")))).check(
                        matches(isDisplayed()))
                    return false
                } catch (e: Exception) {
                    return true
                }

            }
        })
    }

    /**
     * Waits for a [View], located by [ViewInteraction], to be displayed on the screen.
     *
     * @param interaction - [ViewInteraction] that locatea a view.
     * @param timeout - amount of time in milliseconds to wait for condition.
     */
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

    /**
     * Waits for a [View], located by [ViewInteraction] to be fully visible on the screen.
     *
     * @param interaction - [ViewInteraction] that locates a view.
     * @param timeout - amount of time in milliseconds to wait for condition.
     */
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

    /**
     * Waits for a [View], located by [ViewInteraction], to be gone.
     *
     * @param interaction - [ViewInteraction] that locates a view.
     * @param timeout - amount of time in milliseconds to wait for condition.
     * @return [ViewInteraction] for located view.
     */
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

    /**
     * Waits for [View], located by Matcher<View> to be gone.
     *
     * @param viewMatcher - Matcher<View> that locates a view.
     * @param timeout - amount of time in milliseconds to wait for condition.
     */
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

    /**
     * Waits for View, located by Matcher<View>, to be gone.
     *
     * @param interaction - Matcher<View> that locates a view.
     * @param timeout - amount of time in milliseconds to wait for condition.
     * @return usually ViewInteraction of the parent of the view that should be gone.
     */
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
