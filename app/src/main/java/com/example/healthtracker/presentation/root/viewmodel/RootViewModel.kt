package com.example.healthtracker.presentation.root.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.presentation.root.state.RootUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class RootViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {
    val uiState: StateFlow<RootUiState> =
        userRepository.observeProfile()
            .onStart {
                delay(3000.milliseconds)
            }
            .map { profile ->
                if (profile != null) RootUiState.Ready
                else RootUiState.NeedsOnboarding
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RootUiState.Loading
            )
}