package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.OnboardingDraft
import com.example.healthtracker.domain.model.OnboardingPolicy
import com.example.healthtracker.domain.model.UserProfile
import javax.inject.Inject

class BuildUserProfileUseCase @Inject constructor() {

    operator fun invoke(draft: OnboardingDraft): UserProfile? {
        val name = draft.name
            .trim()
            .takeIf {
                it.isNotEmpty() &&
                        it.length <= OnboardingPolicy.NAME_MAX_LENGTH
            }
            ?: return null

        val birthDate = draft.birthDate ?: return null
        val gender = draft.gender ?: return null

        val weight = draft.weightInput
            .toDoubleOrNull()
            ?.takeIf {
                it in OnboardingPolicy.WEIGHT_MIN..OnboardingPolicy.WEIGHT_MAX
            }
            ?: return null

        val height = draft.heightInput
            .toDoubleOrNull()
            ?.takeIf {
                it in OnboardingPolicy.HEIGHT_MIN..OnboardingPolicy.HEIGHT_MAX
            }
            ?: return null

        val activityLevel = draft.activityLevel ?: return null
        val goal = draft.goal ?: return null

        return UserProfile(
            name = name,
            birthDate = birthDate,
            gender = gender,
            weightKg = weight,
            heightCm = height,
            activityLevel = activityLevel,
            goal = goal
        )
    }
}