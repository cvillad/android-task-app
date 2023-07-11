package com.example.testapp.viewModels

sealed class ValidationEvent {
    object Success: ValidationEvent()
    object Failed: ValidationEvent()
}
