package com.example.healthtracker.presentation.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.core.navigation.Dashboard
import com.example.healthtracker.core.navigation.MainScaffold
import com.example.healthtracker.core.navigation.Onboarding
import com.example.healthtracker.presentation.root.state.RootUiState
import com.example.healthtracker.presentation.root.viewmodel.RootViewModel
import com.example.healthtracker.presentation.splash.SplashScreen

@Composable
fun RootScreen(
    viewModel: RootViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (state) {
        RootUiState.Loading -> SplashScreen()
        RootUiState.NeedsOnboarding -> MainScaffold(startKey = Onboarding)
        RootUiState.Ready -> MainScaffold(startKey = Dashboard)
    }
}