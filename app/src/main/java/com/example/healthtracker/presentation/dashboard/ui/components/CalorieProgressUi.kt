package com.example.healthtracker.presentation.dashboard.ui.components

import com.example.healthtracker.domain.model.DailySummary
import kotlin.math.roundToInt

internal data class CalorieProgressUi(
    val indicatorFraction: Float,
    val percentage: Int,
    val isOverGoal: Boolean,
)

internal fun DailySummary.toCalorieProgressUi(): CalorieProgressUi {
    val rawFraction = if (goalCalories > 0) {
        eatenCalories.toFloat() / goalCalories
    } else {
        0f
    }

    return CalorieProgressUi(
        indicatorFraction = rawFraction.coerceIn(0f, 1f),
        percentage = (rawFraction.coerceAtLeast(0f) * 100).roundToInt(),
        isOverGoal = goalCalories > 0 && eatenCalories > goalCalories,
    )
}
