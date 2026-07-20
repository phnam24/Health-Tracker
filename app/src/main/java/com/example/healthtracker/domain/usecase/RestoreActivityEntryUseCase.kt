package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.ActivityEntry
import com.example.healthtracker.domain.repository.ActivityRepository
import javax.inject.Inject

class RestoreActivityEntryUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(entry: ActivityEntry): Long =
        activityRepository.addEntry(entry)
}