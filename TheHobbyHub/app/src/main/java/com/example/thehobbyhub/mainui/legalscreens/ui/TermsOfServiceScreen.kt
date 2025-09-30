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
fun TermsOfServiceScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Terms of Service", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Last Updated: September 30, 2025", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))

        Text("1. Acceptance of Terms", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(
            "By creating an account or using The Hobby Hub, you confirm that you have read, understood, and agree to be bound by these Terms. If you do not agree with these Terms, you must not use the app.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("2. User Obligations & Misuse", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(
            "You agree to use The Hobby Hub responsibly. You are prohibited from posting any content that is illegal, offensive, harassing, or hateful; violating the intellectual property rights of others; using the app for any commercial purpose not explicitly approved by us; or misusing app features, such as the reporting or blocking functions.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Add other sections (UGC, Account Termination, etc.) in the same format
    }
}