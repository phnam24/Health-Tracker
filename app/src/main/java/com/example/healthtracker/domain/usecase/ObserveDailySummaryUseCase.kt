package com.example.healthtracker.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthtracker.domain.model.DailySummary
import com.example.healthtracker.domain.repository.ActivityRepository
import com.example.healthtracker.domain.repository.DiaryRepository
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class ObserveDailySummaryUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val diaryRepository: DiaryRepository,
    private val activityRepository: ActivityRepository,
    private val calculateTdee: CalculateTdeeUseCase
) {
    operator fun invoke(date: LocalDate): Flow<DailySummary?> =
        combine(
            userRepository.observeProfile(),
            diaryRepository.observeTotalCalories(date),
            activityRepository.observeTotalBurned(date)
        ) { profile, eaten, burned ->
            profile?.let {
                val goal = calculateTdee(it, date).target
                DailySummary(
                    date = date,
                    goalCalories = goal,
                    eatenCalories = eaten,
                    burnedCalories = burned,
                    remainingCalories = goal - eaten + burned,
                    balanceCalories = eaten - burned
                )
            }
        }
}