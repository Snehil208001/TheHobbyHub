package com.example.thehobbyhub.core.navigations


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
}