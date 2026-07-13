package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.ActivityEntry
import com.example.healthtracker.domain.model.ActivityType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ActivityRepository {
    fun observeTypes(): Flow<List<ActivityType>>
    fun observeEntries(date: LocalDate): Flow<List<ActivityEntry>>
    fun observeEntriesByRange(from: LocalDate, to: LocalDate): Flow<List<ActivityEntry>>
    fun observeTotalBurned(date: LocalDate): Flow<Int>
    suspend fun addEntry(entry: ActivityEntry): Long
    suspend fun deleteEntry(entry: ActivityEntry)
}