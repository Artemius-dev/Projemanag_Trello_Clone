package com.projemanag.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.projemanag.BaseApplication
import com.projemanag.R
import com.projemanag.firebase.FirestoreClass
import com.projemanag.models.User
import com.projemanag.models.factory.UserFactory
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class SignUpActivity : BaseActivity() {

    @Inject
    lateinit var userFactory: UserFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupActionBar()

        (application as BaseApplication).appComponent.inject(this)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun userRegisteredSuccess() {

        Toast.makeText(
            this,
            "you have " +
                "succesfully registerd the email " +
                "address",
            Toast.LENGTH_LONG
        ).show()
        hideProgressDialog()

        FirebaseAuth.getInstance().signOut()
        finish()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_sign_up_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }

        btn_sign_up.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            userFactory.createUser(name, email, password, this)
        }
    }

    fun registerUserFromJson(file: String?) {

    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter an email")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> {
                true
            }
        }
    }
}
