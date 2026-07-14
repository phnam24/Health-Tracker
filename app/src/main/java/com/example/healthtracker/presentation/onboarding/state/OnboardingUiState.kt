package com.example.healthtracker.presentation.onboarding.state

import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.BmiResult
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.domain.model.OnboardingValidationError
import com.example.healthtracker.domain.model.TdeeBreakdown
import com.example.healthtracker.presentation.onboarding.ui.OnboardingStep
import java.time.LocalDate

data class OnboardingUiState(
    val currentStep: OnboardingStep = OnboardingStep.NAME,
    val name: String = "",
    val birthDate: LocalDate? = null,
    val age: Int? = null,
    val gender: Gender? = null,
    val weightInput: String = "",
    val heightInput: String = "",
    val bmiPreview: BmiResult? = null,
    val activityLevel: ActivityLevel? = null,
    val goal: Goal? = null,
    val tdeePreview: TdeeBreakdown? = null,
    val errors: Map<OnboardingField, OnboardingValidationError> = emptyMap(),
    val isSaving: Boolean = false
) {
    val progress: Float
        get() = currentStep.position / OnboardingStep.entries.size.toFloat()
}