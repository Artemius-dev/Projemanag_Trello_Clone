package com.projemanag.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.projemanag.BaseApplication
import com.projemanag.R
import com.projemanag.models.factory.UserFactory
import com.projemanag.utils.Constants
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var userFactory: UserFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        (application as BaseApplication).appComponent.inject(this)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Change original Font
        val typeface: Typeface =
            Typeface.createFromAsset(assets, "carbon bl.ttf")
        tv_app_name.typeface = typeface

        // Need to go from this Activity to IntroActivity
        Handler().postDelayed(
            {
//                userFactory.signOut()

                val currentUserId = firestoreClass.getCurrentUserID()

                if (currentUserId.isNotEmpty()) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, IntroActivity::class.java))
                }

                finish()
            },
            Constants.SPLASH_SCREEN_DELAY
        )
    }
}
