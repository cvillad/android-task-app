package com.example.testapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.screens.LoginScreen
import com.example.testapp.screens.SignupScreen

@Composable
fun RootNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("signup") { SignupScreen(navController) }
        composable("login") { LoginScreen(navController) }
    }
}