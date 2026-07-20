package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.DiaryDay
import com.example.healthtracker.domain.model.MealSectionData
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.domain.repository.DiaryRepository
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class ObserveDiaryDayUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val userRepository: UserRepository,
    private val calculateTdee: CalculateTdeeUseCase
) {
    operator fun invoke(date: LocalDate): Flow<DiaryDay> =
        combine(
            diaryRepository.observeEntries(date),
            userRepository.observeProfile()
        ) { entries, profile ->
            val goal = profile?.let { calculateTdee(it, date).target } ?: 0
            val sections = MealType.entries.map { mealType ->
                val mealEntries = entries.filter { it.mealType == mealType }
                MealSectionData(
                    type = mealType,
                    entries = mealEntries,
                    totalCalories = mealEntries.sumOf { it.calories }
                )
            }
            DiaryDay(
                date = date,
                goalCalories = goal,
                sections = sections,
                totalCalories = entries.sumOf { it.calories }
            )
        }
}
