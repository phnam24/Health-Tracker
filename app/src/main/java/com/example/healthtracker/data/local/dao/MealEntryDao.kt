package com.example.healthtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.healthtracker.data.local.entity.MealEntryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MealEntryDao {
    @Query("SELECT * FROM meal_entry WHERE date = :date ORDER BY id DESC")
    fun observeByDate(date: LocalDate): Flow<List<MealEntryEntity>>

    @Query("SELECT * FROM meal_entry WHERE date BETWEEN :from AND :to ORDER BY date, id")
    fun observeRange(from: LocalDate, to: LocalDate): Flow<List<MealEntryEntity>>

    @Query("SELECT COALESCE(SUM(calories), 0) FROM meal_entry WHERE date = :date")
    fun observeTotalByDate(date: LocalDate): Flow<Int>

    @Insert
    suspend fun insert(entry: MealEntryEntity): Long

    @Delete
    suspend fun delete(entry: MealEntryEntity)
}