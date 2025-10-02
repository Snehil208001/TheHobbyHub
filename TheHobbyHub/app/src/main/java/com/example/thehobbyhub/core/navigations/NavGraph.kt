// com/example/thehobbyhub/core/navigations/NavGraph.kt
package com.example.thehobbyhub.core.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.thehobbyhub.mainui.cityselectionscreen.ui.CitySelectionScreen
import com.example.thehobbyhub.mainui.homescreen.ui.AdminHomeScreen // Make sure this is imported
import com.example.thehobbyhub.mainui.homescreen.ui.HomeScreen
import com.example.thehobbyhub.mainui.legalscreens.ui.PrivacyPolicyScreen
import com.example.thehobbyhub.mainui.legalscreens.ui.TermsOfServiceScreen
import com.example.thehobbyhub.mainui.loginscreen.ui.ForgotPasswordScreen
import com.example.thehobbyhub.mainui.loginscreen.ui.LoginScreen
import com.example.thehobbyhub.mainui.signupscreen.ui.AdminSignupScreen
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
        composable(Screen.AdminSignup.route) {
            AdminSignupScreen(navController = navController)
        }
        // UPDATED: The CitySelection composable now needs to know about its 'role' argument
        composable(
            route = Screen.CitySelection.route,
            arguments = listOf(navArgument(Screen.CitySelection.ARG_ROLE) { type = NavType.StringType })
        ) {
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
        // ADDED: This is the missing piece that caused the crash.
        // It defines the route and argument for the Admin Home Screen.
        composable(
            route = Screen.AdminHome.route,
            arguments = listOf(navArgument(Screen.AdminHome.ARG_CITY) { type = NavType.StringType })
        ) {
            AdminHomeScreen(navController = navController)
        }
        composable(
            route = Screen.Home.route,
            arguments = listOf(navArgument(Screen.Home.ARG_CITY) { type = NavType.StringType })
        ) {
            HomeScreen(navController = navController)
        }
    }
}