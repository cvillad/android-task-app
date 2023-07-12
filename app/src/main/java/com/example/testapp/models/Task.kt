package com.example.testapp.models

data class GetTasksResponse(
    val success: Boolean,
    val data: List<Task>,
)

data class TaskDefaultResponse(
    val success: Boolean,
    val data: Task,
)

data class CreateTaskBody(
    val description: String,
)

data class CompleteTaskBody(
    val completed: Boolean
)
data class Task(
    val _id: String? = null,
    val description: String,
    var completed: Boolean = false,
)

