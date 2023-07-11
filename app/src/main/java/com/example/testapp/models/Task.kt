package com.example.testapp.models

data class Task(
    //val _id: String,
    val description: String,
    var completed: Boolean = false,
)

data class TaskApi(
    val _id: String,
    val description: String,
    var completed: Boolean = false,
)
