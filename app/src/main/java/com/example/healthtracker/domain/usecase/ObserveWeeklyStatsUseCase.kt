package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.WeeklyStats
import com.example.healthtracker.domain.repository.ActivityRepository
import com.example.healthtracker.domain.repository.DiaryRepository
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

class ObserveWeeklyStatsUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository,
    private val calculateTdee: CalculateTdeeUseCase
) {
    operator fun invoke(endDate: LocalDate): Flow<WeeklyStats?> {
        val startDate = endDate.minusDays(6)
        return combine(
            userRepository.observeProfile(),
            diaryRepository.observeEntriesByRange(startDate, endDate),
            activityRepository.observeEntriesByRange(startDate, endDate)
        ) { profile, meals, activities ->
            profile ?: return@combine null

            val eatenByDate = meals.groupBy { it.date }
                .mapValues { (_, entries) -> entries.sumOf { it.calories } }
            val burnedByDate = activities.groupBy { it.date }
                .mapValues { (_, entries) -> entries.sumOf { it.caloriesBurned } }

            val points = (0L..6L).map { offset ->
                val date = startDate.plusDays(offset)
                val eaten = eatenByDate[date] ?: 0
                val burned = burnedByDate[date] ?: 0
                val goal = calculateTdee(profile, date).target
                val remaining = goal - eaten + burned
                val hasData = eaten > 0 || burned > 0

                WeeklyStats.DayPoint(
                    date = date,
                    eatenCalories = eaten,
                    burnedCalories = burned,
                    goalCalories = goal,
                    remainingCalories = remaining,
                    hasData = hasData,
                    isGoalHit = hasData && kotlin.math.abs(remaining) <= 100
                )
            }

            WeeklyStats(
                startDate = startDate,
                endDate = endDate,
                points = points,
                averageEatenCalories = points
                    .map { it.eatenCalories }.average().roundToInt(),
                averageBurnedCalories = points
                    .map { it.burnedCalories }.average().roundToInt(),
                goalDaysHit = points.count { it.isGoalHit }
            )
        }
    }
}