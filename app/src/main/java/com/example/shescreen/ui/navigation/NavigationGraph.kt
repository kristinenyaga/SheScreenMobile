package com.example.shescreen.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shescreen.data.api.DataViewModel
import com.example.shescreen.data.api.PrefsManager
import com.example.shescreen.ui.screens.Auth.BioDataScreen
import com.example.shescreen.ui.screens.Auth.SignInScreen
import com.example.shescreen.ui.screens.Auth.SignUpScreen
import com.example.shescreen.ui.screens.Chat.ChatScreen
import com.example.shescreen.ui.screens.EducationHub.EducationDetailScreen
import com.example.shescreen.ui.screens.EducationHub.EducationHubScreen
import com.example.shescreen.ui.screens.HealthSummary.HealthScreen
import com.example.shescreen.ui.screens.Home.HomeScreen
import com.example.shescreen.ui.screens.RiskAssessment.PredictionScreen
import com.example.shescreen.ui.screens.RiskAssessment.RiskAssessmentScreen
import com.example.shescreen.ui.screens.Services.ServicesScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    prefsManager: PrefsManager,
    modifier: Modifier
) {
    val dataViewModel: DataViewModel = viewModel()

    NavHost(
        navController = navController, startDestination = HOME_SCREEN
    ) {
        composable(SIGN_UP_SCREEN) {
            SignUpScreen(navController, prefsManager)
        }
        composable(SIGN_IN_SCREEN) {
            SignInScreen(navController)
        }
        composable(PROFILE_SCREEN) {
        }
        composable(EDUCATION_HUB_SCREEN) {
            EducationHubScreen(navController = navController)
        }
        composable(HOME_SCREEN) {
            HomeScreen(navController = navController)
        }
        composable(BIO_DATA_SCREEN) {
            BioDataScreen(navController, prefsManager)
        }
        composable(RISK_ASSESSMENT_SCREEN) {
            RiskAssessmentScreen(
                navController = navController,
                prefsManager = prefsManager,
                viewModel = dataViewModel
            )
        }
        composable(PREDICTION_SCREEN) {
            PredictionScreen(navController, viewModel = dataViewModel)
        }
        composable(HEALTH_SCREEN) {
            HealthScreen(navController)
        }
        composable(CHAT_SCREEN) {
            ChatScreen(navController = navController, prefsManager = prefsManager)
        }
        composable(SERVICES_SCREEN) {
            ServicesScreen(navController = navController)
        }
        composable("educationDetail/{contentId}") { backStackEntry ->
            val contentId = backStackEntry.arguments?.getString("contentId")?.toIntOrNull()
            contentId?.let {
                EducationDetailScreen(contentId = it, navController = navController)
            }
        }

    }
}