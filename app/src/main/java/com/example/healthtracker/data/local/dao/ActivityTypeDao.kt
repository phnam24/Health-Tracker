package com.example.healthtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthtracker.data.local.entity.ActivityTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityTypeDao {
    @Query("SELECT * FROM activity_type ORDER BY name")
    fun observeAll(): Flow<List<ActivityTypeEntity>>

    @Insert
    suspend fun insertAll(items: List<ActivityTypeEntity>)

    @Query("SELECT COUNT(*) FROM activity_type")
    suspend fun count(): Int
}