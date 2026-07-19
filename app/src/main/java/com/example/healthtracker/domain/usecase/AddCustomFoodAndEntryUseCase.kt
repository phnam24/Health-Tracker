package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.AddCustomFoodInput
import com.example.healthtracker.domain.model.AddCustomFoodResult
import com.example.healthtracker.domain.model.CustomFoodError
import com.example.healthtracker.domain.model.CustomFoodField
import com.example.healthtracker.domain.model.Food
import com.example.healthtracker.domain.model.MealEntry
import com.example.healthtracker.domain.repository.DiaryRepository
import com.example.healthtracker.domain.repository.FoodRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class AddCustomFoodAndEntryUseCase @Inject constructor(
    private val foodRepository: FoodRepository,
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(input: AddCustomFoodInput): AddCustomFoodResult {
        val name = input.name.trim()
        val unit = input.unit.trim()
        val caloriesText = input.caloriesInput.trim()
        val parsedCalories = caloriesText.toIntOrNull()
        val errors = buildMap<CustomFoodField, CustomFoodError> {
            if (name.isEmpty()) put(CustomFoodField.NAME, CustomFoodError.REQUIRED)
            if (unit.isEmpty()) put(CustomFoodField.UNIT, CustomFoodError.REQUIRED)
            when {
                caloriesText.isEmpty() ->
                    put(CustomFoodField.CALORIES, CustomFoodError.REQUIRED)

                parsedCalories == null ->
                    put(CustomFoodField.CALORIES, CustomFoodError.INVALID_NUMBER)

                parsedCalories !in MIN_CALORIES..MAX_CALORIES ->
                    put(CustomFoodField.CALORIES, CustomFoodError.OUT_OF_RANGE)
            }
        }
        if (errors.isNotEmpty()) return AddCustomFoodResult.Invalid(errors)

        val calories = requireNotNull(parsedCalories)
        return try {
            val foodId = foodRepository.addCustomFood(
                Food(
                    id = 0,
                    name = name,
                    nameEn = name,
                    caloriesPerUnit = calories,
                    unit = unit,
                    isCustom = true
                )
            )
            val entryId = diaryRepository.addEntry(
                MealEntry(
                    id = 0,
                    date = input.date,
                    mealType = input.mealType,
                    foodId = foodId,
                    foodName = name,
                    quantity = 1.0,
                    calories = calories
                )
            )
            AddCustomFoodResult.Success(entryId)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (_: Exception) {
            AddCustomFoodResult.Failure
        }
    }

    private companion object {
        const val MIN_CALORIES = 1
        const val MAX_CALORIES = 10_000
    }
}
