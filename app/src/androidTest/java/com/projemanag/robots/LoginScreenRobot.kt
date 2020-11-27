package com.projemanag.robots

import com.agoda.kakao.intent.KIntent
import com.projemanag.R
import com.projemanag.activities.SignInActivity

fun introScreen(func: LoginScreenRobot.() -> Unit) = LoginScreenRobot().apply { func() }

class LoginScreenRobot : BaseTestRobot() {

    fun openSignIn() {
        clickOnView(R.id.btn_sign_in_intro)
    }

    fun openSignUp() {
        clickOnView(R.id.btn_sign_up_intro)
    }

    fun isSuccessfullyLoaded() {
        KIntent {
            hasComponent(SignInActivity::class.java.name)
        }
    }
}