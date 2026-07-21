package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.DailyCaloriePoint
import com.example.healthtracker.domain.model.RecentIntakeStats
import com.example.healthtracker.domain.model.StatisticsSnapshot
import com.example.healthtracker.domain.model.WeeklyStats
import com.example.healthtracker.domain.repository.ActivityRepository
import com.example.healthtracker.domain.repository.DiaryRepository
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject
import kotlin.math.roundToInt

class ObserveWeeklyStatsUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository,
    private val calculateTdee: CalculateTdeeUseCase
) {
    operator fun invoke(
        selectedWeekDate: LocalDate,
        today: LocalDate
    ): Flow<StatisticsSnapshot?> {
        val recentStartDate = today.minusDays(6)
        val currentWeekStart = today.with(
            TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
        )
        val selectedWeekStart = selectedWeekDate
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .coerceAtMost(currentWeekStart)
        val selectedWeekEnd = selectedWeekStart.plusDays(6)
        val observableWeekEnd = minOf(selectedWeekEnd, today)

        return combine(
            userRepository.observeProfile(),
            diaryRepository.observeEntriesByRange(recentStartDate, today),
            activityRepository.observeEntriesByRange(recentStartDate, today),
            diaryRepository.observeEntriesByRange(selectedWeekStart, observableWeekEnd),
            activityRepository.observeEntriesByRange(selectedWeekStart, observableWeekEnd)
        ) { profile, recentMeals, recentActivities, weekMeals, weekActivities ->
            profile ?: return@combine null

            val recentEatenByDate = recentMeals.groupBy { it.date }
                .mapValues { (_, entries) -> entries.sumOf { it.calories } }
            val recentBurnedByDate = recentActivities.groupBy { it.date }
                .mapValues { (_, entries) -> entries.sumOf { it.caloriesBurned } }
            val weekEatenByDate = weekMeals.groupBy { it.date }
                .mapValues { (_, entries) -> entries.sumOf { it.calories } }
            val weekBurnedByDate = weekActivities.groupBy { it.date }
                .mapValues { (_, entries) -> entries.sumOf { it.caloriesBurned } }

            val recentPoints = (0L..6L).map { offset ->
                val date = recentStartDate.plusDays(offset)
                val eaten = recentEatenByDate[date] ?: 0
                val burned = recentBurnedByDate[date] ?: 0
                val goal = calculateTdee(profile, date).target
                val remaining = goal - eaten + burned
                val hasData = eaten > 0 || burned > 0

                DailyCaloriePoint(
                    date = date,
                    eatenCalories = eaten,
                    burnedCalories = burned,
                    goalCalories = goal,
                    remainingCalories = remaining,
                    hasData = hasData,
                    isGoalHit = hasData && kotlin.math.abs(remaining) <= 100
                )
            }

            val weekPoints = (0L..6L).map { offset ->
                val date = selectedWeekStart.plusDays(offset)
                val isFuture = date.isAfter(today)
                val eaten = if (isFuture) 0 else weekEatenByDate[date] ?: 0
                val burned = if (isFuture) 0 else weekBurnedByDate[date] ?: 0
                val goal = if (isFuture) 0 else calculateTdee(profile, date).target
                val remaining = if (isFuture) 0 else goal - eaten + burned
                val hasData = !isFuture && (eaten > 0 || burned > 0)

                DailyCaloriePoint(
                    date = date,
                    eatenCalories = eaten,
                    burnedCalories = burned,
                    goalCalories = goal,
                    remainingCalories = remaining,
                    hasData = hasData,
                    isGoalHit = hasData && kotlin.math.abs(remaining) <= 100,
                    isFuture = isFuture
                )
            }
            val elapsedWeekPoints = weekPoints.filterNot(DailyCaloriePoint::isFuture)

            StatisticsSnapshot(
                recentIntake = RecentIntakeStats(
                    startDate = recentStartDate,
                    endDate = today,
                    points = recentPoints
                ),
                selectedWeek = WeeklyStats(
                    startDate = selectedWeekStart,
                    endDate = selectedWeekEnd,
                    points = weekPoints,
                    averageEatenCalories = elapsedWeekPoints
                    .map { it.eatenCalories }.average().roundToInt(),
                    averageBurnedCalories = elapsedWeekPoints
                    .map { it.burnedCalories }.average().roundToInt(),
                    goalDaysHit = elapsedWeekPoints.count { it.isGoalHit },
                    observedDayCount = elapsedWeekPoints.size
                )
            )
        }
    }
}
