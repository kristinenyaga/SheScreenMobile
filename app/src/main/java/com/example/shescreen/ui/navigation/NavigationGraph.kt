package com.example.shescreen.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shescreen.ui.screens.Auth.BioDataScreen
import com.example.shescreen.ui.screens.Auth.SignInScreen
import com.example.shescreen.ui.screens.Auth.SignUpScreen
import com.example.shescreen.ui.screens.Home.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(navController = navController, startDestination = HOME_SCREEN) {
        composable(SIGN_UP_SCREEN) {
            SignUpScreen(navController)
        }
        composable(SIGN_IN_SCREEN) {
            SignInScreen(navController)
        }
        composable(PROFILE_SCREEN) {
        }
        composable(INSIGHTS_SCREEN) {
        }
        composable(HOME_SCREEN) {
            HomeScreen(navController = navController)
        }
        composable(BIO_DATA_SCREEN) {
            BioDataScreen(navController)
        }
    }
}