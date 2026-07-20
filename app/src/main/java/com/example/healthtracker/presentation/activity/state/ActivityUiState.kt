package com.example.healthtracker.presentation.activity.state

import com.example.healthtracker.domain.model.ActivityDay
import java.time.LocalDate

data class ActivityUiState(
    val today: LocalDate? = null,
    val selectedDate: LocalDate? = null,
    val day: ActivityDay? = null,
    val isLoading: Boolean = true,
    val loadFailed: Boolean = false,
    val addSheet: AddActivitySheetUiState? = null
) {
    val isToday: Boolean
        get() = today != null && selectedDate == today
}