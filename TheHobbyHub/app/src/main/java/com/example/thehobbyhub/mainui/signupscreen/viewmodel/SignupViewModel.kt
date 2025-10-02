package com.example.thehobbyhub.mainui.signupscreen.viewmodel // CORRECTED PACKAGE NAME

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.thehobbyhub.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Data class representing the UI state for the Signup Screen.
 * This state is collected by the Composable function.
 */
data class SignupUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val agreedToTerms: Boolean = false,
    val isLoading: Boolean = false,
    val signupError: String? = null
)

/**
 * ViewModel to manage the state and logic for the Signup Screen.
 */
@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(SignupUiState())

    // Public StateFlow to expose the UI state to the Composable
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    // --- State Update Functions ---

    fun onUsernameChange(newUsername: String) {
        _uiState.update { it.copy(username = newUsername) }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = newConfirmPassword) }
    }

    fun onTermsAgreementChange(agreed: Boolean) {
        _uiState.update { it.copy(agreedToTerms = agreed) }
    }

    // --- Event Handling Functions ---

    fun onSignupClicked(navController: NavController) {
        val state = _uiState.value

        // Simple validation logic (You would add more complex/backend logic here)
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(signupError = "Passwords do not match!") }
            return
        }

        if (!state.agreedToTerms) {
            _uiState.update { it.copy(signupError = "You must agree to the Terms and Privacy Policy.") }
            return
        }

        // Clear error and start loading
        _uiState.update { it.copy(isLoading = true, signupError = null) }

        // Simulate a sign-up process (e.g., calling an Auth Repository)
        viewModelScope.launch {
            // Simulate network delay
            kotlinx.coroutines.delay(1500)

            // In a real app, you'd check the result of the sign-up call
            val isSuccess = true // Assume success for this example

            _uiState.update { it.copy(isLoading = false) }

            if (isSuccess) {
                // Navigate to the Login screen after successful signup
                navController.navigate(Screen.Login.route) {
                    // Clear the back stack to prevent going back to signup
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            } else {
                // Handle failure
                _uiState.update { it.copy(signupError = "Sign up failed. Please try again.") }
            }
        }
    }

    fun onGoogleSignInClicked() {
        // Implement Google Sign-In logic here (e.g., launching Google Sign-In flow)
        println("Google Sign-In Clicked")
    }

    fun onTermsClicked(navController: NavController) {
        // Navigate to the Terms and Conditions screen
        navController.navigate(Screen.TermsOfService.route)
    }

    fun onPrivacyClicked(navController: NavController) {
        // Navigate to the Privacy Policy screen
        navController.navigate(Screen.PrivacyPolicy.route)
    }

    fun onLoginClicked(navController: NavController) {
        // Navigate to the Login screen
        navController.navigate(Screen.Login.route)
    }
}