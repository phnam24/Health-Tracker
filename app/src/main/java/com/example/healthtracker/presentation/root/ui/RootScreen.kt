package com.example.healthtracker.presentation.root.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.example.healthtracker.core.navigation.Dashboard
import com.example.healthtracker.core.navigation.MainScaffold
import com.example.healthtracker.core.navigation.Onboarding
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.presentation.components.AppScreenBackground
import com.example.healthtracker.presentation.root.state.RootUiState
import com.example.healthtracker.presentation.root.viewmodel.RootViewModel
import com.example.healthtracker.presentation.splash.SplashScreen
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun RootRoute(
    viewModel: RootViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RootScreen(uiState = uiState)
}

@Composable
fun RootScreen(
    uiState: RootUiState,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        RootUiState.Loading -> {
            HealthTrackerTheme {
                SplashScreen(modifier = modifier)
            }
        }

        is RootUiState.NeedsOnboarding -> {
            ThemedMainScaffold(
                settings = uiState.settings,
                startKey = Onboarding,
                modifier = modifier,
            )
        }

        is RootUiState.Ready -> {
            ThemedMainScaffold(
                settings = uiState.settings,
                startKey = Dashboard,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun ThemedMainScaffold(
    settings: AppSettings,
    startKey: NavKey,
    modifier: Modifier = Modifier,
) {
    HealthTrackerTheme(
        themeMode = settings.themeMode,
        palette = settings.palette,
        fontScale = settings.fontScale,
    ) {
        AppScreenBackground(modifier = modifier.fillMaxSize()) {
            MainScaffold(
                startKey = startKey,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
