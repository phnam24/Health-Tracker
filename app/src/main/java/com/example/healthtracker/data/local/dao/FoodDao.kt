package com.example.healthtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthtracker.data.local.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food ORDER BY name")
    fun observeAll(): Flow<List<FoodEntity>>

    @Query(
        """
        SELECT * FROM food
        WHERE name LIKE '%' || :query || '%'
           OR nameEn LIKE '%' || :query || '%'
        ORDER BY name
        LIMIT 50
    """
    )
    fun search(query: String): Flow<List<FoodEntity>>

    @Insert
    suspend fun insert(food: FoodEntity): Long

    @Insert
    suspend fun insertAll(items: List<FoodEntity>)

    @Query("SELECT COUNT(*) FROM food")
    suspend fun count(): Int
}