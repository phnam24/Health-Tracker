package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.Gender

class CalculateBmrUseCase {
    operator fun invoke(
        gender: Gender,
        weightKg: Double,
        heightCm: Double,
        age: Int
    ): Double {
        require(weightKg > 0 && heightCm > 0 && age >= 0)
        val genderAdjustment = if (gender == Gender.MALE) 5 else -161
        return 10 * weightKg + 6.25 * heightCm - 5 * age + genderAdjustment
    }
}