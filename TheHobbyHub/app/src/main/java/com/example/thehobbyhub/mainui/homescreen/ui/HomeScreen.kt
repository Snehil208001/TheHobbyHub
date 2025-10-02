package com.example.thehobbyhub.mainui.homescreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.thehobbyhub.R
import com.example.thehobbyhub.mainui.homescreen.viewmodel.HomeViewModel
import com.example.thehobbyhub.mainui.homescreen.viewmodel.HobbyEvent
import com.example.thehobbyhub.mainui.homescreen.viewmodel.HobbyHub
import com.example.thehobbyhub.ui.theme.TheHobbyHubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { /* TODO: Navigate to City Selection */ }
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = uiState.currentCity, // Use city from ViewModel
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Change City",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                actions = {
                }
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }
        // --- FLOATING ACTION BUTTON AND ITS POSITION REMOVED ---
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // --- Content above the sections, like "Hello, Sasmita" and search bar ---
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(
                        text = "Hello, Sasmita", // Example name, ideally dynamic
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black // Assuming a dark text color for this
                    )
                    Text(
                        text = "There are ${uiState.totalEvents} events around your location.",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary // Assuming purple from reference
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Search Bar and Filter Icon (similar to reference image)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = "", // This would be managed by ViewModel
                            onValueChange = { /* TODO */ },
                            placeholder = { Text("Search your favorites events ...") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = Color.LightGray
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon( // Filter Icon
                            Icons.Default.Notifications, // Using notifications as a placeholder for a filter icon
                            contentDescription = "Filter",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray.copy(alpha = 0.3f))
                                .padding(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Horizontal scrolling categories (Music, Clothing, Festival, Food)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(listOf("Music", "Clothing", "Festival", "Food")) { category ->
                            CategoryChip(category = category)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }


            // Upcoming Events Section
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SectionTitle(title = "Upcoming Events", modifier = Modifier.weight(1f))
                    Text(
                        text = "See all events",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { /* TODO: Navigate to All Events */ }
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.upcomingEvents) { event ->
                        UpcomingEventCard(event)
                    }
                }
            }

            // Featured Workshops & Events (if still desired, can be integrated or removed)
            item {
                if (uiState.featuredEvents.isNotEmpty()) {
                    SectionTitle(title = "Featured Workshops & Events")
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.featuredEvents) { event ->
                            FeaturedEventCard(event)
                        }
                    }
                }
            }


            // Hubs You Might Like Section (can be renamed/repositioned as per image)
            item {
                if (uiState.suggestedHubs.isNotEmpty()) {
                    SectionTitle(title = "Hubs You Might Like")
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.suggestedHubs) { hub ->
                            HubSuggestionCard(hub)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun CategoryChip(category: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = Modifier
            .width(90.dp)
            .height(80.dp) // Fixed size for chips
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            // Placeholder for category icon, you'd use actual icons here
            Icon(
                Icons.Default.MusicNote,
                contentDescription = category,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(category, style = MaterialTheme.typography.labelMedium)
        }
    }
}


@Composable
fun FeaturedEventCard(event: HobbyEvent) {
    Card(
        modifier = Modifier.width(300.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = event.imageRes),
                    contentDescription = event.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Featured",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    event.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    event.hubName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun UpcomingEventCard(event: HobbyEvent) {
    Card(
        modifier = Modifier.width(200.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(120.dp) // Adjusted height as per reference
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = event.imageRes),
                    contentDescription = event.title,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = event.date.split(" ")[0], // "Aug"
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = event.date.split(" ")[1], // "24"
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = event.priceRange,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                ) // New field
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.hubName,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun HubSuggestionCard(hub: HobbyHub) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = hub.imageRes),
                contentDescription = hub.name,
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = hub.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(12.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        modifier = Modifier.height(70.dp)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            onClick = { /* No action */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Explore, contentDescription = "Explore") }, // Using Explore icon
            label = { Text("Explore") },
            selected = false,
            onClick = { /* TODO: Navigate to Explore */ }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Saved"
                )
            }, // Using Star for Saved
            label = { Text("Saved") },
            selected = false,
            onClick = { /* TODO: Navigate to Saved */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { /* TODO: Navigate to Profile */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TheHobbyHubTheme {
        HomeScreen(navController = rememberNavController())
    }
}

// Icon for Music, as per category chips. Add similar for Clothing, Festival, Food
@Composable
fun MusicNote(modifier: Modifier = Modifier) {
    Icon(Icons.Default.MusicNote, contentDescription = "Music", modifier = modifier)
}