package com.example.testapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testapp.viewModels.LoginEvent
import com.example.testapp.viewModels.LoginViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel: LoginViewModel = viewModel()

    val stateForm = loginViewModel.uiState.value
    var hidePassword by remember { mutableStateOf(true) }

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

            TextField(
                modifier = Modifier.padding(bottom = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                value = stateForm.email,
                label = { Text(text = "Correo electrónico") },
                onValueChange = {
                    loginViewModel.onEvent(LoginEvent.UpdateEmail(it))
                },
            )

            TextField(
                modifier = Modifier.padding(bottom = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType =  KeyboardType.Password
                ),
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
                visualTransformation = if (hidePassword) PasswordVisualTransformation() else VisualTransformation.None
            )

            Button(
                modifier = Modifier.padding(bottom = 20.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Iniciar sesion")
            }

            Text(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .clickable { navController.navigate("signup") },
                text = "Regístrate"
            )
        }
    }
}
