package com.projemanag.robots

import com.projemanag.MockTest
import com.projemanag.firebase.FirestoreValues
import com.projemanag.firebase.IFirestoreClass
import com.projemanag.testHelpers.FakeAuthResult

import io.mockk.coEvery
import io.mockk.every
import java.lang.Exception

fun prepare(scope: MockTest, func: PreparationRobot.() -> Unit) =
    PreparationRobot(scope).apply { func() }

class PreparationRobot(private val scope: MockTest) {

    //    private val authUser = AuthUser("111111")
//
//    fun mockLocationProvider(isLocationAvailable: Boolean = false) {
//        val provider = scope.locationProvider
//        every { provider.startLocationUpdates() } answers { nothing }
//        every { provider.stopLocationUpdates() } answers { nothing }
//        every { provider.addUpdatableLocationListener(any()) } answers { nothing }
//        every { provider.isLocationAvailable() } returns isLocationAvailable
//    }
//
//    fun mockSignUpError() {
//        val userRepository = scope.userRepository
//        coEvery { userRepository.signUp(any(), any()) } returns Result.Error(Exception("SignUp error"))
//    }
//
//    fun mockSuccessfulSignUp(name: String, email: String, password: String) {
//        val userRepository = scope.userRepository
//        coEvery { userRepository.changeUserName(authUser, name) } answers { nothing }
//        coEvery { userRepository.signUp(email, password) } returns Result.Success(authUser)
//    }
//
    fun mockNoAuthorizedUser() {
        val firestoreClass = scope.firestoreScope
        coEvery { firestoreClass.getCurrentUserID() } returns ""
    }

    //
    fun mockAuthorizedUser() {
        val firestoreClass = scope.firestoreScope
        coEvery { firestoreClass.getCurrentUserID() } returns "22222"
    }
//
//    fun mockUserSignOut() {
//        val userRepository = scope.userRepository
//        coEvery { userRepository.signOut() } answers { nothing }
//    }
//
//    fun mockSuccessfulSignIn(email: String, password: String) {
//        val firestoreClass = scope.userRepository
//        val authUser = AuthUser("111111")
//        coEvery { userRepository.signIn(email, password) } returns Result.Success(authUser)
//        coEvery { userRepository.getCurrentUser() } returns Result.Success(authUser)
//    }
//
//    fun mockUnsuccessfulSignInWithException() {
//        val userRepository = scope.userRepository
//        coEvery { userRepository.signIn(any(), any()) } returns Result.Error(Exception("SignIn error"))
//    }

}