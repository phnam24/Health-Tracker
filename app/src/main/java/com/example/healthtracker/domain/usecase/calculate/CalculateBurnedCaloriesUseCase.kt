package com.example.healthtracker.domain.usecase.calculate

import kotlin.math.roundToInt

class CalculateBurnedCaloriesUseCase {
    operator fun invoke(
        met: Double,
        weightKg: Double,
        durationMinutes: Int
    ): Int {
        require(met > 0 && weightKg > 0 && durationMinutes >= 0)
        return (met * weightKg * durationMinutes / 60.0).roundToInt()
    }
}