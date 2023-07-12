package com.example.testapp.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.testapp.api.BackendService
import com.example.testapp.models.GetTasksResponse
import com.example.testapp.models.Task
import com.example.testapp.models.TaskBody

class TasksViewModel: ViewModel() {
    private var _todoList = mutableStateListOf<Task>()

    suspend fun getTasks(authorization: String): List<Task>? {
        return try {
            val response: GetTasksResponse = BackendService.getClient().fetchTasks(authorization)
            return response.data
        } catch (e: java.lang.Exception) {
            Log.e("error", e.message.toString())
            null
        }
    }

    fun addTask(task: Task) {
        _todoList.add(task)
    }

    fun removeTask(task: Task) {
        _todoList.remove(task)
    }

    suspend fun markAsComplete(authorization: String, task: Task, value: Boolean) {
        BackendService.getClient().updateTask(authorization, id = task._id!!, TaskBody(completed = value))
        _todoList.find{ it._id == task._id }?.let {
            it.copy(
                completed = value
            )
        }
    }
}