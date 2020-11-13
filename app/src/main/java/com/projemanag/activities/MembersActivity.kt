
package com.projemanag.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projemanag.R
import com.projemanag.models.Board
import com.projemanag.utils.Constants
import kotlinx.android.synthetic.main.activity_members.*
import kotlinx.android.synthetic.main.activity_my_profile.*

class MembersActivity : AppCompatActivity() {

    private lateinit var mBoardDetails: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)

        if (intent.hasExtra((Constants.BOARD_DETAIL))) {
            mBoardDetails = intent.getParcelableExtra<Board>(Constants.BOARD_DETAIL)
        }

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_members_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.members)
        }

        toolbar_members_activity.setNavigationOnClickListener { onBackPressed() }
    }
}
