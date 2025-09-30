package com.example.thehobbyhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.thehobbyhub.core.navigations.NavGraph
import com.example.thehobbyhub.ui.theme.TheHobbyHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheHobbyHubTheme(darkTheme = false) {
                NavGraph()
            }
        }
    }
}