package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.OnboardingDraft
import com.example.healthtracker.domain.model.TdeeBreakdown
import javax.inject.Inject

class GetTdeePreviewUseCase @Inject constructor(
    private val buildUserProfile: BuildUserProfileUseCase,
    private val calculateTdee: CalculateTdeeUseCase
) {

    operator fun invoke(draft: OnboardingDraft): TdeeBreakdown? {
        val profile = buildUserProfile(draft) ?: return null
        return calculateTdee(profile)
    }
}
