package com.example.testapp.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

data class LoginState(
    val email: String = "",
    val password: String = "",
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

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UpdateEmail -> {
                _uiState.value = _uiState.value.copy(
                    email = event.email
                )
            }
            is LoginEvent.UpdatePassword -> {
                _uiState.value = _uiState.value.copy(
                    password = event.password
                )
            }
            else -> {}
        }
    }
}