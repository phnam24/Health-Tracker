package com.example.healthtracker.presentation.dashboard.state

import com.example.healthtracker.domain.model.DailyAdvice
import com.example.healthtracker.domain.model.DailySummary

data class DashboardUiState(
    val userName: String = "",
    val summary: DailySummary? = null,
    val advice: DailyAdvice? = null,
    val isLoading: Boolean = true,
    val loadFailed: Boolean = false,
)
