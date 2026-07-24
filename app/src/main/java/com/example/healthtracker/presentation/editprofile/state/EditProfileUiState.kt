package com.example.healthtracker.presentation.editprofile.state

import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.BmiResult
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.domain.model.OnboardingValidationError
import com.example.healthtracker.domain.model.TdeeBreakdown
import java.time.LocalDate

data class EditProfileUiState(
    val isLoading: Boolean = true,
    val loadFailed: Boolean = false,
    val name: String = "",
    val birthDate: LocalDate? = null,
    val gender: Gender? = null,
    val weightInput: String = "",
    val heightInput: String = "",
    val activityLevel: ActivityLevel? = null,
    val goal: Goal? = null,
    val bmiPreview: BmiResult? = null,
    val tdeePreview: TdeeBreakdown? = null,
    val errors: Map<OnboardingField, OnboardingValidationError> = emptyMap(),
    val isFormValid: Boolean = false,
    val isDirty: Boolean = false,
    val isSaving: Boolean = false,
) {
    val canSave: Boolean
        get() = !isLoading &&
                !loadFailed &&
                !isSaving &&
                isDirty &&
                isFormValid
}