package com.example.healthtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val nameEn: String,
    val caloriesPerUnit: Int,
    val unit: String,
    val isCustom: Boolean = false
)