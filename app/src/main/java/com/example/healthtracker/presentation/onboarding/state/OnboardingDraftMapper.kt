package com.example.healthtracker.presentation.onboarding.state

import com.example.healthtracker.domain.model.OnboardingDraft

fun OnboardingUiState.toDraft(): OnboardingDraft =
    OnboardingDraft(
        name = name,
        birthDate = birthDate,
        gender = gender,
        weightInput = weightInput,
        heightInput = heightInput,
        activityLevel = activityLevel,
        goal = goal,
    )