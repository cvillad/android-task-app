package com.example.testapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.dataStore
import com.example.testapp.screens.HomeScreen
import com.example.testapp.screens.LoginScreen
import com.example.testapp.screens.SignupScreen
import kotlinx.coroutines.flow.map

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun RootNavigator() {
    val context = LocalContext.current
    val authToken = context.dataStore.data.map { settings ->
        settings[stringPreferencesKey("authToken")]
    }.collectAsState(initial = null).value

    val navController = rememberNavController()

    if (authToken.isNullOrBlank()) {
        NavHost(navController = navController, startDestination = "login") {
            composable("signup") { SignupScreen(navController) }
            composable("login") { LoginScreen(navController) }
        }
    } else {
        AuthNavigator(navController)
    }
}

@Composable
fun AuthNavigator(navController: NavController) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
    }
}