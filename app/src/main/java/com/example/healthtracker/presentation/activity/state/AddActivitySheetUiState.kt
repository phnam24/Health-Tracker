package com.example.healthtracker.presentation.activity.state

import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.model.AddActivityError

data class AddActivitySheetUiState(
    val query: String = "",
    val types: List<ActivityType> = emptyList(),
    val selectedType: ActivityType? = null,
    val durationText: String = "",
    val estimatedCalories: Int? = null,
    val durationError: AddActivityError? = null,
    val isSearching: Boolean = false,
    val searchFailed: Boolean = false,
    val isSubmitting: Boolean = false
)