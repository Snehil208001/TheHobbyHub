// snehil208001/thehobbyhub/TheHobbyHub-d30e7e1f3c7086c270533e7095de4d39ff2ade19/TheHobbyHub/app/src/main/java/com/example/thehobbyhub/core/navigations/NavGraph.kt
package com.example.thehobbyhub.core.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thehobbyhub.mainui.cityselectionscreen.ui.CitySelectionScreen
import com.example.thehobbyhub.mainui.legalscreens.ui.PrivacyPolicyScreen
import com.example.thehobbyhub.mainui.legalscreens.ui.TermsOfServiceScreen
import com.example.thehobbyhub.mainui.loginscreen.ui.LoginScreen
import com.example.thehobbyhub.mainui.signupscreen.ui.SignupScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Signup.route) {
            SignupScreen(navController = navController)
        }
        composable(Screen.CitySelection.route) {
            CitySelectionScreen(navController = navController)
        }
        composable(Screen.TermsOfService.route) { // New composable
            TermsOfServiceScreen(navController = navController)
        }
        composable(Screen.PrivacyPolicy.route) { // New composable
            PrivacyPolicyScreen(navController = navController)
        }
    }
}