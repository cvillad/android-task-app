package com.example.testapp.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.api.BackendService
import com.example.testapp.models.ApiResponse
import com.example.testapp.models.Auth
import com.example.testapp.validations.ValidationEvent
import com.example.testapp.validations.ValidationResult
import com.example.testapp.validations.Validator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isEmailFocused: Boolean = false,
    val isPasswordFocused: Boolean = false,
    val hasEmailError: Boolean = false,
    val hasPasswordError: Boolean = false
)

sealed class LoginEvent {
    data class UpdateEmail(val email: String): LoginEvent()
    data class UpdatePassword(val password: String): LoginEvent()
    object ValidateFields: LoginEvent()
}

class LoginViewModel: ViewModel() {
    private var _uiState = mutableStateOf(LoginState())
    val uiState: State<LoginState> = _uiState

    val validationEvent = MutableSharedFlow<ValidationEvent>()

    suspend fun login(email: String, password: String): ApiResponse {
        return try {
            val body: Auth.DefaultResponse = BackendService.getClient().login(Auth.LoginBody(email, password))

            ApiResponse(success = true, data = body.data!!)
        } catch(e: java.lang.Exception) {
            ApiResponse(success = false, message = "Ocurrió un error. Intente más tarde")
        }
    }
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UpdateEmail -> {
                _uiState.value = _uiState.value.copy(
                    email = event.email,
                    isEmailFocused = true
                )
            }
            is LoginEvent.UpdatePassword -> {
                _uiState.value = _uiState.value.copy(
                    password = event.password,
                    isPasswordFocused = true
                )
            }
            is LoginEvent.ValidateFields -> {
                validateInputs()
            }
        }
    }

    private fun validateInputs() {
        val emailResult = if (_uiState.value.isEmailFocused) Validator.validateEmail(_uiState.value.email)
                        else ValidationResult(true)
        val passwordResult = if (_uiState.value.isPasswordFocused) Validator.validateLength(_uiState.value.password)
                        else ValidationResult(true)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.status }

        _uiState.value = _uiState.value.copy(
            hasEmailError = !emailResult.status,
            hasPasswordError = !passwordResult.status
        )

        viewModelScope.launch {
            if (hasError) {
                validationEvent.emit(ValidationEvent.Failed)
            } else validationEvent.emit(ValidationEvent.Success)
        }
    }
}