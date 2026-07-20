package com.example.healthtracker.domain.repository

interface UnitOfWork {
    suspend fun <T> inTransaction(block: suspend () -> T): T
}
