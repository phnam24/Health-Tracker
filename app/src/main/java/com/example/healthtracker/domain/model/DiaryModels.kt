package com.example.healthtracker.domain.model

import java.time.LocalDate

data class MealSectionData(
    val type: MealType,
    val entries: List<MealEntry>,
    val totalCalories: Int
)

data class DiaryDay(
    val date: LocalDate,
    val goalCalories: Int,
    val sections: List<MealSectionData>,
    val totalCalories: Int
)

enum class AddMealError {
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
    val name: String,
    val caloriesInput: String,
    val unit: String,
    val quantityInput: String
)
