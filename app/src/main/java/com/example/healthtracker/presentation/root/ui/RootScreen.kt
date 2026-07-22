package com.example.healthtracker.presentation.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.example.healthtracker.core.navigation.Dashboard
import com.example.healthtracker.core.navigation.MainScaffold
import com.example.healthtracker.core.navigation.Onboarding
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.presentation.root.state.RootUiState
import com.example.healthtracker.presentation.root.viewmodel.RootViewModel
import com.example.healthtracker.presentation.splash.SplashScreen
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun RootScreen(
    viewModel: RootViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (val currentState = state) {
        RootUiState.Loading -> {
            HealthTrackerTheme {
                SplashScreen()
            }
        }

        is RootUiState.NeedsOnboarding -> {
            ThemedMainScaffold(
                settings = currentState.settings,
                startKey = Onboarding,
            )
        }

        is RootUiState.Ready -> {
            ThemedMainScaffold(
                settings = currentState.settings,
                startKey = Dashboard,
            )
        }
    }
}

@Composable
private fun ThemedMainScaffold(
    settings: AppSettings,
    startKey: NavKey,
) {
    HealthTrackerTheme(
        themeMode = settings.themeMode,
        palette = settings.palette,
        fontScale = settings.fontScale,
    ) {
        MainScaffold(startKey = startKey)
    }
}