package com.example.healthtracker.presentation.statistics

import com.example.healthtracker.domain.model.WeeklyStats
import com.example.healthtracker.presentation.statistics.state.StatisticsUiState
import javax.inject.Inject

class StatisticsUiMapper @Inject constructor() {
    fun map(stats: WeeklyStats?): StatisticsUiState {
        if (stats == null) {
            return StatisticsUiState(
                isLoading = false,
                loadFailed = false,
                isEmpty = true
            )
        }

        return StatisticsUiState(
            stats = stats,
            isLoading = false,
            loadFailed = false,
            isEmpty = !stats.hasAnyData
        )
    }
}