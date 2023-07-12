package com.example.testapp.models

import java.util.*


data class GetTasksResponse(
    val success: Boolean,
    val data: List<Task>,
)
data class TaskBody(
    val completed: Boolean
)
data class Task(
    val _id: String? = null,
    val description: String,
    var completed: Boolean = false,
)

