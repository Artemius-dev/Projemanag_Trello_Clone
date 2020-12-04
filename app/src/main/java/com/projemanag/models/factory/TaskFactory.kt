package com.projemanag.models.factory

import com.projemanag.activities.TaskListActivity
import com.projemanag.firebase.FirestoreClass
import com.projemanag.models.Board
import com.projemanag.models.Task
import javax.inject.Inject

open class TaskFactory
@Inject
constructor(open val firestoreClass: FirestoreClass) {

    open fun createTaskList(taskListName: String, mBoardDetails: Board, activity: TaskListActivity) {
        val task = Task(taskListName, firestoreClass.getCurrentUserID())
        mBoardDetails.taskList.add(0, task)
        mBoardDetails.taskList.removeAt(mBoardDetails.taskList.size - 1)

        firestoreClass.addUpdateTaskList(activity, mBoardDetails)
    }

}