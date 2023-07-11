package com.example.testapp.models

class Auth {
    data class DefaultResponse (
        val success: Boolean,
        val data: Data?,
        val error: String?
    )

    data class Data(
        val token: String,
    )

    data class LoginBody(
        val email: String,
        val password: String,
    )
}