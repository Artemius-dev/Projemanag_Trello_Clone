package com.projemanag.factory

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.projemanag.activities.SignUpActivity
import com.projemanag.firebase.FirestoreClass
import com.projemanag.models.User
import com.projemanag.models.factory.IUserFactory
import com.projemanag.models.factory.UserFactory
import com.projemanag.utils.Constants
import com.projemanag.utils.EspressoIdlingResource
import java.lang.Exception
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class UserFactory
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firestoreClass: FirestoreClass
): IUserFactory {

    private val TAG = "UserFactory"

    val EMAIL = "antonio@gmail.com"
    val PASSWORD = "123456"

    override fun createUser(name: String, email: String, password: String, activity: Activity) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val user = User(firebaseUser.uid, name, registeredEmail)

                    firestoreClass.registerUser(activity as SignUpActivity, user)
                } else {
                    Toast.makeText(
                        activity,
                        "Registration failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun registerFakeUser() {
        EspressoIdlingResource().increment()

        firebaseAuth.createUserWithEmailAndPassword(EMAIL, PASSWORD).addOnCompleteListener {
            task ->
            if(task.isSuccessful) {
                val firebaseUser: FirebaseUser = task.result!!.user!!
                val registeredEmail = firebaseUser.email!!
                val userInfo = User(firebaseUser.uid, "Antonio Berluskoni", registeredEmail)

                firebaseFirestore.collection(Constants.USERS)
                    .document(getFakeUserUID(EMAIL, PASSWORD))
                    .set(userInfo, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d(TAG, "registerFakeUser: Success")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "registerFakeUser: ", it)
                    }
            }
        }.addOnSuccessListener {
            firebaseAuth.signOut()

            EspressoIdlingResource().decrement()
        }
    }

    fun getFakeUserUID(email: String, password: String): String {
        EspressoIdlingResource().increment()

        firebaseAuth.signInWithEmailAndPassword(email, password)

        var currentUser = firebaseAuth.currentUser

        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }

        firebaseAuth.signOut()
        EspressoIdlingResource().decrement()

        return currentUserId

    }

    fun deleteFakeUser() {
        EspressoIdlingResource().increment()
        Log.d(TAG, "deleteFakeUser: ${getFakeUserUID(EMAIL, PASSWORD)}")

        firebaseAuth.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnCompleteListener {
            task ->
            if(task.isSuccessful) {
                try {
                    Log.d(TAG, "deleteFakeUser: ${getFakeUserUID(EMAIL, PASSWORD)}")
                    firebaseFirestore.collection("users").document(getFakeUserUID(EMAIL, PASSWORD))
                        .delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                firebaseAuth.currentUser!!.delete()
                            }
                        }
                } catch (e: Exception) {
                    Log.e(TAG, "deleteFakeUser: ", e)
                }
            }
            firebaseAuth.signOut()
        }.addOnSuccessListener {
            Log.d(TAG, "deleteFakeUser: Success")
            firebaseAuth.signOut()
        }.addOnFailureListener {
            firebaseAuth.signOut()
        }
        firebaseAuth.signOut()

        EspressoIdlingResource().decrement()
    }

    override fun getCurrentUserID(): String {
//        EspressoIdlingResource().increment()
//        var currentUser = firebaseAuth.currentUser
//        EspressoIdlingResource().decrement()
//        var currentUserId = ""
//        if (currentUser != null) {
//            currentUserId = currentUser.uid
//        }

        return "1111111"
    }

    private fun getRandomId(): String {
        var random = Random().nextInt(100000)
        return random.toString()
    }
}