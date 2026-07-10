package com.example.healthtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.healthtracker.data.local.entity.ActivityEntryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ActivityEntryDao {
    @Query("SELECT * FROM activity_entry WHERE date = :date ORDER BY id DESC")
    fun observeByDate(date: LocalDate): Flow<List<ActivityEntryEntity>>

    @Query("SELECT * FROM activity_entry WHERE date BETWEEN :from AND :to ORDER BY date, id")
    fun observeRange(from: LocalDate, to: LocalDate): Flow<List<ActivityEntryEntity>>

    @Query("SELECT COALESCE(SUM(caloriesBurned), 0) FROM activity_entry WHERE date = :date")
    fun observeTotalByDate(date: LocalDate): Flow<Int>

    @Insert
    suspend fun insert(entry: ActivityEntryEntity): Long

    @Delete
    suspend fun delete(entry: ActivityEntryEntity)
}