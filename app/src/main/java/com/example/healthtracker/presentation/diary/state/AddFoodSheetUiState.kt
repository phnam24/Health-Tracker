package com.example.healthtracker.presentation.diary.state

import com.example.healthtracker.domain.model.AddMealError
import com.example.healthtracker.domain.model.Food
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.domain.model.MealQuantityRules

data class AddFoodSheetUiState(
    val targetMeal: MealType,
    val query: String = "",
    val results: List<Food> = emptyList(),
    val selectedFood: Food? = null,
    val quantityInput: String = MealQuantityRules.DEFAULT_INPUT,
    val caloriesPreview: Int? = null,
    val quantityError: AddMealError? = null,
    val customForm: CustomFoodFormUiState? = null,
    val isSearching: Boolean = false,
    val searchFailed: Boolean = false,
    val isSubmitting: Boolean = false
)
