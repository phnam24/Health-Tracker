package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchActivityTypesUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    operator fun invoke(query: String): Flow<List<ActivityType>> =
        activityRepository.observeTypes().map { types ->
            val keyword = query.trim().lowercase()
            if (keyword.isBlank()) types
            else types.filter { type ->
                type.name.lowercase().contains(keyword) ||
                        type.nameEn.lowercase().contains(keyword)
            }
        }
}