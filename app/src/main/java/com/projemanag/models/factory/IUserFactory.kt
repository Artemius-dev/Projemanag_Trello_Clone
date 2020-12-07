package com.projemanag.models.factory

import android.app.Activity

interface IUserFactory {
    open fun createUser(name: String, email: String, password: String, activity: Activity)

    open fun getCurrentUserID(): String
}