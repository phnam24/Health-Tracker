package com.example.healthtracker.presentation.onboarding.ui

enum class OnboardingStep {
    NAME,
    PERSONAL_INFO,
    BODY_METRICS,
    ACTIVITY_LEVEL,
    GOAL;

    val position: Int get() = ordinal + 1
    val isFirst: Boolean get() = this == NAME
    val isLast: Boolean get() = this == GOAL

    fun next(): OnboardingStep = entries.getOrElse(ordinal + 1) { this }
    fun previous(): OnboardingStep = entries.getOrElse(ordinal - 1) { this }
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

enum class ValidationError {
    REQUIRED,
    NAME_TOO_LONG,
    BIRTH_DATE_IN_FUTURE,
    AGE_OUT_OF_RANGE,
    INVALID_NUMBER,
    WEIGHT_OUT_OF_RANGE,
    HEIGHT_OUT_OF_RANGE
}