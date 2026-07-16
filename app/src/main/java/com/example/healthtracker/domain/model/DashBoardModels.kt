package com.example.healthtracker.domain.model

data class DashboardData(
    val userName: String,
    val summary: DailySummary,
    val advice: DailyAdvice
)