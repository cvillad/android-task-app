package com.example.testapp.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.testapp.models.Task

class TasksViewModel: ViewModel() {
    private var _todoList = mutableStateListOf<Task>()

    fun getTasks(): List<Task> {
        return _todoList
    }

    fun addTask(task: Task) {
        _todoList.add(task)
    }

    fun removeTask(task: Task) {
        _todoList.remove(task)
    }

    fun markAsComplete(task: Task, value: Boolean) {
        val index = _todoList.indexOf(task);

        _todoList[index] = _todoList[index].let {
            it.copy(
                completed = value
            )
        }

    }
}