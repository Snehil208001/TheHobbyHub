package com.example.thehobbyhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.thehobbyhub.core.navigations.NavGraph
import com.example.thehobbyhub.ui.theme.TheHobbyHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheHobbyHubTheme(darkTheme = false) {
                // NavGraph() is assumed to be your Composable function
                // that handles navigation between screens.
                NavGraph()
            }
        }
    }
}