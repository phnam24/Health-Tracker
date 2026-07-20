package com.example.healthtracker.presentation.statistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.usecase.ObserveWeeklyStatsUseCase
import com.example.healthtracker.presentation.statistics.state.StatisticsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Clock

sealed interface StatisticsEvent {
    data object RetryClicked : StatisticsEvent
}

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val observeWeeklyStats: ObserveWeeklyStatsUseCase,
    private val uiMapper: StatisticsUiMapper,
    private val clock: Clock
) : ViewModel() {
    private val retryTrigger = MutableStateFlow(0)
    private val endDate = LocalDate.now(clock)

    val uiState: StateFlow<StatisticsUiState> = retryTrigger
        .flatMapLatest {
            observeWeeklyStats(endDate)
                .map(uiMapper::map)
                .onStart { emit(StatisticsUiState(isLoading = true)) }
                .catch { error ->
                    if (error is CancellationException) throw error
                    emit(StatisticsUiState(isLoading = false, loadFailed = true))
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StatisticsUiState()
        )

    fun onEvent(event: StatisticsEvent) {
        when (event) {
            StatisticsEvent.RetryClicked -> retryTrigger.update { it + 1 }
        }
    }
}