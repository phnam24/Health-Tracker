package com.example.healthtracker.presentation.activity.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthtracker.domain.model.ActivityEntry
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.usecase.AddActivityEntryUseCase
import com.example.healthtracker.domain.usecase.DeleteActivityEntryUseCase
import com.example.healthtracker.domain.usecase.ObserveActivityDayUseCase
import com.example.healthtracker.domain.usecase.PreviewActivityCaloriesUseCase
import com.example.healthtracker.domain.usecase.RestoreActivityEntryUseCase
import com.example.healthtracker.domain.usecase.SearchActivityTypesUseCase
import com.example.healthtracker.presentation.activity.state.ActivityUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

sealed interface ActivityEvent {
    data object PreviousDayClicked : ActivityEvent
    data object NextDayClicked : ActivityEvent
    data class DateSelected(val date: LocalDate) : ActivityEvent
    data object RetryClicked : ActivityEvent

    data object AddActivityClicked : ActivityEvent
    data object AddSheetDismissed : ActivityEvent
    data class SearchQueryChanged(val value: String) : ActivityEvent
    data object RetrySearchClicked : ActivityEvent
    data class ActivityTypeSelected(val value: ActivityType) : ActivityEvent
    data class DurationChanged(val value: String) : ActivityEvent
    data class QuickDurationSelected(val minutes: Int) : ActivityEvent
    data object ConfirmAddClicked : ActivityEvent

    data class DeleteEntryClicked(val entry: ActivityEntry) : ActivityEvent
    data class UndoDeleteClicked(val entry: ActivityEntry) : ActivityEvent
}

sealed interface ActivityEffect {
    data class ShowUndoDelete(val entry: ActivityEntry) : ActivityEffect
    data object ShowAddFailed : ActivityEffect
    data object ShowDeleteFailed : ActivityEffect
    data object ShowRestoreFailed : ActivityEffect
    data object ShowProfileRequired : ActivityEffect
    data object ShowAddTodayOnly : ActivityEffect
}

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val observeActivityDay: ObserveActivityDayUseCase,
    private val searchActivityTypes: SearchActivityTypesUseCase,
    private val previewActivityCalories: PreviewActivityCaloriesUseCase,
    private val addActivityEntry: AddActivityEntryUseCase,
    private val deleteActivityEntry: DeleteActivityEntryUseCase,
    private val restoreActivityEntry: RestoreActivityEntryUseCase,
    private val clock: Clock
) : ViewModel() {
    private val selectedDate = MutableStateFlow(LocalDate.now(clock))
    private val retryTrigger = MutableStateFlow(0)
    private val searchQuery = MutableStateFlow<String?>(null)
    private val searchRetryTrigger = MutableStateFlow(0)

    private val _uiState = MutableStateFlow(
        ActivityUiState(
            today = LocalDate.now(clock),
            selectedDate = selectedDate.value
        )
    )
    val uiState: StateFlow<ActivityUiState> = _uiState.asStateFlow()

    private val _effects = Channel<ActivityEffect>(Channel.BUFFERED)
    val effects: Flow<ActivityEffect> = _effects.receiveAsFlow()

    init {
        observeSelectedDay()
        observeSearch()
    }

    fun onEvent(event: ActivityEvent) {
        when (event) {
            ActivityEvent.PreviousDayClicked -> selectDate(selectedDate.value.minusDays(1))
            ActivityEvent.NextDayClicked -> selectDate(selectedDate.value.plusDays(1))
            is ActivityEvent.DateSelected -> selectDate(event.date)
            ActivityEvent.RetryClicked -> retryTrigger.update { it + 1 }
            ActivityEvent.AddActivityClicked -> openSheetIfToday()
            ActivityEvent.AddSheetDismissed -> closeSheet()
            is ActivityEvent.SearchQueryChanged -> updateQuery(event.value)
            ActivityEvent.RetrySearchClicked -> searchRetryTrigger.update { it + 1 }
            is ActivityEvent.ActivityTypeSelected -> selectType(event.value)
            is ActivityEvent.DurationChanged -> updateDuration(event.value)
            is ActivityEvent.QuickDurationSelected -> updateDuration(event.minutes.toString())
            ActivityEvent.ConfirmAddClicked -> submitActivity()
            is ActivityEvent.DeleteEntryClicked -> delete(event.entry)
            is ActivityEvent.UndoDeleteClicked -> restore(event.entry)
        }
    }

    private fun observeSelectedDay() {
        // TODO: Observe activity entries for the selected date.
    }

    private fun observeSearch() {
        // TODO: Observe and debounce activity search queries.
    }

    private fun selectDate(date: LocalDate) {
        // TODO: Validate and update the selected date.
    }

    private fun openSheetIfToday() {
        // TODO: Open the add-activity sheet only for today.
    }

    private fun closeSheet() {
        // TODO: Close and reset the add-activity sheet.
    }

    private fun updateQuery(value: String) {
        // TODO: Update the activity search query.
    }

    private fun selectType(value: ActivityType) {
        // TODO: Select an activity type and refresh the calorie preview.
    }

    private fun updateDuration(value: String) {
        // TODO: Update duration input and refresh the calorie preview.
    }

    private fun submitActivity() {
        // TODO: Validate and add the selected activity.
    }

    private fun delete(entry: ActivityEntry) {
        // TODO: Delete an activity entry and emit the undo effect.
    }

    private fun restore(entry: ActivityEntry) {
        // TODO: Restore a previously deleted activity entry.
    }
}