package com.example.testapp.validations

import android.util.Patterns


data class ValidationResult(
    val status: Boolean = false,
)

object Validator {
    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    fun validateLength(value: String, minLength: Int = 7): ValidationResult {
        return ValidationResult(value.length >= minLength)
    }
}