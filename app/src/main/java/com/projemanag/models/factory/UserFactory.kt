package com.projemanag.models.factory

import android.app.Activity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.projemanag.activities.SignUpActivity
import com.projemanag.firebase.FirestoreClass
import com.projemanag.models.User
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class UserFactory
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseClass: FirestoreClass
){

    fun createUser(name: String, email: String, password: String, activity: Activity) {
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
                        "Registration failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun signOut() {
        try {
            firebaseAuth.signOut()
        }
        catch (exception: Exception) {

        }
    }
}