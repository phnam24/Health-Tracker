package com.example.healthtracker.domain.model

import java.time.LocalDate

data class ActivityEntry(
    val id: Long,
    val date: LocalDate,
    val activityTypeId: Long,
    val activityName: String,
    val activityNameEn: String,
    val durationMinutes: Int,
    val caloriesBurned: Int
)