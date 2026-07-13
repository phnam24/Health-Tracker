package com.example.healthtracker.presentation.root.state

sealed interface RootUiState {
    data object Loading : RootUiState
    data object NeedsOnboarding : RootUiState
    data object Ready : RootUiState
}