package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.dao.ActivityEntryDao
import com.example.healthtracker.data.local.dao.ActivityTypeDao
import com.example.healthtracker.data.mapper.toDomain
import com.example.healthtracker.data.mapper.toEntity
import com.example.healthtracker.domain.model.ActivityEntry
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val activityTypeDao: ActivityTypeDao,
    private val activityEntryDao: ActivityEntryDao
) : ActivityRepository{
    override fun observeTypes(): Flow<List<ActivityType>> =
        activityTypeDao.observeAll().map { results ->
            results.map {
                it.toDomain()
            }
        }

    override fun observeEntries(date: LocalDate): Flow<List<ActivityEntry>> =
        activityEntryDao.observeByDate(date).map { results ->
            results.map {
                it.toDomain()
            }
        }

    override fun observeEntriesByRange(from: LocalDate, to: LocalDate): Flow<List<ActivityEntry>> =
        activityEntryDao.observeRange(from, to).map { results ->
            results.map {
                it.toDomain()
            }
        }

    override fun observeTotalBurned(date: LocalDate): Flow<Int> =
        activityEntryDao.observeTotalByDate(date)

    override suspend fun addEntry(entry: ActivityEntry): Long =
        activityEntryDao.insert(entry.toEntity())

    override suspend fun deleteEntry(entry: ActivityEntry) {
        activityEntryDao.delete(entry.toEntity())
    }

}