package com.example.healthtracker.presentation.diary.state

import com.example.healthtracker.domain.model.DiaryDay
import java.time.LocalDate

data class DiaryUiState(
    val selectedDate: LocalDate? = null,
    val day: DiaryDay? = null,
    val isLoading: Boolean = true,
    val isMutating: Boolean = false,
    val addFoodSheet: AddFoodSheetUiState? = null,
    val loadFailed: Boolean = false
)