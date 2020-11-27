package com.projemanag.firebase

import android.app.Activity
import com.projemanag.activities.CreateBoardActivity
import com.projemanag.activities.MainActivity
import com.projemanag.activities.MembersActivity
import com.projemanag.activities.SignUpActivity
import com.projemanag.activities.TaskListActivity
import com.projemanag.models.Board
import com.projemanag.models.User

class FakeFirestoreClass : IFirestoreClass {
    override fun registerUser(activity: SignUpActivity, userInfo: User) {
        TODO("Not yet implemented")
    }

    override fun getBoardDetails(activity: TaskListActivity, documentId: String) {
        TODO("Not yet implemented")
    }

    override fun createBoard(activity: CreateBoardActivity, board: Board) {
        TODO("Not yet implemented")
    }

    override fun getBoardsList(activity: MainActivity) {
        TODO("Not yet implemented")
    }

    override fun addUpdateTaskList(activity: Activity, board: Board) {
        TODO("Not yet implemented")
    }

    override fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        TODO("Not yet implemented")
    }

    override fun loadUserData(activity: Activity, readBoardsList: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getCurrentUserID(): String {
        return "111111"
    }

    override fun getAssignedMembersListDetails(activity: Activity, assignedTo: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun getMemberDetails(activity: MembersActivity, email: String) {
        TODO("Not yet implemented")
    }

    override fun assignMemberToBoard(activity: MembersActivity, board: Board, user: User) {
        TODO("Not yet implemented")
    }
}