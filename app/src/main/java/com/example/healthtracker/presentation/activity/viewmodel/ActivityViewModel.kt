package com.example.healthtracker.presentation.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.model.ActivityEntry
import com.example.healthtracker.domain.model.ActivityPreviewResult
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.model.AddActivityError
import com.example.healthtracker.domain.model.AddActivityInput
import com.example.healthtracker.domain.model.AddActivityResult
import com.example.healthtracker.domain.usecase.AddActivityEntryUseCase
import com.example.healthtracker.domain.usecase.DeleteActivityEntryUseCase
import com.example.healthtracker.domain.usecase.ObserveActivityDayUseCase
import com.example.healthtracker.domain.usecase.PreviewActivityCaloriesUseCase
import com.example.healthtracker.domain.usecase.RestoreActivityEntryUseCase
import com.example.healthtracker.domain.usecase.SearchActivityTypesUseCase
import com.example.healthtracker.presentation.activity.state.ActivityUiState
import com.example.healthtracker.presentation.activity.state.AddActivitySheetUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

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
    data object ChangeActivityTypeClicked : ActivityEvent
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

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val observeActivityDay: ObserveActivityDayUseCase,
    private val searchActivityTypes: SearchActivityTypesUseCase,
    private val previewActivityCalories: PreviewActivityCaloriesUseCase,
    private val addActivityEntry: AddActivityEntryUseCase,
    private val deleteActivityEntry: DeleteActivityEntryUseCase,
    private val restoreActivityEntry: RestoreActivityEntryUseCase,
    private val clock: Clock,
) : ViewModel() {
    private val selectedDate = MutableStateFlow(LocalDate.now(clock))
    private val retryTrigger = MutableStateFlow(0)
    private val searchQuery = MutableStateFlow<String?>(null)
    private val searchRetryTrigger = MutableStateFlow(0)

    private val _uiState = MutableStateFlow(
        ActivityUiState(
            today = LocalDate.now(clock),
            selectedDate = selectedDate.value,
        )
    )
    val uiState: StateFlow<ActivityUiState> = _uiState.asStateFlow()

    private val _effects = Channel<ActivityEffect>(Channel.BUFFERED)
    val effects: Flow<ActivityEffect> = _effects.receiveAsFlow()

    private var previewJob: Job? = null
    private var profileMissingEffectShown = false

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
            ActivityEvent.ChangeActivityTypeClicked -> clearSelectedType()
            is ActivityEvent.DurationChanged -> updateDuration(event.value)
            is ActivityEvent.QuickDurationSelected -> updateDuration(event.minutes.toString())
            ActivityEvent.ConfirmAddClicked -> submitActivity()
            is ActivityEvent.DeleteEntryClicked -> delete(event.entry)
            is ActivityEvent.UndoDeleteClicked -> restore(event.entry)
        }
    }

    private fun observeSelectedDay() {
        viewModelScope.launch {
            combine(selectedDate, retryTrigger) { date, retry -> date to retry }
                .flatMapLatest { (date, _) ->
                    observeActivityDay(date)
                        .onStart {
                            _uiState.update {
                                it.copy(
                                    today = LocalDate.now(clock),
                                    selectedDate = date,
                                    isLoading = true,
                                    loadFailed = false,
                                )
                            }
                        }
                        .catch {
                            _uiState.update {
                                it.copy(
                                    selectedDate = date,
                                    isLoading = false,
                                    loadFailed = true,
                                )
                            }
                        }
                }
                .collect { day ->
                    _uiState.update {
                        it.copy(
                            today = LocalDate.now(clock),
                            selectedDate = day.date,
                            day = day,
                            isLoading = false,
                            loadFailed = false,
                        )
                    }
                }
        }
    }

    private fun observeSearch() {
        viewModelScope.launch {
            combine(searchQuery, searchRetryTrigger) { query, retry ->
                query?.trim() to retry
            }
                .distinctUntilChanged()
                .debounce(SEARCH_DEBOUNCE_MILLIS.milliseconds)
                .flatMapLatest { (query, _) ->
                    if (query == null) {
                        emptyFlow()
                    } else {
                        searchActivityTypes(query)
                            .map { results -> query to results }
                            .onStart { setSearchLoading(query) }
                            .catch { setSearchFailed(query) }
                    }
                }
                .collect { (query, results) ->
                    _uiState.update { state ->
                        val sheet = state.addSheet ?: return@update state
                        if (sheet.query.trim() != query) {
                            state
                        } else {
                            state.copy(
                                addSheet = sheet.copy(
                                    types = results,
                                    isSearching = false,
                                    searchFailed = false,
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun selectDate(date: LocalDate) {
        val today = LocalDate.now(clock)
        if (date.isAfter(today) || date == selectedDate.value) return

        previewJob?.cancel()
        searchQuery.value = null
        selectedDate.value = date
        profileMissingEffectShown = false
        _uiState.update {
            it.copy(
                today = today,
                selectedDate = date,
                day = null,
                isLoading = true,
                loadFailed = false,
                addSheet = null,
            )
        }
    }

    private fun openSheetIfToday() {
        val state = _uiState.value
        val today = LocalDate.now(clock)
        if (state.selectedDate != today) {
            viewModelScope.launch { _effects.send(ActivityEffect.ShowAddTodayOnly) }
            return
        }
        if (state.isLoading || state.loadFailed || state.isMutating) return
        if (state.addSheet?.isSubmitting == true) return

        profileMissingEffectShown = false
        _uiState.update {
            it.copy(
                today = today,
                addSheet = AddActivitySheetUiState(),
            )
        }
        searchQuery.value = ""
    }

    private fun closeSheet() {
        val sheet = _uiState.value.addSheet ?: return
        if (sheet.isSubmitting) return

        previewJob?.cancel()
        searchQuery.value = null
        profileMissingEffectShown = false
        _uiState.update { it.copy(addSheet = null) }
    }

    private fun updateQuery(value: String) {
        val currentSheet = _uiState.value.addSheet ?: return
        if (currentSheet.isSubmitting || currentSheet.selectedType != null) return

        _uiState.update { state ->
            val sheet = state.addSheet ?: return@update state
            state.copy(
                addSheet = sheet.copy(
                    query = value,
                    searchFailed = false,
                )
            )
        }
        searchQuery.value = value
    }

    private fun selectType(value: ActivityType) {
        val sheet = _uiState.value.addSheet ?: return
        if (sheet.isSubmitting) return

        previewJob?.cancel()
        searchQuery.value = null
        profileMissingEffectShown = false
        _uiState.update { state ->
            val currentSheet = state.addSheet ?: return@update state
            state.copy(
                addSheet = currentSheet.copy(
                    selectedType = value,
                    durationText = "",
                    estimatedCalories = null,
                    durationError = null,
                    isSearching = false,
                    searchFailed = false,
                )
            )
        }
    }

    private fun clearSelectedType() {
        val sheet = _uiState.value.addSheet ?: return
        if (sheet.isSubmitting) return

        previewJob?.cancel()
        profileMissingEffectShown = false
        _uiState.update { state ->
            val currentSheet = state.addSheet ?: return@update state
            state.copy(
                addSheet = currentSheet.copy(
                    selectedType = null,
                    durationText = "",
                    estimatedCalories = null,
                    durationError = null,
                )
            )
        }
        searchQuery.value = sheet.query
    }

    private fun updateDuration(value: String) {
        val sheet = _uiState.value.addSheet ?: return
        val type = sheet.selectedType ?: return
        if (sheet.isSubmitting) return

        previewJob?.cancel()
        _uiState.update { state ->
            val currentSheet = state.addSheet ?: return@update state
            state.copy(
                addSheet = currentSheet.copy(
                    durationText = value,
                    estimatedCalories = null,
                    durationError = null,
                )
            )
        }
        previewJob = launchPreview(type = type, durationText = value)
    }

    private fun launchPreview(type: ActivityType, durationText: String): Job =
        viewModelScope.launch {
            val result = try {
                previewActivityCalories(type, durationText)
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                _uiState.updatePreviewIfCurrent(type, durationText) { sheet ->
                    sheet.copy(estimatedCalories = null)
                }
                return@launch
            }

            when (result) {
                is ActivityPreviewResult.Success -> {
                    profileMissingEffectShown = false
                    _uiState.updatePreviewIfCurrent(type, durationText) { sheet ->
                        sheet.copy(
                            estimatedCalories = result.preview.caloriesBurned,
                            durationError = null,
                        )
                    }
                }

                is ActivityPreviewResult.Invalid -> {
                    _uiState.updatePreviewIfCurrent(type, durationText) { sheet ->
                        sheet.copy(
                            estimatedCalories = null,
                            durationError = result.error.takeIf { it.isDurationError() },
                        )
                    }
                    if (
                        result.error == AddActivityError.PROFILE_REQUIRED &&
                        !profileMissingEffectShown
                    ) {
                        profileMissingEffectShown = true
                        _effects.send(ActivityEffect.ShowProfileRequired)
                    }
                }
            }
        }

    private fun submitActivity() {
        val state = _uiState.value
        val sheet = state.addSheet ?: return
        val date = state.selectedDate ?: return
        if (sheet.isSubmitting) return

        if (date != LocalDate.now(clock)) {
            viewModelScope.launch { _effects.send(ActivityEffect.ShowAddTodayOnly) }
            return
        }

        val input = AddActivityInput(
            date = date,
            activityType = sheet.selectedType,
            durationText = sheet.durationText,
        )
        setSubmitting(true)

        viewModelScope.launch {
            try {
                when (val result = addActivityEntry(input)) {
                    is AddActivityResult.Success -> closeSheetAfterSuccess()
                    is AddActivityResult.Invalid -> handleSubmitError(result.error)
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                setSubmitting(false)
                _effects.send(ActivityEffect.ShowAddFailed)
            }
        }
    }

    private suspend fun handleSubmitError(error: AddActivityError) {
        setSubmitting(false)
        when {
            error.isDurationError() -> {
                _uiState.update { state ->
                    val sheet = state.addSheet ?: return@update state
                    state.copy(
                        addSheet = sheet.copy(
                            estimatedCalories = null,
                            durationError = error,
                        )
                    )
                }
            }

            error == AddActivityError.ACTIVITY_REQUIRED -> {
                clearSelectedType()
            }

            error == AddActivityError.PROFILE_REQUIRED -> {
                _effects.send(ActivityEffect.ShowProfileRequired)
            }

            error == AddActivityError.DATE_NOT_TODAY -> {
                _effects.send(ActivityEffect.ShowAddTodayOnly)
            }
        }
    }

    private fun delete(entry: ActivityEntry) {
        val state = _uiState.value
        val today = LocalDate.now(clock)
        if (
            state.isMutating ||
            state.isLoading ||
            state.selectedDate != today ||
            entry.date != today
        ) return

        _uiState.update { it.copy(isMutating = true) }
        viewModelScope.launch {
            try {
                deleteActivityEntry(entry)
                _effects.send(ActivityEffect.ShowUndoDelete(entry))
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                _effects.send(ActivityEffect.ShowDeleteFailed)
            } finally {
                _uiState.update { it.copy(isMutating = false) }
            }
        }
    }

    private fun restore(entry: ActivityEntry) {
        if (_uiState.value.isMutating) return

        _uiState.update { it.copy(isMutating = true) }
        viewModelScope.launch {
            try {
                restoreActivityEntry(entry)
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                _effects.send(ActivityEffect.ShowRestoreFailed)
            } finally {
                _uiState.update { it.copy(isMutating = false) }
            }
        }
    }

    private fun setSearchLoading(query: String) {
        _uiState.update { state ->
            val sheet = state.addSheet ?: return@update state
            if (sheet.query.trim() != query) return@update state
            state.copy(
                addSheet = sheet.copy(
                    isSearching = true,
                    searchFailed = false,
                )
            )
        }
    }

    private fun setSearchFailed(query: String) {
        _uiState.update { state ->
            val sheet = state.addSheet ?: return@update state
            if (sheet.query.trim() != query) return@update state
            state.copy(
                addSheet = sheet.copy(
                    isSearching = false,
                    searchFailed = true,
                )
            )
        }
    }

    private fun setSubmitting(value: Boolean) {
        _uiState.update { state ->
            val sheet = state.addSheet ?: return@update state
            state.copy(addSheet = sheet.copy(isSubmitting = value))
        }
    }

    private fun closeSheetAfterSuccess() {
        previewJob?.cancel()
        searchQuery.value = null
        profileMissingEffectShown = false
        _uiState.update { it.copy(addSheet = null) }
    }

    private fun AddActivityError.isDurationError(): Boolean =
        this == AddActivityError.DURATION_REQUIRED ||
            this == AddActivityError.DURATION_INVALID ||
            this == AddActivityError.DURATION_OUT_OF_RANGE

    private fun MutableStateFlow<ActivityUiState>.updatePreviewIfCurrent(
        type: ActivityType,
        durationText: String,
        transform: (AddActivitySheetUiState) -> AddActivitySheetUiState,
    ) {
        update { state ->
            val sheet = state.addSheet ?: return@update state
            if (
                sheet.selectedType?.id != type.id ||
                sheet.durationText != durationText ||
                sheet.isSubmitting
            ) {
                state
            } else {
                state.copy(addSheet = transform(sheet))
            }
        }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 300L
    }
}