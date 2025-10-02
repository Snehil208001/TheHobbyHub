// com/example/thehobbyhub/core/navigations/routes.kt
package com.example.thehobbyhub.core.navigations

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object AdminSignup : Screen("admin_signup")

    // UPDATED: Now accepts a 'role' argument to determine the next screen
    object CitySelection : Screen("city_selection/{role}") {
        const val ARG_ROLE = "role" // Argument key
        fun createRoute(role: String) = "city_selection/$role"
    }

    object TermsOfService : Screen("terms_of_service")
    object PrivacyPolicy : Screen("privacy_policy")
    object ForgotPassword : Screen("forgot_password")

    // ADDED: New route for the Admin Home Screen, accepts a 'city' argument
    object AdminHome : Screen("admin_home/{city}") {
        const val ARG_CITY = "city" // Argument key
        fun createRoute(city: String) = "admin_home/$city"
    }

    object Home : Screen("home/{city}") {
        const val ARG_CITY = "city"
        fun createRoute(city: String) = "home/$city"
    }
}