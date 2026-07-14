package com.example.healthtracker.domain.usecase.onboarding

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthtracker.domain.model.OnboardingDraft
import com.example.healthtracker.domain.model.TdeeBreakdown
import com.example.healthtracker.domain.usecase.calculate.CalculateTdeeUseCase
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class GetTdeePreviewUseCase @Inject constructor(
    private val buildUserProfile: BuildUserProfileUseCase,
    private val calculateTdee: CalculateTdeeUseCase
) {

    operator fun invoke(draft: OnboardingDraft): TdeeBreakdown? {
        val profile = buildUserProfile(draft) ?: return null
        return calculateTdee(profile)
    }
}