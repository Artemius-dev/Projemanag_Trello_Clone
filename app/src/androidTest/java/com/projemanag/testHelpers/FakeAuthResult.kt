package com.projemanag.testHelpers

import android.os.Parcel
import com.google.firebase.auth.AdditionalUserInfo
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class FakeAuthResult  // Singleton
    ()  {
//    private var mUser: FirebaseUser? = null
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    override fun getAdditionalUserInfo(): AdditionalUserInfo? = FakeAdditionalUserInfo.INSTANCE
//
//    override fun writeToParcel(parcel: Parcel?, i: Int) {
//        throw IllegalStateException("Don't try to parcel FakeAuthResult!")
//    }
//
//    override fun getUser(): FirebaseUser? {
//        if (mUser == null) {
//            mUser = getMockFirebaseUser()
//        }
//        return mUser
//    }
//
//    override fun getCredential(): AuthCredential? =
//        OAuthProvider.newCredentialBuilder("provider")
//        .setAccessToken("foo")
//        .setIdToken("bar")
//        .build()
//
//    companion object {
//        val INSTANCE: AuthResult = FakeAuthResult()
//    }
//
//    fun getMockFirebaseUser(): FirebaseUser? {
//        val user: FirebaseUser = mockk<FirebaseUser>()
//        every { user.uid } returns TestConstants.UID
//        every { user.email } returns TestConstants.EMAIL
//        every { user.displayName } returns TestConstants.NAME
//        every { user.photoUrl } returns TestConstants.PHOTO_URI
//        return user
//    }
//
//    fun getCurrentUserID() : String {
//        return TestConstants.UID
//    }

}