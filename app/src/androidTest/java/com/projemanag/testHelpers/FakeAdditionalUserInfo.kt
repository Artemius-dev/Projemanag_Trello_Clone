package com.projemanag.testHelpers

import android.os.Parcel
import com.google.firebase.auth.AdditionalUserInfo

class FakeAdditionalUserInfo  // Singleton
private constructor() : AdditionalUserInfo {
    override fun getProviderId(): String? {
        return null
    }

    override fun getProfile(): Map<String, Any>? {
        return null
    }

    override fun getUsername(): String? {
        return null
    }

    override fun isNewUser(): Boolean {
        return false
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        throw IllegalStateException("Don't try to parcel FakeAuthResult!")
    }

    companion object {
        val INSTANCE: AdditionalUserInfo = FakeAdditionalUserInfo()
    }
}