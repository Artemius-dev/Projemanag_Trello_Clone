package com.projemanag.robots

import android.content.Context
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.platform.app.InstrumentationRegistry
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.google.firebase.auth.FirebaseAuth
import com.projemanag.factory.UserFactory
import com.projemanag.testHelpers.TestConstants.MAIN_USER_EMAIL
import com.projemanag.testHelpers.TestConstants.MAIN_USER_PASSWORD
import com.projemanag.utils.EspressoIdlingResource
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent

//@HiltAndroidTest
open class BaseTestRobot {

//    @get: Rule
//    val hiltRule = HiltAndroidRule(this)

   lateinit var userFactory: UserFactory

    lateinit var firebaseAuth: FirebaseAuth

    var context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface MyClassInterface {
        fun getFirebaseAuth(): FirebaseAuth

        fun getUserFactory(): UserFactory
    }

    private fun setUserFactory() : UserFactory {
        val hiltEntryPoint : MyClassInterface = EntryPointAccessors.fromApplication(context, MyClassInterface::class.java)

        return hiltEntryPoint.getUserFactory()
    }

    private fun setFirebaseAuth(): FirebaseAuth {
        val hiltEntryPoint : MyClassInterface = EntryPointAccessors.fromApplication(context, MyClassInterface::class.java)

        return hiltEntryPoint.getFirebaseAuth()
    }
    init {
//        val component = TestBaseApplication_Application.builder()
//
//        component.inject(this)
//
//
//        val myClassInterface =
//            EntryPoints.get(component, MyClassInterface::class.java)
//        userFactory = myClassInterface.getUserFactory()
//        firebaseAuth = myClassInterface.getFirebaseAuth()

        userFactory = setUserFactory()

        firebaseAuth = setFirebaseAuth()
    }

    fun signIn() {
        firebaseAuth.signInWithEmailAndPassword(
            MAIN_USER_EMAIL,
            MAIN_USER_PASSWORD
        )
    }

    fun signOut() {
        EspressoIdlingResource().increment()
        val fAuth = FirebaseAuth.getInstance()
        fAuth.signOut()
        firebaseAuth.signOut()
        EspressoIdlingResource().decrement()
    }

    fun registerFakeUser() {
        userFactory.registerFakeUserMain()
    }

    fun registerSecondFakeUser() {
        userFactory.registerFakeUserSecond()
    }

    fun deleteFakeUser() {
        userFactory.deleteFakeUser()
    }

    fun resetFakeUserData() {
        userFactory.resetFakeUsersData()
    }

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