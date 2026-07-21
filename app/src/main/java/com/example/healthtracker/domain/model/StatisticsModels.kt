package com.example.healthtracker.domain.model

import java.time.LocalDate

data class StatisticsSnapshot(
    val recentIntake: RecentIntakeStats,
    val selectedWeek: WeeklyStats
) {
    val hasAnyData: Boolean
        get() = recentIntake.hasAnyData || selectedWeek.hasAnyData
}

data class RecentIntakeStats(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val points: List<DailyCaloriePoint>
) {
    val hasAnyData: Boolean
        get() = points.any { it.eatenCalories > 0 }
}

data class WeeklyStats(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val points: List<DailyCaloriePoint>,
    val averageEatenCalories: Int,
    val averageBurnedCalories: Int,
    val goalDaysHit: Int,
    val observedDayCount: Int
) {
    val hasAnyData: Boolean
        get() = points.any(DailyCaloriePoint::hasData)
}

data class DailyCaloriePoint(
    val date: LocalDate,
    val eatenCalories: Int,
    val burnedCalories: Int,
    val goalCalories: Int,
    val remainingCalories: Int,
    val hasData: Boolean,
    val isGoalHit: Boolean,
    val isFuture: Boolean = false
)
