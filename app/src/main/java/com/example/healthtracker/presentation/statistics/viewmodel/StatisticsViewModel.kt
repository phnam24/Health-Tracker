package com.example.healthtracker.presentation.statistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.core.time.currentDateFlow
import com.example.healthtracker.domain.usecase.ObserveWeeklyStatsUseCase
import com.example.healthtracker.presentation.statistics.StatisticsUiMapper
import com.example.healthtracker.presentation.statistics.state.StatisticsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.Clock
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

sealed interface StatisticsEvent {
    data object PreviousWeekClicked : StatisticsEvent
    data object NextWeekClicked : StatisticsEvent
    data object CurrentWeekClicked : StatisticsEvent
    data object RetryClicked : StatisticsEvent
}

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val observeWeeklyStats: ObserveWeeklyStatsUseCase,
    private val uiMapper: StatisticsUiMapper,
    private val clock: Clock,
) : ViewModel() {
    private val selectedWeekStart = MutableStateFlow<LocalDate?>(null)
    private val retryTrigger = MutableStateFlow(0)
    private val todayFlow = currentDateFlow(clock)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<StatisticsUiState> = combine(
        selectedWeekStart,
        retryTrigger,
        todayFlow,
    ) { requestedWeekStart, _, today ->
        val currentWeekStart = today.currentWeekStart()
        StatisticsRequest(
            today = today,
            currentWeekStart = currentWeekStart,
            selectedWeekStart = requestedWeekStart?.coerceAtMost(currentWeekStart)
                ?: currentWeekStart,
        )
    }
        .flatMapLatest { request ->
            val today = request.today
            val weekStart = request.selectedWeekStart
            val currentWeekStart = request.currentWeekStart
            observeWeeklyStats(weekStart, today)
                .map { snapshot ->
                    uiMapper.map(snapshot, today, weekStart, currentWeekStart)
                }
                .onStart {
                    emit(
                        StatisticsUiState(
                            today = today,
                            selectedWeekStart = weekStart,
                            currentWeekStart = currentWeekStart,
                            isLoading = true
                        )
                    )
                }
                .catch { error ->
                    if (error is CancellationException) throw error
                    emit(
                        StatisticsUiState(
                            today = today,
                            selectedWeekStart = weekStart,
                            currentWeekStart = currentWeekStart,
                            isLoading = false,
                            loadFailed = true
                        )
                    )
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StatisticsUiState()
        )

    fun onEvent(event: StatisticsEvent) {
        when (event) {
            StatisticsEvent.PreviousWeekClicked -> selectedWeekStart.update {
                (it ?: LocalDate.now(clock).currentWeekStart()).minusWeeks(1)
            }

            StatisticsEvent.NextWeekClicked -> selectedWeekStart.update {
                val currentWeekStart = LocalDate.now(clock).currentWeekStart()
                val nextWeekStart = (it ?: currentWeekStart).plusWeeks(1)
                nextWeekStart.takeIf { next -> next.isBefore(currentWeekStart) }
            }

            StatisticsEvent.CurrentWeekClicked -> {
                selectedWeekStart.value = null
            }

            StatisticsEvent.RetryClicked -> retryTrigger.update { it + 1 }
        }
    }
}

private data class StatisticsRequest(
    val today: LocalDate,
    val selectedWeekStart: LocalDate,
    val currentWeekStart: LocalDate,
)

private fun LocalDate.currentWeekStart(): LocalDate =
    with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
