package com.example.healthtracker.domain.model

data class Food(
    val id: Long,
    val name: String,
    val nameEn: String,
    val caloriesPerUnit: Int,
    val unit: String,
    val isCustom: Boolean
)