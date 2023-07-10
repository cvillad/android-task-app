package com.example.testapp.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SignupScreen(navController: NavController) {
    Text(text = "Signup")
    Button(onClick = { navController.navigate("Login") }) {
        Text(text = "Iniciar sesion")
    }
}
