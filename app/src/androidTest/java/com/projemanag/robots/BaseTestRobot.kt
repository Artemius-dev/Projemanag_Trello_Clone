package com.projemanag.robots

import android.content.Context
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.platform.app.InstrumentationRegistry
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.google.firebase.auth.FirebaseAuth
import com.projemanag.MyCustomComponentBuilder
import com.projemanag.TestBaseApplication
import com.projemanag.TestBaseApplication_Application
import com.projemanag.factory.UserFactory
import com.projemanag.utils.Constants
import com.projemanag.utils.EspressoIdlingResource
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import javax.inject.Inject

//@HiltAndroidTest
open class BaseTestRobot
@Inject
constructor(
    val componentBuilder: MyCustomComponentBuilder
){

    constructor()

//    @get: Rule
//    val hiltRule = HiltAndroidRule(this)

    var userFactory: UserFactory

    var firebaseAuth: FirebaseAuth

    var context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface MyClassInterface {
        fun getFirebaseAuth(): FirebaseAuth

        fun getUserFactory(): UserFactory
    }

    init {
//        val component = TestBaseApplication_Application.builder()
//
//        component.inject(this)

        val component = componentBuilder.build()

        val myClassInterface =
            EntryPoints.get(component, MyClassInterface::class.java)
        userFactory = myClassInterface.getUserFactory()
        firebaseAuth = myClassInterface.getFirebaseAuth()
    }

    fun signIn() {
        userFactory.registerFakeUser()
        firebaseAuth.signInWithEmailAndPassword(
            EMAIL,
            PASSWORD
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
        userFactory.registerFakeUser()
    }

    fun deleteFakeUser() {
        userFactory.deleteFakeUser()
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

    companion object {
        const val EMAIL = "antonio@gmail.com"
        const val PASSWORD = "123456"
        const val NAME = "Antonio Berluskoni"
    }

}