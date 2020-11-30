package com.projemanag

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.projemanag.activities.SplashActivity
import com.projemanag.conditionwatcher.ConditionWatchers.waitForElement
import com.projemanag.di.TestAppComponent
import com.projemanag.matchers.ToastMatcher
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.startsWith
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LogInTest() : BaseTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(SplashActivity::class.java)

    @get: Rule
    var chain = RuleChain.outerRule(clearPreferencesRule)
        .around(mActivityTestRule)

    val EMAIL = "artem@gmail.com"
    val PASSWORD = "123456"

//    @MockK
//    var mMockAuth: FirestoreClass? = null
//
//    @Before
//    fun setUp() {
//        MockKAnnotations.init(this, relaxUnitFun = true)
//    }

    @Test
    fun logIn_without_error() {

        waitForElement(onView(withId(R.id.btn_sign_in_intro)), 10000).perform(click())
        waitForElement(onView(withId(R.id.et_email_sign_in))).perform(typeText(EMAIL), closeSoftKeyboard())
        waitForElement(onView(withId(R.id.et_password_sign_in))).perform(typeText(PASSWORD), closeSoftKeyboard())
        waitForElement(onView(withId(R.id.btn_sign_in))).perform(click())
        waitForElement(onView(withText(startsWith("Profile Data"))).inRoot(allOf(ToastMatcher(), not(isDialog()))).check(
            matches(isDisplayed())), 15000)
    }

    @Test
    fun logIn_with_error() {
        waitForElement(onView(withId(R.id.btn_sign_in_intro)), 10000).perform(click())
        waitForElement(onView(withId(R.id.et_email_sign_in))).perform(typeText(EMAIL), closeSoftKeyboard())
        waitForElement(onView(withId(R.id.btn_sign_in))).perform(click())
        waitForElement(onView(
            anyOf(
                withText(startsWith("Please enter")),
                withText(startsWith("No such"))
            )
        )).check(
            matches(isDisplayed()))
    }

    override fun injectTest() {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }
}