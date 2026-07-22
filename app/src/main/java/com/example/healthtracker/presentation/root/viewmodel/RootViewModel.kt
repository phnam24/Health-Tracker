package com.example.healthtracker.presentation.root.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.repository.SettingsRepository
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.presentation.root.state.RootUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class RootViewModel @Inject constructor(
    userRepository: UserRepository,
    settingsRepository: SettingsRepository,
) : ViewModel() {
    val uiState: StateFlow<RootUiState> =
        combine(
            userRepository.observeProfile(),
            settingsRepository.observeSettings(),
        ) { profile, settings ->
            val state: RootUiState = if (profile != null) {
                RootUiState.Ready(settings)
            } else {
                RootUiState.NeedsOnboarding(settings)
            }
            state
        }
            .onStart {
                delay(3000.milliseconds)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RootUiState.Loading
            )
}