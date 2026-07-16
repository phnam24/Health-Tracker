package com.example.healthtracker.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthtracker.domain.model.DashboardData
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class ObserveDashboardUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val observeDailySummary: ObserveDailySummaryUseCase,
    private val buildDailyAdvice: BuildDailyAdviceUseCase
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(date: LocalDate): Flow<DashboardData?> =
        combine(
            userRepository.observeProfile(),
            observeDailySummary(date)
        ) { profile, summary ->
            if (profile == null || summary == null) return@combine null

            DashboardData(
                userName = profile.name,
                summary = summary,
                advice = buildDailyAdvice(summary.remainingCalories)
            )
        }
}