package com.example.healthtracker.domain.model

enum class Gender { MALE, FEMALE }

enum class Goal { LOSE, MAINTAIN, GAIN }

enum class MealType { BREAKFAST, LUNCH, DINNER, SNACK }

enum class BmiCategory { UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESE }

enum class ActivityLevel(val factor: Double) {
    SEDENTARY(1.2),
    LIGHT(1.375),
    MODERATE(1.55),
    ACTIVE(1.725),
    VERY_ACTIVE(1.9)
}