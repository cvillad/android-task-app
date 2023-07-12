package com.example.testapp.viewModels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.testapp.api.BackendService
import com.example.testapp.models.*

class TasksViewModel: ViewModel() {
    private var _todoList =  mutableStateOf(mutableStateListOf<Task>())
    private val apiClient = BackendService.getClient()
    val todoList: State<List<Task>> = _todoList

    suspend fun getTasks(authorization: String) {
        try {
            val tasks = apiClient.fetchTasks(authorization).data
            _todoList.value = tasks.toMutableStateList()

        } catch (e: java.lang.Exception) {
            Log.e("error", e.message.toString())
            null
        }
    }

    suspend fun addTask(authorization: String, description: String) {
        try {
            val response: TaskDefaultResponse = apiClient.createTask(authorization, CreateTaskBody(
                description = description
            ))
            val task = response.data
            _todoList.value.add(task)
        } catch (e: Exception) {
            Log.e("AddTask", e.message.toString())
        }
    }

    suspend fun markAsComplete(authorization: String, task: Task, value: Boolean) {
        try {
            apiClient.updateTask(authorization, id = task._id!!, CompleteTaskBody(completed = value))
            val index = _todoList.value.indexOf(task);

            _todoList.value[index] = _todoList.value[index].let {
                it.copy(
                    completed = value
                )
            }
        } catch (e: Exception) {
            Log.e("UpdateTask", e.message.toString())
        }
    }

    fun removeTask(task: Task) {
        _todoList.value.remove(task)
    }
}