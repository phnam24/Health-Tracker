package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.BmiCategory
import com.example.healthtracker.domain.model.BmiResult

class CalculateBmiUseCase {
    operator fun invoke(weightKg: Double, heightCm: Double): BmiResult {
        require(weightKg > 0 && heightCm > 0)
        val heightMeter = heightCm / 100.0
        val bmi = weightKg / (heightMeter * heightMeter)
        val category = when {
            bmi < 18.5 -> BmiCategory.UNDERWEIGHT
            bmi < 25.0 -> BmiCategory.NORMAL
            bmi < 30.0 -> BmiCategory.OVERWEIGHT
            else -> BmiCategory.OBESE
        }
        return BmiResult(bmi, category)
    }
}