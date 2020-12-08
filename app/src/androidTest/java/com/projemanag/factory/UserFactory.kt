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
import com.projemanag.testHelpers.TestConstants
import com.projemanag.testHelpers.TestConstants.MAIN_USER_EMAIL
import com.projemanag.testHelpers.TestConstants.MAIN_USER_PASSWORD
import com.projemanag.testHelpers.TestConstants.SECOND_USER_EMAIL
import com.projemanag.testHelpers.TestConstants.SECOND_USER_PASSWORD
import com.projemanag.utils.Constants
import com.projemanag.utils.EspressoIdlingResource
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class UserFactory
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firestoreClass: FirestoreClass
) : IUserFactory {

    private val TAG = "UserFactory"

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

    fun registerFakeUserMain() {
        EspressoIdlingResource().increment()
        firebaseAuth.createUserWithEmailAndPassword(MAIN_USER_EMAIL, MAIN_USER_PASSWORD)
            .addOnCompleteListener { task ->
                Log.d(TAG, "registerFakeUser: ${task.isSuccessful}")
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val userInfo =
                        User(firebaseUser.uid, TestConstants.MAIN_USER_NAME, registeredEmail)

                    EspressoIdlingResource().increment()

                    firebaseFirestore.collection(Constants.USERS)
                        .document(firebaseUser.uid)
                        .set(userInfo, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d(TAG, "registerFakeUser: Success")
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "registerFakeUser: ", it)
                        }

                    EspressoIdlingResource().decrement()
                }
                EspressoIdlingResource().increment()

                firebaseAuth.signOut()

                EspressoIdlingResource().decrement()
            }

    }

    fun registerFakeUserSecond() {
        EspressoIdlingResource().increment()
        firebaseAuth.createUserWithEmailAndPassword(SECOND_USER_EMAIL, SECOND_USER_PASSWORD)
            .addOnCompleteListener { task ->
                Log.d(TAG, "registerFakeUser: ${task.isSuccessful}")
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val userInfo =
                        User(firebaseUser.uid, TestConstants.SECOND_USER_NAME, registeredEmail)

                    EspressoIdlingResource().increment()

                    firebaseFirestore.collection(Constants.USERS)
                        .document(firebaseUser.uid)
                        .set(userInfo, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d(TAG, "registerFakeUser: Success")
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "registerFakeUser: ", it)
                        }

                    EspressoIdlingResource().decrement()
                }
                EspressoIdlingResource().increment()

                firebaseAuth.signOut()

                EspressoIdlingResource().decrement()
            }
    }

    fun getFakeUserUID(email: String, password: String): String {
        EspressoIdlingResource().increment()

        var currentUserId = ""

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            var currentUser = firebaseAuth.currentUser


            if (currentUser != null) {
                currentUserId = currentUser.uid
            }

            firebaseAuth.signOut()
            EspressoIdlingResource().decrement()

        }.addOnSuccessListener {
            Log.d(TAG, "c: Success $currentUserId")
        }
        return currentUserId
    }

    fun deleteFakeUser() {
        EspressoIdlingResource().increment()
        var fakeUserUID = ""

        // TODO Implement Coroutines andd add this to separate method
        firebaseAuth.signInWithEmailAndPassword(MAIN_USER_EMAIL, MAIN_USER_PASSWORD).addOnCompleteListener {

            var currentUser = firebaseAuth.currentUser


            if (currentUser != null) {
                fakeUserUID = currentUser.uid
            }

            firebaseAuth.signOut()
            EspressoIdlingResource().decrement()

        }.addOnSuccessListener {
            Log.d(TAG, "c: Success $fakeUserUID")
        }
        // TODO

        Log.d(TAG, "fakeUserUID: ${fakeUserUID}")

        firebaseAuth.signInWithEmailAndPassword(MAIN_USER_EMAIL, MAIN_USER_PASSWORD).addOnCompleteListener { task ->
            Log.d(TAG, "deleteFakeUser: ${fakeUserUID}; ${task.isSuccessful}")
            if (task.isSuccessful) {
                try {
                    firebaseFirestore.collection(Constants.USERS).document(fakeUserUID)
                        .delete()
                        .addOnCompleteListener {
                            firebaseAuth.currentUser!!.delete()
                            Log.d(TAG, "deleteFakeUser: Deleted Success")
                        }
                } catch (e: Exception) {
                    Log.e(TAG, "deleteFakeUser: ", e)
                }
            }
        }
        firebaseAuth.signOut()

        EspressoIdlingResource().decrement()
    }

    override fun getCurrentUserID(): String {
        EspressoIdlingResource().increment()
        var currentUser = firebaseAuth.currentUser
        EspressoIdlingResource().decrement()
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }

        return currentUserId
    }

    fun resetFakeUsersData() {

        val userHashMap = HashMap<String, Any>()
        userHashMap[Constants.NAME] = TestConstants.MAIN_USER_NAME
        firebaseAuth.signInWithEmailAndPassword(MAIN_USER_EMAIL, MAIN_USER_PASSWORD).addOnCompleteListener {

            firebaseAuth.uid?.let { it1 -> firebaseFirestore.collection(Constants.USERS).document(it1).update(userHashMap) }

            EspressoIdlingResource().decrement()

        }.addOnSuccessListener {
            Log.d(TAG, "User Updated: Success ")
        }

        firebaseFirestore.collection(Constants.BOARDS).whereEqualTo(Constants.CREATED_BY, TestConstants.MAIN_USER_NAME).get().addOnSuccessListener {
            querySnapshot ->
            val listOfDocuments = querySnapshot.documents
            for(document in listOfDocuments) {
                firebaseFirestore.collection(Constants.BOARDS).document(document.id).delete()
            }
        }

        firebaseAuth.signOut()

        firebaseAuth.createUserWithEmailAndPassword(SECOND_USER_EMAIL, SECOND_USER_PASSWORD)
            .addOnCompleteListener { task ->
                Log.d(TAG, "registerFakeUser: ${task.isSuccessful}")
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val userInfo =
                        User(firebaseUser.uid, TestConstants.SECOND_USER_NAME, registeredEmail)

                    EspressoIdlingResource().increment()

                    firebaseFirestore.collection(Constants.USERS)
                        .document(firebaseUser.uid)
                        .set(userInfo, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d(TAG, "registerFakeUser: Success")
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "registerFakeUser: ", it)
                        }

                    EspressoIdlingResource().decrement()
                }
            }

    }
}