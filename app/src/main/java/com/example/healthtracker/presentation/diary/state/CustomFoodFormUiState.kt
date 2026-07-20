package com.example.healthtracker.presentation.diary.state

import com.example.healthtracker.domain.model.CustomFoodError
import com.example.healthtracker.domain.model.CustomFoodField

data class CustomFoodFormUiState(
    val nameInput: String = "",
    val caloriesInput: String = "",
    val unitInput: String = "",
    val quantityInput: String = "1",
    val caloriesPreview: Int? = null,
    val errors: Map<CustomFoodField, CustomFoodError> = emptyMap()
)
