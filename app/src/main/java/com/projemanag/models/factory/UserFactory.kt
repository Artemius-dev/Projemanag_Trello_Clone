package com.projemanag.models.factory

import android.app.Activity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.projemanag.R
import com.projemanag.activities.SignUpActivity
import com.projemanag.firebase.FirestoreClass
import com.projemanag.models.User
import com.projemanag.utils.EspressoIdlingResource
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
open class UserFactory
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseClass: FirestoreClass
) : IUserFactory {

    override fun createUser(name: String, email: String, password: String, activity: Activity) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val user = User(firebaseUser.uid, name, registeredEmail)

                    firebaseClass.registerUser(activity as SignUpActivity, user)
                } else {
                    Toast.makeText(
                        activity,
                        activity.resources.getString(R.string.register_fail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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

}