package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.ActivityCaloriesPreview
import com.example.healthtracker.domain.model.ActivityPreviewResult
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.model.AddActivityError
import com.example.healthtracker.domain.repository.UserRepository
import javax.inject.Inject

class PreviewActivityCaloriesUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val calculateBurnedCalories: CalculateBurnedCaloriesUseCase
) {
    suspend operator fun invoke(
        activityType: ActivityType?,
        durationText: String
    ): ActivityPreviewResult {
        if (activityType == null) {
            return ActivityPreviewResult.Invalid(AddActivityError.ACTIVITY_REQUIRED)
        }

        val minutes = parseDuration(durationText)
            ?: return ActivityPreviewResult.Invalid(durationError(durationText))
        val profile = userRepository.getProfile()
            ?: return ActivityPreviewResult.Invalid(AddActivityError.PROFILE_REQUIRED)

        return ActivityPreviewResult.Success(
            ActivityCaloriesPreview(
                durationMinutes = minutes,
                caloriesBurned = calculateBurnedCalories(
                    met = activityType.met,
                    weightKg = profile.weightKg,
                    durationMinutes = minutes
                )
            )
        )
    }
}