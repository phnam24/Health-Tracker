package com.example.healthtracker.presentation.editprofile.state

import com.example.healthtracker.domain.model.OnboardingDraft
import com.example.healthtracker.domain.model.UserProfile
import com.example.healthtracker.presentation.components.toEditableDecimal

fun EditProfileUiState.toDraft(): OnboardingDraft =
    OnboardingDraft(
        name = name,
        birthDate = birthDate,
        gender = gender,
        weightInput = weightInput,
        heightInput = heightInput,
        activityLevel = activityLevel,
        goal = goal,
    )

fun UserProfile.toEditProfileUiState(): EditProfileUiState =
    EditProfileUiState(
        isLoading = false,
        name = name,
        birthDate = birthDate,
        gender = gender,
        weightInput = weightKg.toEditableDecimal(),
        heightInput = heightCm.toEditableDecimal(),
        activityLevel = activityLevel,
        goal = goal,
    )