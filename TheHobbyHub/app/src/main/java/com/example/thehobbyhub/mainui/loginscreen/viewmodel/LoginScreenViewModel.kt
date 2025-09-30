package com.example.thehobbyhub.mainui.loginscreen.viewmodel

// File: com/example/thehobbyhub/mainui/loginscreen/ui/LoginViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.thehobbyhub.core.navigations.Screen // Assuming you have a Screen enum/sealed class
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Data class (defined here for simplicity, or in its own file)
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // --- State Mutators ---

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, error = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, error = null) }
    }

    // --- Event Handlers (Business Logic and Navigation) ---

    fun onLoginClicked(navController: NavController) {
        val state = _uiState.value

        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(error = "Email and Password cannot be empty.") }
            return
        }

        // Start loading and clear previous errors
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            // Simulate API call delay
            delay(1500)

            // 1. Check credentials (Simulated business logic)
            val success = state.email == "test@user.com" && state.password == "password"

            _uiState.update { it.copy(isLoading = false) }

            if (success) {
                // 2. Navigate on successful login
                // We use a string route "city_selection" as per your composable
                // In a real app, use a constant like Screen.CitySelection.route
                navController.navigate("city_selection") {
                    // Prevent navigating back to the login screen
                    popUpTo("login") { inclusive = true }
                }
            } else {
                // 3. Update state with error
                _uiState.update { it.copy(error = "Invalid email or password.") }
            }
        }
    }

    fun onForgotPasswordClicked() {
        // TODO: Implement navigation or logic for password reset flow
        println("Forgot Password Clicked - Triggering reset flow.")
    }

    fun onGoogleSignInClicked() {
        // TODO: Implement logic for initiating Google sign-in flow
        println("Google Sign-In Clicked - Triggering external auth.")
    }

    fun onSignUpClicked(navController: NavController) {
        // Navigate to the sign-up screen
        // We use a string route "signup" as per your composable
        navController.navigate("signup")
    }
}