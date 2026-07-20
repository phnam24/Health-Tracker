package com.example.healthtracker.data.mapper

import com.example.healthtracker.data.local.entity.ActivityEntryEntity
import com.example.healthtracker.data.local.entity.ActivityTypeEntity
import com.example.healthtracker.data.local.entity.FoodEntity
import com.example.healthtracker.data.local.entity.MealEntryEntity
import com.example.healthtracker.data.local.entity.UserProfileEntity
import com.example.healthtracker.domain.model.ActivityEntry
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.model.Food
import com.example.healthtracker.domain.model.MealEntry
import com.example.healthtracker.domain.model.UserProfile

fun FoodEntity.toDomain() = Food(
    id = id,
    name = name,
    nameEn = nameEn,
    caloriesPerUnit = caloriesPerUnit,
    unit = unit,
    isCustom = isCustom
)

fun Food.toEntity() = FoodEntity(
    id = id,
    name = name,
    nameEn = nameEn,
    caloriesPerUnit = caloriesPerUnit,
    unit = unit,
    isCustom = isCustom
)

fun UserProfileEntity.toDomain() = UserProfile(
    name = name,
    birthDate = birthDate,
    gender = gender,
    weightKg = weightKg,
    heightCm = heightCm,
    activityLevel = activityLevel,
    goal = goal
)

fun UserProfile.toEntity() = UserProfileEntity(
    id = 1,
    name = name,
    birthDate = birthDate,
    gender = gender,
    weightKg = weightKg,
    heightCm = heightCm,
    activityLevel = activityLevel,
    goal = goal
)

fun ActivityTypeEntity.toDomain() = ActivityType(
    id = id,
    name = name,
    nameEn = nameEn,
    met = met
)

fun ActivityType.toEntity() = ActivityTypeEntity(
    id = id,
    name = name,
    nameEn = nameEn,
    met = met
)

fun ActivityEntryEntity.toDomain() = ActivityEntry(
    id = id,
    date = date,
    activityTypeId = activityTypeId,
    activityName = activityName,
    activityNameEn = activityNameEn,
    durationMinutes = durationMinutes,
    caloriesBurned = caloriesBurned
)

fun ActivityEntry.toEntity() = ActivityEntryEntity(
    id = id,
    date = date,
    activityTypeId = activityTypeId,
    activityName = activityName,
    activityNameEn = activityNameEn,
    durationMinutes = durationMinutes,
    caloriesBurned = caloriesBurned
)

fun MealEntryEntity.toDomain() = MealEntry(
    id = id,
    date = date,
    mealType = mealType,
    foodId = foodId,
    foodName = foodName,
    foodNameEn = foodNameEn,
    quantity = quantity,
    calories = calories
)

fun MealEntry.toEntity() = MealEntryEntity(
    id = id,
    date = date,
    mealType = mealType,
    foodId = foodId,
    foodName = foodName,
    foodNameEn = foodNameEn,
    quantity = quantity,
    calories = calories
)



