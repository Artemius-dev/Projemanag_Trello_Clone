package com.projemanag.firebase

import android.app.Activity
import com.projemanag.activities.CreateBoardActivity
import com.projemanag.activities.MainActivity
import com.projemanag.activities.MembersActivity
import com.projemanag.activities.SignUpActivity
import com.projemanag.activities.TaskListActivity
import com.projemanag.models.Board
import com.projemanag.models.User

interface IFirestoreClass {
    fun registerUser(activity: SignUpActivity, userInfo: User)
    fun getBoardDetails(activity: TaskListActivity, documentId: String)
    fun createBoard(activity: CreateBoardActivity, board: Board)
    fun getBoardsList(activity: MainActivity)
    fun addUpdateTaskList(activity: Activity, board: Board)
    fun updateUserProfileData(
        activity: Activity,
        userHashMap: HashMap<String, Any>
    )

    fun loadUserData(activity: Activity, readBoardsList: Boolean = false)
    fun getCurrentUserID(): String
    fun getAssignedMembersListDetails(activity: Activity, assignedTo: ArrayList<String>)
    fun getMemberDetails(activity: MembersActivity, email: String)
    fun assignMemberToBoard(activity: MembersActivity, board: Board, user: User)
}