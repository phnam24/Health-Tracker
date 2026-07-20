package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.ActivityDay
import com.example.healthtracker.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class ObserveActivityDayUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    operator fun invoke(date: LocalDate): Flow<ActivityDay> =
        combine(
            activityRepository.observeEntries(date),
            activityRepository.observeTotalBurned(date),
            activityRepository.observeTypes()
        ) { entries, total, types ->
            ActivityDay(
                date = date,
                entries = entries,
                totalBurnedCalories = total,
                totalDurationMinutes = entries.sumOf { it.durationMinutes },
                activityCount = entries.size,
                metByActivityTypeId = types.associate { it.id to it.met }
            )
        }
}
