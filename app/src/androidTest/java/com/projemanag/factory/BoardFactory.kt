package com.projemanag.factory

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.projemanag.activities.BaseActivity
import com.projemanag.activities.CreateBoardActivity
import com.projemanag.firebase.FirestoreClass
import com.projemanag.models.Board
import com.projemanag.models.Task
import com.projemanag.utils.Constants
import com.projemanag.utils.EspressoIdlingResource
import javax.inject.Inject

class BoardFactory
@Inject
constructor(val firestoreClass: FirestoreClass) {

    fun createBoard(activity: CreateBoardActivity,
                    board: Board) {

        firestoreClass.createBoard(activity, board)
    }

    fun createFakeBoard(boardName: String = "", image: String = "",
                        createdBy: String = "", assignedTo: ArrayList<String> = ArrayList<String>(),
                        documentId: String = "", taskList: ArrayList<Task> = ArrayList<Task>()) {
        EspressoIdlingResource().increment()

        val board = Board(boardName, image, createdBy, assignedTo, documentId, taskList)

        firestoreClass.mFireStore.collection(Constants.BOARDS)
            .document()
            .set(board, SetOptions.merge())

        EspressoIdlingResource().decrement()
    }

    fun uploadBoardImage(activity: CreateBoardActivity,
                         board: Board,
                         boardImageURL: String,
                         mSelectedImageFileUri: Uri?) {

        var mBoardImageURL = boardImageURL

        val sRef: StorageReference =
            FirebaseStorage.getInstance().reference.child(
                "BOARD_IMAGE" + System.currentTimeMillis() + "." +
                        Constants.getFileExtension(activity, mSelectedImageFileUri)
            )

        sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener { taskSnapshot ->
            Log.e(
                "Board Image URL",
                taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
            )

            taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                Log.i("Downloadable Image URL", uri.toString())
                mBoardImageURL = uri.toString()

                val boardWithImage = Board(
                    board.name,
                    mBoardImageURL,
                    board.createdBy,
                    board.assignedTo
                )

                firestoreClass.createBoard(activity, boardWithImage)
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(
                activity,
                exception.message,
                Toast.LENGTH_LONG
            ).show()

            BaseActivity().hideProgressDialog()
        }
    }

}