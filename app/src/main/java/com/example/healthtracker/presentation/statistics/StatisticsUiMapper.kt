package com.example.healthtracker.presentation.statistics

import com.example.healthtracker.domain.model.StatisticsSnapshot
import com.example.healthtracker.presentation.statistics.state.StatisticsUiState
import java.time.LocalDate
import javax.inject.Inject

class StatisticsUiMapper @Inject constructor() {
    fun map(
        snapshot: StatisticsSnapshot?,
        selectedWeekStart: LocalDate,
        currentWeekStart: LocalDate
    ): StatisticsUiState {
        if (snapshot == null) {
            return StatisticsUiState(
                selectedWeekStart = selectedWeekStart,
                currentWeekStart = currentWeekStart,
                isLoading = false,
                loadFailed = false,
                isRecentIntakeEmpty = true,
                isSelectedWeekEmpty = true
            )
        }

        return StatisticsUiState(
            selectedWeekStart = snapshot.selectedWeek.startDate,
            currentWeekStart = currentWeekStart,
            snapshot = snapshot,
            isLoading = false,
            loadFailed = false,
            isRecentIntakeEmpty = !snapshot.recentIntake.hasAnyData,
            isSelectedWeekEmpty = !snapshot.selectedWeek.hasAnyData
        )
    }
}
