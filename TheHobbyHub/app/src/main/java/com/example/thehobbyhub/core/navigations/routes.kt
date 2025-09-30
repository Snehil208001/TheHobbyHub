// snehil208001/thehobbyhub/TheHobbyHub-d30e7e1f3c7086c270533e7095de4d39ff2ade19/TheHobbyHub/app/src/main/java/com/example/thehobbyhub/core/navigations/routes.kt
package com.example.thehobbyhub.core.navigations

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object CitySelection : Screen("city_selection")
    object TermsOfService : Screen("terms_of_service") // New route
    object PrivacyPolicy : Screen("privacy_policy")   // New route
}