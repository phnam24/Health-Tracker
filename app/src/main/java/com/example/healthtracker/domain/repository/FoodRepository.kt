package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun observeFoods(): Flow<List<Food>>
    fun searchFoods(query: String): Flow<List<Food>>
    suspend fun addCustomFood(food: Food): Long
}