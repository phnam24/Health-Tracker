package com.example.healthtracker.domain.model

import java.time.LocalDate

data class MealEntry(
    val id: Long,
    val date: LocalDate,
    val mealType: MealType,
    val foodId: Long,
    val foodName: String,
    val foodNameEn: String,
    val quantity: Double,
    val calories: Int
)