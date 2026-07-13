package com.example.healthtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import java.time.LocalDate

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val weightKg: Double,
    val heightCm: Double,
    val activityLevel: ActivityLevel,
    val goal: Goal
)
