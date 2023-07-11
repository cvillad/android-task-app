package com.example.testapp.validations

sealed class ValidationEvent {
    object Success: ValidationEvent()
    object Failed: ValidationEvent()
}
