package com.example.healthtracker.domain.usecase.onboarding

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthtracker.domain.model.OnboardingDraft
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.domain.model.OnboardingValidationError
import com.example.healthtracker.domain.repository.UserRepository
import javax.inject.Inject

sealed interface CompleteOnboardingResult {

    data object Success : CompleteOnboardingResult

    data class InvalidData(
        val errors: Map<OnboardingField, OnboardingValidationError>
    ) : CompleteOnboardingResult
}

@RequiresApi(Build.VERSION_CODES.O)
class CompleteOnboardingUseCase @Inject constructor(
    private val validateOnboarding: ValidateOnboardingUseCase,
    private val buildUserProfile: BuildUserProfileUseCase,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        draft: OnboardingDraft
    ): CompleteOnboardingResult {
        val errors = validateOnboarding(draft)

        if (errors.isNotEmpty()) {
            return CompleteOnboardingResult.InvalidData(errors)
        }

        val profile = checkNotNull(buildUserProfile(draft)) {
            "A validated onboarding draft must produce a UserProfile"
        }

        userRepository.saveProfile(profile)

        return CompleteOnboardingResult.Success
    }
}