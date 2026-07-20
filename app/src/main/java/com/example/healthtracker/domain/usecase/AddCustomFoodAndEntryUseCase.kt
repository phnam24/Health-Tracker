package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.AddCustomFoodInput
import com.example.healthtracker.domain.model.AddCustomFoodResult
import com.example.healthtracker.domain.model.CustomFoodError
import com.example.healthtracker.domain.model.CustomFoodField
import com.example.healthtracker.domain.model.Food
import com.example.healthtracker.domain.model.MealEntry
import com.example.healthtracker.domain.model.MealQuantityRules
import com.example.healthtracker.domain.repository.DiaryRepository
import com.example.healthtracker.domain.repository.FoodRepository
import com.example.healthtracker.domain.repository.UnitOfWork
import kotlinx.coroutines.CancellationException
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class AddCustomFoodAndEntryUseCase @Inject constructor(
    private val foodRepository: FoodRepository,
    private val diaryRepository: DiaryRepository,
    private val unitOfWork: UnitOfWork,
    private val clock: Clock,
) {
    suspend operator fun invoke(input: AddCustomFoodInput): AddCustomFoodResult {
        if (input.date != LocalDate.now(clock)) {
            return AddCustomFoodResult.DateNotAllowed
        }
        val name = input.name.trim()
        val unit = input.unit.trim()
        val caloriesText = input.caloriesInput.trim()
        val parsedCalories = caloriesText.toIntOrNull()
        val quantityText = input.quantityInput.trim()
        val parsedQuantity = MealQuantityRules.parse(quantityText)
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
            when {
                quantityText.isEmpty() ->
                    put(CustomFoodField.QUANTITY, CustomFoodError.REQUIRED)

                parsedQuantity == null || !parsedQuantity.isFinite() ->
                    put(CustomFoodField.QUANTITY, CustomFoodError.INVALID_NUMBER)

                !MealQuantityRules.isAllowed(parsedQuantity) ->
                    put(CustomFoodField.QUANTITY, CustomFoodError.OUT_OF_RANGE)
            }
        }
        if (errors.isNotEmpty()) return AddCustomFoodResult.Invalid(errors)

        val calories = requireNotNull(parsedCalories)
        val quantity = requireNotNull(parsedQuantity)
        return try {
            val entryId = unitOfWork.inTransaction {
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
                diaryRepository.addEntry(
                    MealEntry(
                        id = 0,
                        date = input.date,
                        mealType = input.mealType,
                        foodId = foodId,
                        foodName = name,
                        quantity = quantity,
                        calories = MealQuantityRules.calculateCalories(
                            quantity = quantity,
                            caloriesPerUnit = calories,
                        )
                    )
                )
            }
            AddCustomFoodResult.Success(entryId)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (_: Exception) {
            AddCustomFoodResult.Failure
        }
    }

    fun previewCalories(
        caloriesInput: String,
        quantityInput: String,
    ): Int? {
        val calories = caloriesInput.trim().toIntOrNull()
            ?.takeIf { it in MIN_CALORIES..MAX_CALORIES }
            ?: return null
        val quantity = MealQuantityRules.parse(quantityInput)
            ?.takeIf(MealQuantityRules::isAllowed)
            ?: return null
        return MealQuantityRules.calculateCalories(quantity, calories)
    }

    private companion object {
        const val MIN_CALORIES = 1
        const val MAX_CALORIES = 10_000
    }
}
