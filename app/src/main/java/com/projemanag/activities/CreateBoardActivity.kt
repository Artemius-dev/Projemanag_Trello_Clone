package com.projemanag.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.projemanag.R
import com.projemanag.models.Board
import com.projemanag.utils.Constants
import kotlinx.android.synthetic.main.activity_create_board.*
import java.io.IOException

private const val TAG = "CreateBoardActivity"
//@AndroidEntryPoint
class CreateBoardActivity : BaseActivity() {

    private var mSelectedImageFileUri: Uri? = null

    private lateinit var mUserName: String

    private var mBoardImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_board)
        setupActionBar()

        if (intent.hasExtra(Constants.NAME)) {
            mUserName = intent.getStringExtra(Constants.NAME)
        }
    }

    fun createBoard() {

        val boardName = et_board_name.text.toString()

        if (boardName.isNotEmpty()) {
            showProgressDialog(resources.getString(R.string.please_wait))

            val assignedUsersArrayList: ArrayList<String> = ArrayList()
            assignedUsersArrayList.add(getCurrentUserID())

            var board = Board(
                boardName,
                mBoardImageURL,
                mUserName,
                assignedUsersArrayList
            )

            firebaseFirestore.collection(Constants.BOARDS).whereEqualTo(Constants.NAME, boardName)
                .get()
                .addOnSuccessListener {
                    querySnapshot ->
                    val listDocuments = querySnapshot.documents
                    Log.d(TAG, "createBoard: ListDocuments ${listDocuments.isEmpty()}")
                    if(listDocuments.isEmpty()) {
                        boardFactory.createBoard(this, board)

                    }
                    else {
                        hideProgressDialog()

                        showErrorSnackBar(resources.getString(R.string.please_enter_another_name_for_a_board))
                    }

                }.addOnFailureListener {
                }
        } else {
            showErrorSnackBar(resources.getString(R.string.board_name_is_blank))
        }
    }

    private fun uploadBoardImage() {
        showProgressDialog(resources.getString(R.string.please_wait))

        val assignedUsersArrayList: ArrayList<String> = ArrayList()
        assignedUsersArrayList.add(getCurrentUserID())

        var board = Board(
            et_board_name.text.toString(),
            mBoardImageURL,
            mUserName,
            assignedUsersArrayList
        )

        boardFactory.uploadBoardImage(this, board, mBoardImageURL, mSelectedImageFileUri)
    }

    fun boardCreatedSuccessfully() {
        hideProgressDialog()

        setResult(Activity.RESULT_OK)

        finish()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_create_board_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.create_board_title)
        }

        toolbar_create_board_activity.setNavigationOnClickListener { onBackPressed() }
        iv_board_image.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Constants.showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }
        }

        btn_create.setOnClickListener {

            if (mSelectedImageFileUri != null) {
                uploadBoardImage()
            } else {
                createBoard()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Constants.showImageChooser(this)
            }
        } else {
            Toast.makeText(
                this,
                "Oops, you just denied the permission for storage. " +
                        "You ca also allow it from settings.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK &&
            requestCode == Constants.PICK_IMAGE_REQUEST_CODE &&
            data!!.data != null
        ) {
            mSelectedImageFileUri = data.data

            try {
                Glide
                    .with(this)
                    .load(mSelectedImageFileUri)
                    .centerCrop()
                    .placeholder(R.drawable.ic_board_place_holder)
                    .into(iv_board_image)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
