package com.example.healthtracker.domain.model

import java.time.LocalDate

data class UserProfile(
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val weightKg: Double,
    val heightCm: Double,
    val activityLevel: ActivityLevel,
    val goal: Goal
)