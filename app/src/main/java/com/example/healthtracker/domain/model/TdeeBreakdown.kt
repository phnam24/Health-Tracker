package com.example.healthtracker.domain.model

data class TdeeBreakdown(
    val bmr: Int,
    val tdee: Int,
    val goalAdjustment: Int,
    val target: Int
)