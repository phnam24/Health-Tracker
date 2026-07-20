package com.example.healthtracker.domain.model

import java.time.LocalDate
import kotlin.math.roundToInt

data class MealSectionData(
    val type: MealType,
    val entries: List<MealEntry>,
    val totalCalories: Int
)

data class DiaryDay(
    val date: LocalDate,
    val sections: List<MealSectionData>,
    val totalCalories: Int,
    val foodCount: Int,
    val mealCount: Int,
)

enum class AddMealError {
    DATE_NOT_TODAY,
    FOOD_REQUIRED,
    QUANTITY_REQUIRED,
    QUANTITY_INVALID,
    QUANTITY_OUT_OF_RANGE
}

sealed interface MealPreviewResult {
    data class Valid(val calories: Int) : MealPreviewResult
    data class Invalid(val error: AddMealError) : MealPreviewResult
}

sealed interface AddMealResult {
    data class Success(val entryId: Long) : AddMealResult
    data class Invalid(val error: AddMealError) : AddMealResult
    data object Failure : AddMealResult
}

enum class CustomFoodField { NAME, CALORIES, UNIT, QUANTITY }
enum class CustomFoodError { REQUIRED, INVALID_NUMBER, OUT_OF_RANGE }

sealed interface AddCustomFoodResult {
    data class Success(val entryId: Long) : AddCustomFoodResult
    data class Invalid(
        val errors: Map<CustomFoodField, CustomFoodError>
    ) : AddCustomFoodResult

    data object DateNotAllowed : AddCustomFoodResult
    data object Failure : AddCustomFoodResult
}

data class AddMealInput(
    val date: LocalDate,
    val mealType: MealType,
    val food: Food?,
    val quantityInput: String
)

data class AddCustomFoodInput(
    val date: LocalDate,
    val mealType: MealType,
    val nameVi: String,
    val nameEn: String,
    val caloriesInput: String,
    val unit: String,
    val quantityInput: String
)

object MealQuantityRules {
    const val DEFAULT_INPUT = "1"
    const val DEFAULT_VALUE = 1.0
    const val STEPPER_STEP = 0.5
    const val STEPPER_MIN = 0.5
    const val MAX_VALUE = 100.0

    fun parse(input: String): Double? =
        input.trim().replace(',', '.').toDoubleOrNull()

    fun isAllowed(value: Double): Boolean =
        value.isFinite() && value > 0.0 && value <= MAX_VALUE

    fun calculateCalories(quantity: Double, caloriesPerUnit: Int): Int =
        (quantity * caloriesPerUnit).roundToInt()

    fun adjust(input: String, delta: Double): String {
        val current = parse(input) ?: DEFAULT_VALUE
        val adjusted = (current + delta).coerceIn(STEPPER_MIN, MAX_VALUE)
        return if (adjusted % 1.0 == 0.0) {
            adjusted.toInt().toString()
        } else {
            adjusted.toString()
        }
    }
}
