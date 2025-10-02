// com/example/thehobbyhub/mainui/cityselectionscreen/viewmodel/CitySelectionViewModel.kt
package com.example.thehobbyhub.mainui.cityselectionscreen.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.thehobbyhub.core.navigations.Screen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CitySelectionUiState(
    val searchQuery: String = "",
    val selectedCity: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentCity: String? = null
)

@HiltViewModel
class CitySelectionViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CitySelectionUiState())
    val uiState: StateFlow<CitySelectionUiState> = _uiState.asStateFlow()

    private val userRole: String = savedStateHandle.get<String>(Screen.CitySelection.ARG_ROLE) ?: "User"

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onCitySelected(city: String, navController: NavController) {
        _uiState.update { it.copy(selectedCity = city, currentCity = city) }

        // This logic determines where to go next based on the role.
        val route = if (userRole == "Admin") {
            Screen.AdminHome.createRoute(city)
        } else {
            Screen.Home.createRoute(city)
        }

        navController.navigate(route) {
            popUpTo(Screen.CitySelection.route) { inclusive = true }
        }
    }

    fun onLocationPermissionDenied() {
        _uiState.update {
            it.copy(
                isLoading = false,
                error = "Permission is required to detect your location automatically."
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(context: Context, navController: NavController) {
        _uiState.update { it.copy(isLoading = true, error = null) }

        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                try {
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (addresses != null && addresses.isNotEmpty()) {
                        val city = addresses[0].locality
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                currentCity = city,
                                selectedCity = city
                            )
                        }

                        // This is the corrected logic for the "Use My Current Location" button.
                        // It checks the role before navigating.
                        val route = if (userRole == "Admin") {
                            Screen.AdminHome.createRoute(city)
                        } else {
                            Screen.Home.createRoute(city)
                        }

                        navController.navigate(route) {
                            popUpTo(Screen.CitySelection.route) { inclusive = true }
                        }
                    } else {
                        _uiState.update { it.copy(isLoading = false, error = "Could not determine city from location.") }
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(isLoading = false, error = "An error occurred while fetching the city name.") }
                }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Could not retrieve current location.") }
            }
        }.addOnFailureListener { e ->
            _uiState.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}