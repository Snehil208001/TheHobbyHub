// File: PrivacyPolicyScreen.kt
package com.example.thehobbyhub.mainui.legalscreens.ui

import androidx.compose.foundation.layout.Column // Removed duplicate Column import
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    // 1. Use Scaffold for standard screen structure (TopAppBar, Content)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background // Ensure background consistency
    ) { paddingValues ->
        // 2. Main content Column uses padding from Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 8.dp) // Extra horizontal padding for readability
        ) {
            // Updated Typography for better hierarchy
            Text(
                text = "Last Updated: September 30, 2025",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Section 1: Data We Collect
            LegalSectionTitle("1. Data We Collect")
            LegalBodyText(
                "We collect the following information: Your **Name**, **Email**, and a hashed **Password**; the City you select; your chosen **Hobby Tags** and any **Photos** you upload; and technical data like your Device ID, IP address, and fcm_token for push notifications.",
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Section 2: Purpose of Data Use
            LegalSectionTitle("2. Purpose of Data Use")
            LegalBodyText(
                "We use your data to provide our core **City-Based Filtering** service, send you **Push Notifications**, connect you with other users, and process payments for features like **Ticketing**.",
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Section 3: Data Sharing and Disclosure
            LegalSectionTitle("3. Data Sharing and Disclosure")
            LegalBodyText(
                "We do **not** sell your personal data. We may share information with trusted third-party service providers (e.g., payment processors, hosting partners) to run the app, but only to the extent necessary to perform their services for us."
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Section 4: Your Rights
            LegalSectionTitle("4. Your Rights")
            LegalBodyText(
                "You have the right to **access**, **correct**, or **delete** your personal information at any time. You can typically do this directly within your Account Settings, or by contacting us at support@thehobbyhub.com."
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Section 5: Changes to this Policy
            LegalSectionTitle("5. Changes to this Policy")
            LegalBodyText(
                "We may update our Privacy Policy from time to time. We will notify you of any changes by posting the new Policy on this page and updating the 'Last Updated' date."
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// *** REMOVE LegalSectionTitle and LegalBodyText from here ***