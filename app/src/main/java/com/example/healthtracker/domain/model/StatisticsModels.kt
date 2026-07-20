package com.example.healthtracker.domain.model

import java.time.LocalDate

data class WeeklyStats(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val points: List<DayPoint>,
    val averageEatenCalories: Int,
    val averageBurnedCalories: Int,
    val goalDaysHit: Int
) {
    data class DayPoint(
        val date: LocalDate,
        val eatenCalories: Int,
        val burnedCalories: Int,
        val goalCalories: Int,
        val remainingCalories: Int,
        val hasData: Boolean,
        val isGoalHit: Boolean
    )

    val hasAnyData: Boolean
        get() = points.any(DayPoint::hasData)
}