package com.example.healthtracker.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "activity_entry", indices = [Index("date")])
data class ActivityEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate,
    val activityTypeId: Long,
    val activityName: String,
    val activityNameEn: String,
    val durationMinutes: Int,
    val caloriesBurned: Int
)