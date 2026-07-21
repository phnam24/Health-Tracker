package com.example.healthtracker.presentation.root.state

import com.example.healthtracker.domain.model.AppSettings

sealed interface RootUiState {
    data object Loading : RootUiState
    data class NeedsOnboarding(val settings: AppSettings) : RootUiState
    data class Ready(val settings: AppSettings) : RootUiState
}