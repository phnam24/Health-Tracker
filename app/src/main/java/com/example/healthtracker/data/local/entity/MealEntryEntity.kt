package com.example.healthtracker.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.healthtracker.domain.model.MealType
import java.time.LocalDate

@Entity(tableName = "meal_entry", indices = [Index("date")])
data class MealEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate,
    val mealType: MealType,
    val foodId: Long,
    val foodName: String,
    val quantity: Double,
    val calories: Int
)