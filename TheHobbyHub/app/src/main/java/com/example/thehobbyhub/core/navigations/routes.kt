package com.example.thehobbyhub.core.navigations

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object CitySelection : Screen("city_selection")
    object TermsOfService : Screen("terms_of_service")
    object PrivacyPolicy : Screen("privacy_policy")
    object ForgotPassword : Screen("forgot_password")
}