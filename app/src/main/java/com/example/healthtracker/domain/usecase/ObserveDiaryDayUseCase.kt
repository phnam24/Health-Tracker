package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.DiaryDay
import com.example.healthtracker.domain.model.MealSectionData
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class ObserveDiaryDayUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
) {
    operator fun invoke(date: LocalDate): Flow<DiaryDay> =
        diaryRepository.observeEntries(date).map { entries ->
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
                sections = sections,
                totalCalories = entries.sumOf { it.calories },
                foodCount = entries.size,
                mealCount = sections.count { it.entries.isNotEmpty() },
            )
        }
}
