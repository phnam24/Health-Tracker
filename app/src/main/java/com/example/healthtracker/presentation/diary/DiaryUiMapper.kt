package com.example.healthtracker.presentation.diary

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.AddMealError
import com.example.healthtracker.domain.model.CustomFoodError
import com.example.healthtracker.domain.model.CustomFoodField
import com.example.healthtracker.domain.model.MealType

fun MealType.titleRes(): Int = when (this) {
    MealType.BREAKFAST -> R.string.meal_breakfast
    MealType.LUNCH -> R.string.meal_lunch
    MealType.DINNER -> R.string.meal_dinner
    MealType.SNACK -> R.string.meal_snack
}

@Composable
fun AddMealError.message(): String = stringResource(
    when (this) {
        AddMealError.DATE_NOT_TODAY -> R.string.diary_add_today_only
        AddMealError.FOOD_REQUIRED -> R.string.error_food_required
        AddMealError.QUANTITY_REQUIRED -> R.string.error_quantity_required
        AddMealError.QUANTITY_INVALID -> R.string.error_quantity_invalid
        AddMealError.QUANTITY_OUT_OF_RANGE -> R.string.error_quantity_out_of_range
    }
)

@Composable
fun CustomFoodError.message(field: CustomFoodField): String = stringResource(
    when (this) {
        CustomFoodError.REQUIRED -> when (field) {
            CustomFoodField.NAME -> R.string.error_custom_name_required
            CustomFoodField.CALORIES -> R.string.error_custom_calories_required
            CustomFoodField.UNIT -> R.string.error_custom_unit_required
            CustomFoodField.QUANTITY -> R.string.error_quantity_required
        }

        CustomFoodError.INVALID_NUMBER -> if (field == CustomFoodField.QUANTITY) {
            R.string.error_quantity_invalid
        } else {
            R.string.error_invalid_number
        }

        CustomFoodError.OUT_OF_RANGE -> if (field == CustomFoodField.QUANTITY) {
            R.string.error_quantity_out_of_range
        } else {
            R.string.error_custom_calories_out_of_range
        }
    }
)
