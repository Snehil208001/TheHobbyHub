// File: TermsOfServiceScreen.kt
package com.example.thehobbyhub.mainui.legalscreens.ui

import androidx.compose.foundation.layout.Column
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
fun TermsOfServiceScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    // Use Scaffold for standard screen layout with a TopAppBar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terms of Service", fontWeight = FontWeight.SemiBold) },
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
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        // Main content Column, using padding from Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 8.dp) // Extra horizontal padding for readability
        ) {
            Text(
                text = "Last Updated: September 30, 2025",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Section 1: Acceptance of Terms
            LegalSectionTitle("1. Acceptance of Terms")
            LegalBodyText(
                "By creating an account or using The Hobby Hub, you confirm that you have read, understood, and agree to be bound by these **Terms of Service**. If you do not agree with these Terms, you must not use the app.",
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Section 2: User Obligations & Misuse
            LegalSectionTitle("2. User Obligations & Misuse")
            LegalBodyText(
                "You agree to use The Hobby Hub responsibly. You are prohibited from posting any content that is **illegal**, **offensive**, **harassing**, or **hateful**; violating the intellectual property rights of others; using the app for any commercial purpose not explicitly approved by us; or misusing app features, such as the reporting or blocking functions.",
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Section 3: User Generated Content (UGC)
            LegalSectionTitle("3. User Generated Content (UGC)")
            LegalBodyText(
                "You retain all ownership rights to the content (photos, posts, comments) you create and post on The Hobby Hub. However, you grant us a worldwide, royalty-free license to use, reproduce, modify, and display your UGC solely for the purpose of operating and promoting the app.",
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Section 4: Account Suspension and Termination
            LegalSectionTitle("4. Account Suspension and Termination")
            LegalBodyText(
                "We reserve the right to **suspend** or **terminate** your account immediately, without prior notice, if you breach any of these Terms. This includes misuse, fraudulent activity, or engaging in harmful behavior towards other users.",
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Section 5: Limitation of Liability
            LegalSectionTitle("5. Limitation of Liability")
            LegalBodyText(
                "The Hobby Hub is provided 'as is'. We are not liable for any indirect, incidental, special, consequential, or punitive damages, including loss of profits, data, or goodwill, resulting from your access to or use of the app."
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
// *** REMOVE LegalSectionTitle and LegalBodyText from here ***