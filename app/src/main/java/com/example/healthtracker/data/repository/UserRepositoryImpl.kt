package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.dao.UserProfileDao
import com.example.healthtracker.data.mapper.toDomain
import com.example.healthtracker.data.mapper.toEntity
import com.example.healthtracker.domain.model.UserProfile
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : UserRepository {
    override fun observeProfile(): Flow<UserProfile?> =
        userProfileDao.observe().map { it?.toDomain() }

    override suspend fun getProfile(): UserProfile? =
        userProfileDao.get()?.toDomain()

    override suspend fun saveProfile(profile: UserProfile) {
        userProfileDao.upsert(profile.toEntity())
    }
}