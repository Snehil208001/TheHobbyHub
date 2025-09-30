// snehil208001/thehobbyhub/TheHobbyHub-d30e7e1f3c7086c270533e7095de4d39ff2ade19/TheHobbyHub/app/src/main/java/com/example/thehobbyhub/mainui/cityselectionscreen/ui/CitySelectionScreen.kt
package com.example.thehobbyhub.mainui.cityselectionscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun CitySelectionScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select Your City")
        // TODO: Add city selection UI
    }
}