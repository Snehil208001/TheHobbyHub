package com.example.thehobbyhub.mainui.homescreen.viewmodel

import androidx.annotation.DrawableRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.thehobbyhub.R
import com.example.thehobbyhub.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class HobbyEvent(
    val title: String,
    val hubName: String, // Represents location/sub-location in the image's context
    val date: String, // Format "Aug 24"
    val priceRange: String, // New field for price range
    @DrawableRes val imageRes: Int,
    val isFeatured: Boolean = false
)

data class HobbyHub(
    val name: String,
    @DrawableRes val imageRes: Int
)

data class HomeUiState(
    val currentCity: String = "Jombang, East Java", // Matches reference image
    val totalEvents: Int = 32, // Matches reference image
    val featuredEvents: List<HobbyEvent> = emptyList(),
    val upcomingEvents: List<HobbyEvent> = emptyList(),
    val suggestedHubs: List<HobbyHub> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    // UPDATED: Inject SavedStateHandle to access navigation arguments
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Retrieve the city from the navigation arguments
        val city = savedStateHandle.get<String>(Screen.Home.ARG_CITY) ?: "Unknown City"
        loadData(city)
    }

    private fun loadData(city: String) { // Pass the city to this function
        val allEvents = listOf(
            HobbyEvent(
                title = "Bernadya Solo Concert",
                hubName = "Mojokerto, East Java", // Location for this event
                date = "Aug 24",
                priceRange = "Rp250k - Rp500k",
                imageRes = R.drawable.thehobbyhub, // Replace with actual image
                isFeatured = false
            ),
            HobbyEvent(
                title = "Moshing Fest 2024",
                hubName = "Jombang, East Java", // Location for this event
                date = "Aug 26",
                priceRange = "Rp80k - Rp250k",
                imageRes = R.drawable.thehobbyhub // Replace with actual image
            ),
            HobbyEvent(
                title = "Weekend Pottery Workshop",
                hubName = "Patna Potters",
                date = "Oct 4",
                priceRange = "Free",
                imageRes = R.drawable.thehobbyhub,
                isFeatured = true
            ),
            HobbyEvent(
                title = "Sunrise Photo Walk at Gandhi Ghat",
                hubName = "Patna Shutterbugs",
                date = "Oct 11",
                priceRange = "Rp50k",
                imageRes = R.drawable.thehobbyhub
            ),
            HobbyEvent(
                title = "Intro to Kotlin for Beginners",
                hubName = "Patna Coders",
                date = "Oct 12",
                priceRange = "Rp100k",
                imageRes = R.drawable.thehobbyhub
            )
        )

        val allHubs = listOf(
            HobbyHub("Patna Runners", R.drawable.thehobbyhub),
            HobbyHub("Foodies of Patna", R.drawable.thehobbyhub),
            HobbyHub("Patna Cyclists", R.drawable.thehobbyhub),
            HobbyHub("Board Games Club", R.drawable.thehobbyhub)
        )

        _uiState.value = HomeUiState(
            // UPDATED: Use the city passed from the navigation argument
            currentCity = city,
            totalEvents = 32, // Set initial total events from reference
            featuredEvents = allEvents.filter { it.isFeatured },
            upcomingEvents = allEvents.filter { !it.isFeatured }.take(5), // Take a few for the upcoming events
            suggestedHubs = allHubs
        )
    }
}