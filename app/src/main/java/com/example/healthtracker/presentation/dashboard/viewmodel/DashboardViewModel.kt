package com.example.healthtracker.presentation.dashboard.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.usecase.ObserveDashboardUseCase
import com.example.healthtracker.presentation.dashboard.state.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

sealed interface DashboardEvent {
    data object AddMealClicked : DashboardEvent
    data object AddActivityClicked : DashboardEvent
    data object RetryClicked : DashboardEvent
}

sealed interface DashboardEffect {
    data object NavigateToDiary : DashboardEffect
    data object NavigateToActivity : DashboardEffect
}

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val observeDashboard: ObserveDashboardUseCase,
    clock: Clock
) : ViewModel() {
    private val today = LocalDate.now(clock)
    private val retryTrigger = MutableStateFlow(0)

    val uiState: StateFlow<DashboardUiState> =
        retryTrigger
            .flatMapLatest {
                observeDashboard(date = today)
                    .map { data ->
                        if (data == null) {
                            DashboardUiState(isLoading = false)
                        } else {
                            DashboardUiState(
                                userName = data.userName,
                                summary = data.summary,
                                advice = data.advice,
                                isLoading = false,
                            )
                        }
                    }
                    .onStart { emit(DashboardUiState(isLoading = true)) }
                    .catch {
                        emit(
                            DashboardUiState(
                                isLoading = false,
                                loadFailed = true,
                            )
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DashboardUiState()
            )

    private val _effects = Channel<DashboardEffect>(Channel.BUFFERED)
    val effects: Flow<DashboardEffect> = _effects.receiveAsFlow()

    private fun emitEffect(effect: DashboardEffect) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }

    fun onEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.AddMealClicked -> emitEffect(DashboardEffect.NavigateToDiary)
            DashboardEvent.AddActivityClicked -> emitEffect(DashboardEffect.NavigateToActivity)
            DashboardEvent.RetryClicked -> retryTrigger.update { it + 1 }
        }
    }
}
