package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.BmiResult
import com.example.healthtracker.domain.model.OnboardingDraft
import com.example.healthtracker.domain.model.OnboardingPolicy
import javax.inject.Inject

class GetBmiPreviewUseCase @Inject constructor(
    private val calculateBmi: CalculateBmiUseCase
) {

    operator fun invoke(draft: OnboardingDraft): BmiResult? {
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

        return calculateBmi(
            weightKg = weight,
            heightCm = height
        )
    }
}