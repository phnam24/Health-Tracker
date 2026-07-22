package com.example.healthtracker.domain.model

import java.time.LocalDate

data class OnboardingDraft(
    val name: String,
    val birthDate: LocalDate?,
    val gender: Gender?,
    val weightInput: String,
    val heightInput: String,
    val activityLevel: ActivityLevel?,
    val goal: Goal?
)

object OnboardingPolicy {
    const val NAME_MAX_LENGTH = 50

    const val AGE_MIN = 10
    const val AGE_MAX = 100

    const val WEIGHT_MIN = 20.0
    const val WEIGHT_MAX = 300.0

    const val HEIGHT_MIN = 100.0
    const val HEIGHT_MAX = 250.0
}

enum class OnboardingField {
    NAME,
    BIRTH_DATE,
    GENDER,
    WEIGHT,
    HEIGHT,
    ACTIVITY_LEVEL,
    GOAL
}

enum class OnboardingValidationError {
    REQUIRED,
    NAME_TOO_LONG,
    BIRTH_DATE_IN_FUTURE,
    AGE_OUT_OF_RANGE,
    INVALID_NUMBER,
    WEIGHT_OUT_OF_RANGE,
    HEIGHT_OUT_OF_RANGE
}
