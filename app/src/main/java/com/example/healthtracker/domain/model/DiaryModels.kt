package com.example.healthtracker.domain.model

import java.time.LocalDate

data class DailySummary(
    val date: LocalDate,
    val goalCalories: Int,
    val eatenCalories: Int,
    val burnedCalories: Int,
    val remainingCalories: Int,
    val balanceCalories: Int
)

enum class DailyAdviceType {
    NEED_MORE,
    ON_TARGET,
    OVER
}

data class DailyAdvice(
    val type: DailyAdviceType,
    val differenceCalories: Int
)
