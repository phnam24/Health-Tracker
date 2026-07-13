package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.dao.MealEntryDao
import com.example.healthtracker.data.mapper.toDomain
import com.example.healthtracker.data.mapper.toEntity
import com.example.healthtracker.domain.model.MealEntry
import com.example.healthtracker.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val mealEntryDao: MealEntryDao
) : DiaryRepository {
    override fun observeEntries(date: LocalDate): Flow<List<MealEntry>> =
        mealEntryDao.observeByDate(date).map { results ->
            results.map {
                it.toDomain()
            }
        }

    override fun observeEntriesByRange(from: LocalDate, to: LocalDate): Flow<List<MealEntry>> =
        mealEntryDao.observeRange(from, to).map { results ->
            results.map {
                it.toDomain()
            }
        }

    override fun observeTotalCalories(date: LocalDate): Flow<Int> =
        mealEntryDao.observeTotalByDate(date)

    override suspend fun addEntry(entry: MealEntry): Long =
        mealEntryDao.insert(entry.toEntity())

    override suspend fun deleteEntry(entry: MealEntry) {
        mealEntryDao.delete(entry.toEntity())
    }
}