package com.example.healthtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.healthtracker.data.local.dao.ActivityEntryDao
import com.example.healthtracker.data.local.dao.ActivityTypeDao
import com.example.healthtracker.data.local.dao.FoodDao
import com.example.healthtracker.data.local.dao.MealEntryDao
import com.example.healthtracker.data.local.dao.UserProfileDao
import com.example.healthtracker.data.local.entity.ActivityEntryEntity
import com.example.healthtracker.data.local.entity.ActivityTypeEntity
import com.example.healthtracker.data.local.entity.FoodEntity
import com.example.healthtracker.data.local.entity.MealEntryEntity
import com.example.healthtracker.data.local.entity.UserProfileEntity

@Database(
    entities = [
        UserProfileEntity::class,
        FoodEntity::class,
        ActivityTypeEntity::class,
        MealEntryEntity::class,
        ActivityEntryEntity::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun foodDao(): FoodDao
    abstract fun activityTypeDao(): ActivityTypeDao
    abstract fun mealEntryDao(): MealEntryDao
    abstract fun activityEntryDao(): ActivityEntryDao
}