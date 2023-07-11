package com.example.testapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testapp.validations.ValidationEvent
import com.example.testapp.viewModels.LoginEvent
import com.example.testapp.viewModels.LoginViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel: LoginViewModel = viewModel()

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val stateForm = loginViewModel.uiState.value
    var isValidForm by remember { mutableStateOf(false) }
    var hidePassword by remember { mutableStateOf(true) }

    scope.launch {
        loginViewModel.validationEvent.collect { event->
            isValidForm = when(event) {
                is ValidationEvent.Success -> true
                is ValidationEvent.Failed -> false
            }
        }
    }

    LaunchedEffect(key1 = stateForm) {
        loginViewModel.onEvent(LoginEvent.ValidateFields)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Iniciar sesión",
                modifier = Modifier.padding(bottom = 20.dp),
                style = MaterialTheme.typography.h4
            )

            Column(
                modifier = Modifier.padding(bottom = 20.dp),
            ) {
                TextField(
                    modifier = Modifier.padding(bottom = 4.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    value = stateForm.email,
                    label = { Text(text = "Correo electrónico") },
                    onValueChange = {
                        loginViewModel.onEvent(LoginEvent.UpdateEmail(it))
                    },
                    singleLine = true,
                    isError = stateForm.hasEmailError
                )
                if (stateForm.hasEmailError) {
                    Text(
                        text = "Ingrese un email válido",
                        style = MaterialTheme.typography.caption,
                        color = Color.Red
                    )
                }
            }

            Column(
                modifier = Modifier.padding(bottom = 20.dp),
            ) {
                TextField(
                    modifier = Modifier.padding(bottom = 4.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType =  KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions { focusManager.clearFocus() },
                    value = stateForm.password,
                    onValueChange = {
                        loginViewModel.onEvent(LoginEvent.UpdatePassword(it))
                    },
                    label = { Text(text = "Contraseña") },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable { hidePassword = !hidePassword },
                            imageVector = if (hidePassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle visibility"
                        )
                    },
                    visualTransformation = if (hidePassword) PasswordVisualTransformation() else VisualTransformation.None,
                    singleLine = true,
                    isError = stateForm.hasPasswordError
                )
                if (stateForm.hasPasswordError) {
                    Text(
                        text = "La contraseña debe contener más de 7 caracteres",
                        style = MaterialTheme.typography.caption,
                        color = Color.Red
                    )
                }
            }

            Button(
                modifier = Modifier.padding(bottom = 20.dp),
                enabled = isValidForm,
                onClick = { navController.navigate("home") }
            ) {
                Text(text = "Iniciar sesion")
            }

            Text(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .clickable { navController.navigate("signup") },
                text = "Regístrate",
            )
        }
    }
}
