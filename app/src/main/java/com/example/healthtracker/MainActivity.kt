package com.example.healthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.healthtracker.core.navigation.Dashboard
import com.example.healthtracker.core.navigation.MainScaffold
import com.example.healthtracker.presentation.theme.HealthTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthTrackerTheme {
                MainScaffold(Dashboard)
            }
        }
    }
}

