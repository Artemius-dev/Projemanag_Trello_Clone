package com.projemanag.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.projemanag.BaseApplication
import com.projemanag.R
import com.projemanag.di.DaggerAppComponent
import com.projemanag.di.ProductionModule
import com.projemanag.models.factory.UserFactory
import com.projemanag.utils.Constants
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var userFactory: UserFactory

    private val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val component = DaggerAppComponent.builder().productionModule(ProductionModule(BaseApplication())).build()

        component.inject(this)

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

                val currentUserId = userFactory.getCurrentUserID()
                Log.d(TAG, "SplashActivity: $currentUserId")

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
