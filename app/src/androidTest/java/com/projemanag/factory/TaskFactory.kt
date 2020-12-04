package com.projemanag.factory

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projemanag.activities.TaskListActivity
import com.projemanag.firebase.FirestoreClass
import com.projemanag.models.Board
import com.projemanag.models.Card
import com.projemanag.models.Task
import com.projemanag.models.factory.TaskFactory
import com.projemanag.testHelpers.readJSONFromAsset
import com.projemanag.utils.Constants
import com.projemanag.utils.EspressoIdlingResource
import javax.inject.Inject

class TaskFactory
@Inject
constructor(override val firestoreClass: FirestoreClass) : TaskFactory(firestoreClass){

    override fun createTaskList(taskListName: String, mBoardDetails: Board, activity: TaskListActivity) {
        val task = Task(taskListName, firestoreClass.getCurrentUserID())
        mBoardDetails.taskList.add(0, task)
        mBoardDetails.taskList.removeAt(mBoardDetails.taskList.size - 1)

        firestoreClass.addUpdateTaskList(activity, mBoardDetails)
    }

    fun createTaskList(board: Board) {
        val taskListHashMap = HashMap<String, Any>()
        taskListHashMap[Constants.TASK_LIST] = board.taskList

        EspressoIdlingResource().increment()

        firestoreClass.mFireStore.collection(Constants.BOARDS)
            .document(board.documentId)
            .update(taskListHashMap)

        EspressoIdlingResource().decrement()
    }

    fun createFakeListOfTasks(): List<Task> {
        val tasks: List<Task> = Gson()
            .fromJson(
                readJSONFromAsset("task_list.json"),
                object: TypeToken<List<Task>>(){}.type
            )
        return tasks
    }

    fun createFakeTask(board: Board, users: ArrayList<Card>) {
        val task = Task("FakeTask", "FakeUser", users)

        val taskListHashMap = HashMap<String, Any>()
        taskListHashMap[Constants.TASK_LIST] = board.taskList

        EspressoIdlingResource().increment()

        firestoreClass.mFireStore.collection(Constants.BOARDS)
            .document(board.documentId)
            .update(taskListHashMap)

    }
}