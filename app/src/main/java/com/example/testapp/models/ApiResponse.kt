package com.example.testapp.models

data class ApiResponse(
    val success: Boolean,
    val data: Any? = null,
    val message: String? = null,
)