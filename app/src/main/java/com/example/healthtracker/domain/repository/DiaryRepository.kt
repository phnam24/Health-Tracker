package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.MealEntry
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DiaryRepository {
    fun observeEntries(date: LocalDate): Flow<List<MealEntry>>
    fun observeEntriesByRange(from: LocalDate, to: LocalDate): Flow<List<MealEntry>>
    fun observeTotalCalories(date: LocalDate): Flow<Int>
    suspend fun addEntry(entry: MealEntry): Long
    suspend fun deleteEntry(entry: MealEntry)
}