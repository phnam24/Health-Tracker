package com.example.healthtracker.domain.model

import java.time.LocalDate

data class ActivityDay(
    val date: LocalDate,
    val entries: List<ActivityEntry>,
    val totalBurnedCalories: Int,
    val totalDurationMinutes: Int,
    val activityCount: Int,
    val metByActivityTypeId: Map<Long, Double> = emptyMap()
)

data class AddActivityInput(
    val date: LocalDate,
    val activityType: ActivityType?,
    val durationText: String
)

data class ActivityCaloriesPreview(
    val durationMinutes: Int,
    val caloriesBurned: Int
)

enum class AddActivityError {
    PROFILE_REQUIRED,
    ACTIVITY_REQUIRED,
    DURATION_REQUIRED,
    DURATION_INVALID,
    DURATION_OUT_OF_RANGE,
    DATE_NOT_TODAY
}

sealed interface ActivityPreviewResult {
    data class Success(val preview: ActivityCaloriesPreview) : ActivityPreviewResult
    data class Invalid(val error: AddActivityError) : ActivityPreviewResult
}

sealed interface AddActivityResult {
    data class Success(val entryId: Long) : AddActivityResult
    data class Invalid(val error: AddActivityError) : AddActivityResult
}

object ActivityDurationRules {
    const val MIN_MINUTES = 1
    const val MAX_MINUTES = 1_440
}
