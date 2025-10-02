package com.example.thehobbyhub.mainui.loginscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.thehobbyhub.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Represents the state of the Login screen.
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val role: String = "User"
)

/**
 * ViewModel for the Login screen, responsible for handling business logic and state.
 */
@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // --- State Update Functions ---

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, error = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, error = null) }
    }

    fun onRoleChange(newRole: String) {
        _uiState.update { it.copy(role = newRole) }
    }

    // --- Event Handlers ---

    /**
     * Handles the login button click event.
     */
    fun onLoginClicked(navController: NavController) {
        // Show loading indicator
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            // Simulate a network request
            delay(1500)

            _uiState.update { it.copy(isLoading = false) }

            if (_uiState.value.role == "Admin") {
                // TODO: Navigate to the admin dashboard
            } else {
                navController.navigate(Screen.CitySelection.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        }
    }

    /**
     * Navigates to the forgot password screen.
     */
    fun onForgotPasswordClicked(navController: NavController) {
        navController.navigate(Screen.ForgotPassword.route)
    }

    /**
     * Placeholder for Google Sign-In logic.
     */
    fun onGoogleSignInClicked() {
        // TODO: Implement Google Sign-In logic
        println("Google Sign-In Clicked")
    }

    /**
     * Navigates to the correct sign-up screen based on the selected role.
     */
    fun onSignUpClicked(navController: NavController) {
        // Check the current role from the UI state
        when (_uiState.value.role) {
            "Admin" -> {
                // Navigate directly to the detailed Admin Signup Screen
                navController.navigate(Screen.AdminSignup.route)
            }
            "User" -> {
                // Navigate to the standard User Signup Screen
                // This is the screen we previously renamed from SignupScreen
                navController.navigate(Screen.Signup.route)
            }
            else -> {
                // Fallback to the role selection screen if something goes wrong
                navController.navigate(Screen.Signup.route)
            }
        }
    }
}