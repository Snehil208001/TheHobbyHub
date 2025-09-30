package com.example.thehobbyhub.mainui.cityselectionscreen.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
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
class CitySelectionViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CitySelectionUiState())
    val uiState: StateFlow<CitySelectionUiState> = _uiState.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onCitySelected(city: String, navController: NavController) {
        _uiState.update { it.copy(selectedCity = city, currentCity = city) }
        // UPDATED: Use the new createRoute function to pass the city
        navController.navigate(Screen.Home.createRoute(city)) {
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
                        // UPDATED: Use the new createRoute function here as well
                        navController.navigate(Screen.Home.createRoute(city)) {
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