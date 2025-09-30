package com.example.thehobbyhub.mainui.cityselectionscreen.ui

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thehobbyhub.core.utils.hasLocationPermission
import com.example.thehobbyhub.core.utils.rememberLocationPermissionLauncher
import com.example.thehobbyhub.mainui.cityselectionscreen.viewmodel.CitySelectionViewModel
import com.example.thehobbyhub.ui.theme.DarkGray1A1A1A
import com.example.thehobbyhub.ui.theme.OffWhite
import com.example.thehobbyhub.ui.theme.Purple6C63FF
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitySelectionScreen(
    navController: NavController,
    citySelectionViewModel: CitySelectionViewModel = viewModel()
) {
    val uiState by citySelectionViewModel.uiState.collectAsState()
    val context = LocalContext.current
    var isContentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        isContentVisible = true
    }

    val locationPermissionLauncher = rememberLocationPermissionLauncher { isGranted ->
        if (isGranted) {
            citySelectionViewModel.fetchCurrentLocation(context)
        } else {
            citySelectionViewModel.onLocationPermissionDenied()
        }
    }

    val indianCities = remember {
        listOf(
            "Mumbai", "Delhi", "Bengaluru", "Hyderabad", "Chennai", "Kolkata", "Ahmedabad",
            "Pune", "Jaipur", "Lucknow", "Kanpur", "Nagpur", "Indore", "Thane", "Bhopal",
            "Visakhapatnam", "Pimpri-Chinchwad", "Patna", "Vadodara", "Ghaziabad", "Ludhiana",
            "Agra", "Nashik", "Faridabad", "Meerut", "Rajkot", "Varanasi", "Srinagar", "Aurangabad",
            "Dhanbad", "Amritsar", "Prayagraj", "Ranchi", "Madurai", "Jabalpur", "Coimbatore",
            "Gwalior", "Vijayawada", "Jodhpur", "Raipur", "Kota", "Guwahati", "Chandigarh",
            "Mysuru", "Thiruvananthapuram", "Bhubaneswar", "Tiruchirappalli", "Moradabad", "Bareilly"
        ).sorted()
    }

    val filteredCities = remember(uiState.searchQuery) {
        if (uiState.searchQuery.isBlank()) {
            indianCities
        } else {
            indianCities.filter {
                it.contains(uiState.searchQuery, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite) // Use the new clean background color
    ) {
        AnimatedVisibility(
            visible = isContentVisible,
            enter = slideInVertically(initialOffsetY = { it / 4 }) + fadeIn(animationSpec = tween(700))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Hello, User!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkGray1A1A1A,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Choose your city to discover local events and activities.",
                    fontSize = 16.sp,
                    color = DarkGray1A1A1A.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = uiState.searchQuery,
                            onValueChange = { citySelectionViewModel.onSearchQueryChange(it) },
                            placeholder = { Text("Search for a city...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedIndicatorColor = Purple6C63FF,
                                unfocusedIndicatorColor = Color.LightGray
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (hasLocationPermission(context)) {
                                    citySelectionViewModel.fetchCurrentLocation(context)
                                } else {
                                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Purple6C63FF)
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                            } else {
                                Icon(Icons.Default.LocationOn, contentDescription = "Location Icon")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Use My Current Location", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        uiState.error?.let { errorMessage ->
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                        uiState.currentCity?.let { city ->
                            Text(
                                text = "Detected: $city",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                textAlign = TextAlign.Center,
                                color = Purple6C63FF
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Popular Cities",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = DarkGray1A1A1A,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp, start = 8.dp),
                    textAlign = TextAlign.Start
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(filteredCities) { city ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    citySelectionViewModel.onCitySelected(city)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = city,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                if (uiState.selectedCity == city) {
                                    Icon(Icons.Default.LocationOn, contentDescription = "Selected", tint = Purple6C63FF)
                                } else {
                                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select", tint = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}