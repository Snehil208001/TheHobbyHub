package com.example.thehobbyhub.core.navigations

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object CitySelection : Screen("city_selection")
    object TermsOfService : Screen("terms_of_service")
    object PrivacyPolicy : Screen("privacy_policy")
    object ForgotPassword : Screen("forgot_password")

    // UPDATED: Home route now includes a placeholder for the city argument
    object Home : Screen("home/{city}") {
        const val ARG_CITY = "city" // Argument key
        fun createRoute(city: String) = "home/$city"
    }
}