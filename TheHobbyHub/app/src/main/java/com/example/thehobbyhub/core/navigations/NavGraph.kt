package com.example.thehobbyhub.core.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thehobbyhub.mainui.cityselectionscreen.ui.CitySelectionScreen
import com.example.thehobbyhub.mainui.legalscreens.ui.PrivacyPolicyScreen
import com.example.thehobbyhub.mainui.legalscreens.ui.TermsOfServiceScreen
import com.example.thehobbyhub.mainui.loginscreen.ui.ForgotPasswordScreen
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
        composable(Screen.TermsOfService.route) {
            TermsOfServiceScreen(navController = navController)
        }
        composable(Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(navController = navController)
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
        }
    }
}