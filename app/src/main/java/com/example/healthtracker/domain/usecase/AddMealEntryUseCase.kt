package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.AddMealInput
import com.example.healthtracker.domain.model.AddMealResult
import com.example.healthtracker.domain.model.AddMealError
import com.example.healthtracker.domain.model.MealEntry
import com.example.healthtracker.domain.model.MealPreviewResult
import com.example.healthtracker.domain.repository.DiaryRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject
import kotlin.math.roundToInt

class AddMealEntryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    fun preview(input: AddMealInput): MealPreviewResult =
        when (val prepared = prepare(input)) {
            is PreparedMeal.Invalid -> MealPreviewResult.Invalid(prepared.error)
            is PreparedMeal.Valid -> MealPreviewResult.Valid(prepared.entry.calories)
        }

    suspend operator fun invoke(input: AddMealInput): AddMealResult {
        return when (val prepared = prepare(input)) {
            is PreparedMeal.Invalid -> AddMealResult.Invalid(prepared.error)
            is PreparedMeal.Valid -> try {
                AddMealResult.Success(diaryRepository.addEntry(prepared.entry))
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                AddMealResult.Failure
            }
        }
    }

    private fun prepare(input: AddMealInput): PreparedMeal {
        val food = input.food
            ?: return PreparedMeal.Invalid(AddMealError.FOOD_REQUIRED)
        val rawQuantity = input.quantityInput.trim()
        if (rawQuantity.isEmpty()) {
            return PreparedMeal.Invalid(AddMealError.QUANTITY_REQUIRED)
        }

        val quantity = rawQuantity.replace(',', '.').toDoubleOrNull()
            ?: return PreparedMeal.Invalid(AddMealError.QUANTITY_INVALID)
        if (!quantity.isFinite()) {
            return PreparedMeal.Invalid(AddMealError.QUANTITY_INVALID)
        }
        if (quantity <= 0.0 || quantity > MAX_QUANTITY) {
            return PreparedMeal.Invalid(AddMealError.QUANTITY_OUT_OF_RANGE)
        }

        val calories = (quantity * food.caloriesPerUnit).roundToInt()
        return PreparedMeal.Valid(
            MealEntry(
                id = 0,
                date = input.date,
                mealType = input.mealType,
                foodId = food.id,
                foodName = food.name,
                quantity = quantity,
                calories = calories
            )
        )
    }

    private sealed interface PreparedMeal {
        data class Valid(val entry: MealEntry) : PreparedMeal
        data class Invalid(val error: AddMealError) : PreparedMeal
    }

    private companion object {
        const val MAX_QUANTITY = 100.0
    }
}
