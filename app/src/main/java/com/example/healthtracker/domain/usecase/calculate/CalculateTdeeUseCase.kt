package com.example.healthtracker.domain.usecase.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.TdeeBreakdown
import com.example.healthtracker.domain.model.UserProfile
import java.time.LocalDate
import kotlin.math.roundToInt

class CalculateTdeeUseCase(
    private val calculateAge: CalculateAgeUseCase,
    private val calculateBmr: CalculateBmrUseCase
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(
        profile: UserProfile,
        today: LocalDate = LocalDate.now()
    ): TdeeBreakdown {
        val age = calculateAge(profile.birthDate, today)
        val rawBmr = calculateBmr(
            profile.gender,
            profile.weightKg,
            profile.heightCm,
            age
        )
        val tdee = (rawBmr * profile.activityLevel.factor).roundToInt()
        val adjustment = when (profile.goal) {
            Goal.LOSE -> -500
            Goal.MAINTAIN -> 0
            Goal.GAIN -> 500
        }

        return TdeeBreakdown(
            bmr = rawBmr.roundToInt(),
            tdee = tdee,
            goalAdjustment = adjustment,
            target = tdee + adjustment
        )
    }
}