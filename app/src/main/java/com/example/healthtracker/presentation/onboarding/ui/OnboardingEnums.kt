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