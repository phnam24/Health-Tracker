package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.ActivityEntry
import com.example.healthtracker.domain.model.AddActivityError
import com.example.healthtracker.domain.model.AddActivityInput
import com.example.healthtracker.domain.model.AddActivityResult
import com.example.healthtracker.domain.repository.ActivityRepository
import com.example.healthtracker.domain.repository.UserRepository
import java.time.LocalDate
import javax.inject.Inject
import java.time.Clock

class AddActivityEntryUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository,
    private val calculateBurnedCalories: CalculateBurnedCaloriesUseCase,
    private val clock: Clock
) {
    suspend operator fun invoke(input: AddActivityInput): AddActivityResult {
        if (input.date != LocalDate.now(clock)) {
            return AddActivityResult.Invalid(AddActivityError.DATE_NOT_TODAY)
        }
        val type = input.activityType
            ?: return AddActivityResult.Invalid(AddActivityError.ACTIVITY_REQUIRED)
        val minutes = parseDuration(input.durationText)
            ?: return AddActivityResult.Invalid(durationError(input.durationText))
        val profile = userRepository.getProfile()
            ?: return AddActivityResult.Invalid(AddActivityError.PROFILE_REQUIRED)

        val calories = calculateBurnedCalories(
            met = type.met,
            weightKg = profile.weightKg,
            durationMinutes = minutes
        )
        val id = activityRepository.addEntry(
            ActivityEntry(
                id = 0,
                date = input.date,
                activityTypeId = type.id,
                activityName = type.name,
                activityNameEn = type.nameEn.ifBlank { type.name },
                durationMinutes = minutes,
                caloriesBurned = calories
            )
        )
        return AddActivityResult.Success(id)
    }
}