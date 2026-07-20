package com.example.healthtracker.presentation.statistics.state

import com.example.healthtracker.domain.model.WeeklyStats

data class StatisticsUiState(
    val rangeLabel: String = "",
    val stats: WeeklyStats? = null,
    val isLoading: Boolean = true,
    val loadFailed: Boolean = false,
    val isEmpty: Boolean = false
)