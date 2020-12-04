package com.projemanag.activities

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.projemanag.BaseApplication
import com.projemanag.R
import com.projemanag.di.DaggerAppComponent
import com.projemanag.di.ProductionModule
import com.projemanag.firebase.FirestoreClass
import com.projemanag.models.factory.TaskFactory
import com.projemanag.models.factory.UserFactory
import com.projemanag.utils.EspressoIdlingResource
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.dialog_progress.*
import java.lang.reflect.Field
import java.lang.reflect.Method
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    private lateinit var mProgressDialog: Dialog

    private val TAG = "BaseActivity"

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firestoreClass: FirestoreClass

    @Inject
    lateinit var taskFactory: TaskFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val component = DaggerAppComponent.builder().productionModule(ProductionModule(BaseApplication())).build()

        component.inject(this)
    }

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(this)

        /* Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.
         */
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.tv_progress_text.text = text

        // Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    fun getCurrentUserID(): String {
        EspressoIdlingResource().increment()
        var currentUser = firebaseAuth.currentUser
        EspressoIdlingResource().decrement()
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }

        return currentUserId
    }

    fun showErrorSnackBar(message: String) {
        val snackBar = Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        )
        val snacBarView = snackBar.view
        snacBarView.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.snackbar_error_color
            )
        )
        snackBar.show()
    }
}
