package com.example.testapp.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginScreen(navController: NavController) {
    Text(text = "Login")
    Button(onClick = { navController.navigate("signup") }) {
        Text(text = "Regístrate")
    }
}
