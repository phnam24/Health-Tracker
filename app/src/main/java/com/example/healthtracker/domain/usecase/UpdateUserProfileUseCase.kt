package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.OnboardingDraft
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.domain.model.OnboardingValidationError
import com.example.healthtracker.domain.repository.UserRepository
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

sealed interface UpdateUserProfileResult {
    data class Success(
        val newTargetCalories: Int,
    ) : UpdateUserProfileResult

    data class InvalidData(
        val errors: Map<OnboardingField, OnboardingValidationError>,
    ) : UpdateUserProfileResult
}

class UpdateUserProfileUseCase @Inject constructor(
    private val validate: ValidateOnboardingUseCase,
    private val buildProfile: BuildUserProfileUseCase,
    private val userRepository: UserRepository,
    private val calculateTdee: CalculateTdeeUseCase,
    private val clock: Clock,
) {
    suspend operator fun invoke(
        draft: OnboardingDraft,
    ): UpdateUserProfileResult {
        val errors = validate(draft)
        if (errors.isNotEmpty()) {
            return UpdateUserProfileResult.InvalidData(errors)
        }

        val profile = checkNotNull(buildProfile(draft)) {
            "A validated profile draft must produce a UserProfile"
        }

        userRepository.saveProfile(profile)

        val targetCalories = calculateTdee(
            profile = profile,
            today = LocalDate.now(clock),
        ).target

        return UpdateUserProfileResult.Success(
            newTargetCalories = targetCalories,
        )
    }
}