package com.example.thehobbyhub.mainui.signupscreen.viewmodel

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

data class AdminSignupUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val verificationCode: String = "",
    val organizationName: String = "",
    val reasonForAccess: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AdminSignupViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AdminSignupUiState())
    val uiState: StateFlow<AdminSignupUiState> = _uiState.asStateFlow()

    fun onFullNameChange(name: String) {
        _uiState.update { it.copy(fullName = name) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onPhoneNumberChange(phone: String) {
        _uiState.update { it.copy(phoneNumber = phone) }
    }

    fun onCityChange(city: String) {
        _uiState.update { it.copy(city = city) }
    }

    fun onVerificationCodeChange(code: String) {
        _uiState.update { it.copy(verificationCode = code) }
    }

    fun onOrganizationNameChange(name: String) {
        _uiState.update { it.copy(organizationName = name) }
    }

    fun onReasonForAccessChange(reason: String) {
        _uiState.update { it.copy(reasonForAccess = reason) }
    }

    fun onAdminSignupClicked(navController: NavController) {
        // Basic validation
        if (uiState.value.fullName.isBlank() || uiState.value.email.isBlank() || uiState.value.password.isBlank() || uiState.value.verificationCode.isBlank()) {
            _uiState.update { it.copy(error = "Please fill all required fields.") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            // TODO: Implement actual admin signup logic here
            kotlinx.coroutines.delay(2000) // Simulate network call
            println("Admin signup attempt: ${uiState.value}")
            _uiState.update { it.copy(isLoading = false) }

            // Navigate to the login screen after submitting the request.
            navController.navigate(Screen.Login.route) {
                // Clear the back stack to prevent going back to signup
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
}