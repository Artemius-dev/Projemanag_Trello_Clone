package com.projemanag.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.projemanag.activities.CardDetailsActivity
import com.projemanag.activities.CreateBoardActivity
import com.projemanag.activities.MainActivity
import com.projemanag.activities.MembersActivity
import com.projemanag.activities.MyProfileActivity
import com.projemanag.activities.SignInActivity
import com.projemanag.activities.SignUpActivity
import com.projemanag.activities.TaskListActivity
import com.projemanag.models.Board
import com.projemanag.models.User
import com.projemanag.utils.Constants
import com.projemanag.utils.EspressoIdlingResource
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "FirestoreClass"

open class FirestoreClass
@Inject
constructor(val mFireStore: FirebaseFirestore, val mFireStoreAuth: FirebaseAuth) : IFirestoreClass{

     override fun registerUser(activity: SignUpActivity, userInfo: User) {
        EspressoIdlingResource().increment()

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                EspressoIdlingResource().decrement()

                activity.userRegisteredSuccess()
            }.addOnFailureListener { e ->
                EspressoIdlingResource().decrement()

                Log.e(activity.javaClass.simpleName, "Error writing document", e)
            }
    }

     override fun getBoardDetails(activity: TaskListActivity, documentId: String) {
        EspressoIdlingResource().increment()

        mFireStore.collection(Constants.BOARDS)
            .document(documentId)
            .get()
            .addOnSuccessListener {
                document ->
                EspressoIdlingResource().decrement()

                Log.i(activity.javaClass.simpleName, document.toString())
                val board = document.toObject(Board::class.java)!!
                board.documentId = document.id
                activity.boardDetails(board)
            }.addOnFailureListener { e ->
                EspressoIdlingResource().decrement()

                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board", e)
            }
    }

     override fun createBoard(activity: CreateBoardActivity, board: Board) {
        EspressoIdlingResource().increment()

        mFireStore.collection(Constants.BOARDS)
            .document()
            .set(board, SetOptions.merge())
            .addOnSuccessListener {
                EspressoIdlingResource().decrement()

                Log.e(activity.javaClass.simpleName, "Board created successfully.")

                Toast.makeText(
                    activity,
                    "Board created successfully.",
                    Toast.LENGTH_SHORT
                ).show()
                activity.boardCreatedSuccessfully()
            }.addOnFailureListener {
                exception ->
                EspressoIdlingResource().decrement()

                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    exception

                )
            }
    }

     override fun getBoardsList(activity: MainActivity) {
        EspressoIdlingResource().increment()

        mFireStore.collection(Constants.BOARDS)
            .whereArrayContains(Constants.ASSIGNED_TO, getCurrentUserID())
            .get()
            .addOnSuccessListener {
                document ->
                EspressoIdlingResource().decrement()

                Log.i(activity.javaClass.simpleName, document.documents.toString())
                val boardList: ArrayList<Board> = ArrayList()
                for (i in document.documents) {
                    val board = i.toObject(Board::class.java)!!
                    board.documentId = i.id
                    boardList.add(board)
                }

                activity.populateBoardsListToUI(boardList)
            }.addOnFailureListener { e ->
                EspressoIdlingResource().decrement()

                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board", e)
            }
    }

     override fun addUpdateTaskList(activity: Activity, board: Board) {
        val taskListHashMap = HashMap<String, Any>()
        taskListHashMap[Constants.TASK_LIST] = board.taskList

        EspressoIdlingResource().increment()

        mFireStore.collection(Constants.BOARDS)
            .document(board.documentId)
            .update(taskListHashMap)
            .addOnSuccessListener {
                EspressoIdlingResource().decrement()

                Log.e(activity.javaClass.simpleName, "TaskList update successfully.")

                if (activity is TaskListActivity)
                    activity.addUpdateTaskListSuccess()
                else if (activity is CardDetailsActivity)
                    activity.addUpdateTaskListSuccess()
            }.addOnFailureListener {
                exception ->
                EspressoIdlingResource().decrement()

                if (activity is TaskListActivity)
                    activity.hideProgressDialog()
                else if (activity is CardDetailsActivity)
                    activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board", exception)
            }
    }

     override fun updateUserProfileData(
        activity: Activity,
        userHashMap: HashMap<String, Any>
    ) {
        EspressoIdlingResource().increment()

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                EspressoIdlingResource().decrement()

                Log.i(activity.javaClass.simpleName, "Profile Data updated successfully!")
                Toast.makeText(
                    activity,
                    "Profile Data updated successfully!",
                    Toast.LENGTH_SHORT
                ).show()
                when (activity) {
                    is MainActivity -> {
                        activity.tokenUpdateSuccess()
                    }
                    is MyProfileActivity -> {
                        activity.profileUpdateSuccess()
                    }
                }
            }.addOnFailureListener {
                e ->
                EspressoIdlingResource().decrement()

                when (activity) {
                    is MainActivity -> {
                        activity.hideProgressDialog()
                    }
                    is MyProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
                Toast.makeText(
                    activity,
                    "Error when updating the profile!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
    }

     override fun loadUserData(activity: Activity, readBoardsList: Boolean) {
        EspressoIdlingResource().increment()

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                EspressoIdlingResource().decrement()

                Log.d(TAG, "loadUserData: ${document.data}")
                val loggedInUser = document.toObject(User::class.java)!!

                when (activity) {
                    is SignInActivity -> {
                        activity.sigInSuccess(loggedInUser)
                    }
                    is MainActivity -> {
                        activity.updateNavigationUserDetails(loggedInUser, readBoardsList)
                    }
                    is MyProfileActivity -> {
                        activity.setUserDataInUI(loggedInUser)
                    }
                }
            }.addOnFailureListener { e ->
                EspressoIdlingResource().decrement()

                when (activity) {
                    is SignInActivity -> {
                        activity.hideProgressDialog()
                    }
                    is MainActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e(activity.javaClass.simpleName, "Error writing document", e)
            }
    }

     override fun getCurrentUserID(): String {
        EspressoIdlingResource().increment()
        var currentUser = mFireStoreAuth.currentUser
        EspressoIdlingResource().decrement()
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }

        return currentUserId
    }

     override fun getAssignedMembersListDetails(activity: Activity, assignedTo: ArrayList<String>) {
        EspressoIdlingResource().increment()

        mFireStore.collection(Constants.USERS)
            .whereIn(Constants.ID, assignedTo)
            .get()
            .addOnSuccessListener {
                document ->
                EspressoIdlingResource().decrement()

                Log.e(activity.javaClass.simpleName, document.documents.toString())

                val usersList: ArrayList<User> = ArrayList()

                for (i in document.documents) {
                    val user = i.toObject(User::class.java)!!
                    usersList.add(user)
                }

                if (activity is MembersActivity)
                    activity.setupMembersList(usersList)
                else if (activity is TaskListActivity)
                    activity.boardMembersDetailsList(usersList)
            }.addOnFailureListener { e ->
                EspressoIdlingResource().decrement()

                if (activity is MembersActivity)
                    activity.hideProgressDialog()
                else if (activity is TaskListActivity)
                    activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
            }
    }

     override fun getMemberDetails(activity: MembersActivity, email: String) {
        EspressoIdlingResource().increment()

        mFireStore.collection(Constants.USERS)
            .whereEqualTo(Constants.EMAIL, email)
            .get()
            .addOnSuccessListener {
                document ->
                EspressoIdlingResource().decrement()

                if (document.documents.size > 0) {
                    val user = document.documents[0].toObject(User::class.java)!!
                    activity.memberDetails(user)
                } else {
                    activity.hideProgressDialog()
                    activity.showErrorSnackBar("No such member found")
                }
            }.addOnFailureListener { e ->
                EspressoIdlingResource().decrement()

                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details",
                    e
                )
            }
    }

     override fun assignMemberToBoard(activity: MembersActivity, board: Board, user: User) {
        EspressoIdlingResource().increment()

        val assignedToHashMap = HashMap<String, Any>()
        assignedToHashMap[Constants.ASSIGNED_TO] = board.assignedTo

        mFireStore.collection(Constants.BOARDS)
            .document(board.documentId)
            .update(assignedToHashMap)
            .addOnSuccessListener {
                EspressoIdlingResource().decrement()

                activity.memberAssignSuccess(user)
            }.addOnFailureListener { e ->
                EspressoIdlingResource().decrement()

                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board.", e)
            }
    }
}
