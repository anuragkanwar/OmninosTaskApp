package com.example.omninostaskapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.omninostaskapp.presentation.SingUpScreen.SignUpScreen
import com.example.omninostaskapp.presentation.homescreen.DefaultHomeScreen
import com.example.omninostaskapp.presentation.welcomeScreen.WelcomeScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.name) {

        composable(AppScreens.HomeScreen.name) {
            DefaultHomeScreen(navController)
        }

        composable(AppScreens.SignUpScreen.name) {
            SignUpScreen(navController)
        }

        composable(AppScreens.WelcomeScreen.name) {
            WelcomeScreen(navController)
        }
    }

}