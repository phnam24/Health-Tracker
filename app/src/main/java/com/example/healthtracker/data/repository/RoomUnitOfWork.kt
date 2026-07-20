package com.example.healthtracker.data.repository

import androidx.room.withTransaction
import com.example.healthtracker.data.local.HealthDatabase
import com.example.healthtracker.domain.repository.UnitOfWork
import javax.inject.Inject

class RoomUnitOfWork @Inject constructor(
    private val database: HealthDatabase,
) : UnitOfWork {
    override suspend fun <T> inTransaction(block: suspend () -> T): T =
        database.withTransaction { block() }
}
