package com.example.healthtracker.presentation.statistics.state

import com.example.healthtracker.domain.model.StatisticsSnapshot
import java.time.LocalDate

data class StatisticsUiState(
    val today: LocalDate? = null,
    val selectedWeekStart: LocalDate? = null,
    val currentWeekStart: LocalDate? = null,
    val snapshot: StatisticsSnapshot? = null,
    val isLoading: Boolean = true,
    val loadFailed: Boolean = false,
    val isRecentIntakeEmpty: Boolean = false,
    val isSelectedWeekEmpty: Boolean = false
) {
    val canGoNext: Boolean
        get() = selectedWeekStart != null &&
            currentWeekStart != null &&
            selectedWeekStart.isBefore(currentWeekStart)
}
