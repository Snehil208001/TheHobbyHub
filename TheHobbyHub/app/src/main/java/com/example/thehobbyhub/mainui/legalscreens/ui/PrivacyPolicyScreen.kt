package com.example.thehobbyhub.mainui.legalscreens.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Privacy Policy", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Last Updated: September 30, 2025", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))

        Text("1. Data We Collect", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(
            "We collect the following information: Your Name, Email, and a hashed Password; the City you select; your chosen Hobby Tags and any Photos you upload; and technical data like your Device ID, IP address, and fcm_token for push notifications.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("2. Purpose of Data Use", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(
            "We use your data to provide our core City-Based Filtering service, send you Push Notifications, connect you with other users, and process payments for features like Ticketing.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Add other sections (Data Sharing, Security, etc.) in the same format
    }
}