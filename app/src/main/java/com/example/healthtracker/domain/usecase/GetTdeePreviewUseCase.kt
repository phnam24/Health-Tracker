package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.OnboardingDraft
import com.example.healthtracker.domain.model.TdeeBreakdown
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class GetTdeePreviewUseCase @Inject constructor(
    private val buildUserProfile: BuildUserProfileUseCase,
    private val calculateTdee: CalculateTdeeUseCase,
    private val clock: Clock,
) {

    operator fun invoke(draft: OnboardingDraft): TdeeBreakdown? {
        val profile = buildUserProfile(draft) ?: return null
        return calculateTdee(
            profile = profile,
            today = LocalDate.now(clock),
        )
    }
}
