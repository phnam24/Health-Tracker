package com.example.healthtracker.presentation.diary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.model.AddCustomFoodInput
import com.example.healthtracker.domain.model.AddCustomFoodResult
import com.example.healthtracker.domain.model.AddMealError
import com.example.healthtracker.domain.model.AddMealInput
import com.example.healthtracker.domain.model.AddMealResult
import com.example.healthtracker.domain.model.CustomFoodField
import com.example.healthtracker.domain.model.Food
import com.example.healthtracker.domain.model.MealEntry
import com.example.healthtracker.domain.model.MealPreviewResult
import com.example.healthtracker.domain.model.MealQuantityRules
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.domain.usecase.AddCustomFoodAndEntryUseCase
import com.example.healthtracker.domain.usecase.AddMealEntryUseCase
import com.example.healthtracker.domain.usecase.DeleteMealEntryUseCase
import com.example.healthtracker.domain.usecase.ObserveDiaryDayUseCase
import com.example.healthtracker.domain.usecase.RestoreMealEntryUseCase
import com.example.healthtracker.domain.usecase.SearchFoodsUseCase
import com.example.healthtracker.presentation.diary.state.AddFoodSheetUiState
import com.example.healthtracker.presentation.diary.state.CustomFoodFormUiState
import com.example.healthtracker.presentation.diary.state.DiaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds

sealed interface DiaryEvent {
    data object PreviousDayClicked : DiaryEvent
    data object NextDayClicked : DiaryEvent
    data class DateSelected(val value: LocalDate) : DiaryEvent
    data object RetryClicked : DiaryEvent
    data class AddFoodClicked(val mealType: MealType) : DiaryEvent
    data object AddFoodDismissed : DiaryEvent
    data class SearchQueryChanged(val value: String) : DiaryEvent
    data object RetrySearchClicked : DiaryEvent
    data class FoodSelected(val food: Food) : DiaryEvent
    data object BackToFoodSearchClicked : DiaryEvent
    data object DecreaseQuantityClicked : DiaryEvent
    data object IncreaseQuantityClicked : DiaryEvent
    data class QuantityChanged(val value: String) : DiaryEvent
    data object ConfirmAddFoodClicked : DiaryEvent
    data object OpenCustomFoodClicked : DiaryEvent
    data object CloseCustomFoodClicked : DiaryEvent
    data class CustomFoodNameViChanged(val value: String) : DiaryEvent
    data class CustomFoodNameEnChanged(val value: String) : DiaryEvent
    data class CustomFoodCaloriesChanged(val value: String) : DiaryEvent
    data class CustomFoodUnitChanged(val value: String) : DiaryEvent
    data class CustomFoodQuantityChanged(val value: String) : DiaryEvent
    data object DecreaseCustomFoodQuantityClicked : DiaryEvent
    data object IncreaseCustomFoodQuantityClicked : DiaryEvent
    data object ConfirmCustomFoodClicked : DiaryEvent
    data class DeleteEntryClicked(val entry: MealEntry) : DiaryEvent
    data class UndoDeleteClicked(val entry: MealEntry) : DiaryEvent
}

sealed interface DiaryEffect {
    data class ShowUndoDelete(val entry: MealEntry) : DiaryEffect
    data object ShowAddFailed : DiaryEffect
    data object ShowCustomFoodSaveFailed : DiaryEffect
    data object ShowDeleteFailed : DiaryEffect
    data object ShowRestoreFailed : DiaryEffect
    data object ShowAddTodayOnly : DiaryEffect
}

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val observeDiaryDay: ObserveDiaryDayUseCase,
    private val searchFoods: SearchFoodsUseCase,
    private val addMealEntry: AddMealEntryUseCase,
    private val addCustomFoodAndEntry: AddCustomFoodAndEntryUseCase,
    private val deleteMealEntry: DeleteMealEntryUseCase,
    private val restoreMealEntry: RestoreMealEntryUseCase,
    private val clock: Clock
) : ViewModel() {

    private val selectedDate = MutableStateFlow(LocalDate.now(clock))
    private val retryTrigger = MutableStateFlow(0)
    private val searchQuery = MutableStateFlow<String?>(null)
    private val searchRetryTrigger = MutableStateFlow(0)

    private val _uiState = MutableStateFlow(
        DiaryUiState(
            today = LocalDate.now(clock),
            selectedDate = selectedDate.value
        )
    )
    val uiState: StateFlow<DiaryUiState> = _uiState.asStateFlow()

    private val _effects = Channel<DiaryEffect>(Channel.BUFFERED)
    val effects: Flow<DiaryEffect> = _effects.receiveAsFlow()

    init {
        observeSelectedDay()
        observeFoodSearch()
    }

    fun onEvent(event: DiaryEvent) {
        when (event) {
            DiaryEvent.PreviousDayClicked ->
                selectDate(selectedDate.value.minusDays(1))

            DiaryEvent.NextDayClicked ->
                selectDate(selectedDate.value.plusDays(1))

            is DiaryEvent.DateSelected -> selectDate(event.value)
            DiaryEvent.RetryClicked -> retryTrigger.update { it + 1 }
            is DiaryEvent.AddFoodClicked -> openAddFoodSheet(event.mealType)
            DiaryEvent.AddFoodDismissed -> dismissAddFoodSheet()
            is DiaryEvent.SearchQueryChanged -> updateSearchQuery(event.value)
            DiaryEvent.RetrySearchClicked -> searchRetryTrigger.update { it + 1 }
            is DiaryEvent.FoodSelected -> selectFood(event.food)
            DiaryEvent.BackToFoodSearchClicked -> clearSelectedFood()
            DiaryEvent.DecreaseQuantityClicked ->
                adjustQuantity(-MealQuantityRules.STEPPER_STEP)

            DiaryEvent.IncreaseQuantityClicked ->
                adjustQuantity(MealQuantityRules.STEPPER_STEP)

            is DiaryEvent.QuantityChanged -> updateQuantity(event.value)
            DiaryEvent.ConfirmAddFoodClicked -> submitSelectedFood()
            DiaryEvent.OpenCustomFoodClicked -> openCustomFoodForm()
            DiaryEvent.CloseCustomFoodClicked -> closeCustomFoodForm()
            is DiaryEvent.CustomFoodNameViChanged -> updateCustomNameVi(event.value)
            is DiaryEvent.CustomFoodNameEnChanged -> updateCustomNameEn(event.value)
            is DiaryEvent.CustomFoodCaloriesChanged -> updateCustomCalories(event.value)
            is DiaryEvent.CustomFoodUnitChanged -> updateCustomUnit(event.value)
            is DiaryEvent.CustomFoodQuantityChanged -> updateCustomQuantity(event.value)
            DiaryEvent.DecreaseCustomFoodQuantityClicked ->
                adjustCustomQuantity(-MealQuantityRules.STEPPER_STEP)

            DiaryEvent.IncreaseCustomFoodQuantityClicked ->
                adjustCustomQuantity(MealQuantityRules.STEPPER_STEP)

            DiaryEvent.ConfirmCustomFoodClicked -> submitCustomFood()
            is DiaryEvent.DeleteEntryClicked -> deleteEntry(event.entry)
            is DiaryEvent.UndoDeleteClicked -> restoreEntry(event.entry)
        }
    }

    private fun observeSelectedDay() {
        viewModelScope.launch {
            combine(selectedDate, retryTrigger) { date, retry -> date to retry }
                .flatMapLatest { (date, _) ->
                    observeDiaryDay(date)
                        .onStart {
                            _uiState.update {
                                it.copy(
                                    selectedDate = date,
                                    isLoading = true,
                                    loadFailed = false
                                )
                            }
                        }
                        .catch {
                            _uiState.update {
                                it.copy(
                                    selectedDate = date,
                                    isLoading = false,
                                    loadFailed = true
                                )
                            }
                        }
                }
                .collect { day ->
                    _uiState.update {
                        it.copy(
                            selectedDate = day.date,
                            day = day,
                            isLoading = false,
                            loadFailed = false
                        )
                    }
                }
        }
    }

    private fun observeFoodSearch() {
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
                        searchFoods(query)
                            .map { results -> query to results }
                            .onStart { setSearchLoading(query) }
                            .catch { setSearchFailed(query) }
                    }
                }
                .collect { (query, results) ->
                    _uiState.update { state ->
                        val sheet = state.addFoodSheet ?: return@update state
                        if (sheet.customForm != null || sheet.query.trim() != query) {
                            state
                        } else {
                            state.copy(
                                addFoodSheet = sheet.copy(
                                    results = results,
                                    isSearching = false,
                                    searchFailed = false
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

        searchQuery.value = null
        selectedDate.value = date
        _uiState.update {
            it.copy(
                today = today,
                selectedDate = date,
                day = null,
                isLoading = true,
                loadFailed = false,
                addFoodSheet = null
            )
        }
    }

    private fun openAddFoodSheet(mealType: MealType) {
        val state = _uiState.value
        val today = LocalDate.now(clock)
        if (
            state.selectedDate != today ||
            state.isMutating ||
            state.addFoodSheet?.isSubmitting == true
        ) return

        _uiState.update {
            it.copy(addFoodSheet = AddFoodSheetUiState(targetMeal = mealType))
        }
        searchQuery.value = ""
    }

    private fun dismissAddFoodSheet() {
        val sheet = _uiState.value.addFoodSheet ?: return
        if (sheet.isSubmitting) return

        searchQuery.value = null
        _uiState.update { it.copy(addFoodSheet = null) }
    }

    private fun updateSearchQuery(value: String) {
        val currentSheet = _uiState.value.addFoodSheet ?: return
        if (currentSheet.isSubmitting || currentSheet.customForm != null) return

        _uiState.update { state ->
            val sheet = state.addFoodSheet ?: return@update state

            state.copy(
                addFoodSheet = sheet.copy(
                    query = value,
                    selectedFood = null,
                    caloriesPreview = null,
                    quantityError = null,
                    searchFailed = false
                )
            )
        }
        searchQuery.value = value
    }

    private fun selectFood(food: Food) {
        _uiState.update { state ->
            val sheet = state.addFoodSheet ?: return@update state
            if (sheet.isSubmitting || sheet.customForm != null) return@update state

            state.copy(
                addFoodSheet = applyPreview(
                    state = state,
                    sheet = sheet.copy(
                        selectedFood = food,
                        quantityInput = MealQuantityRules.DEFAULT_INPUT,
                        quantityError = null
                    )
                )
            )
        }
    }

    private fun updateQuantity(value: String) {
        _uiState.update { state ->
            val sheet = state.addFoodSheet ?: return@update state
            if (sheet.isSubmitting || sheet.customForm != null) return@update state

            state.copy(
                addFoodSheet = applyPreview(
                    state = state,
                    sheet = sheet.copy(quantityInput = value)
                )
            )
        }
    }

    private fun clearSelectedFood() {
        _uiState.update { state ->
            val sheet = state.addFoodSheet ?: return@update state
            if (sheet.isSubmitting || sheet.customForm != null) return@update state

            state.copy(
                addFoodSheet = sheet.copy(
                    selectedFood = null,
                    quantityInput = MealQuantityRules.DEFAULT_INPUT,
                    caloriesPreview = null,
                    quantityError = null
                )
            )
        }
    }

    private fun adjustQuantity(delta: Double) {
        val sheet = _uiState.value.addFoodSheet ?: return
        if (sheet.isSubmitting || sheet.customForm != null) return

        updateQuantity(MealQuantityRules.adjust(sheet.quantityInput, delta))
    }

    private fun applyPreview(
        state: DiaryUiState,
        sheet: AddFoodSheetUiState
    ): AddFoodSheetUiState {
        val date = state.selectedDate
            ?: return sheet.copy(caloriesPreview = null, quantityError = null)
        if (sheet.selectedFood == null) {
            return sheet.copy(caloriesPreview = null, quantityError = null)
        }

        return when (
            val result = addMealEntry.preview(
                AddMealInput(
                    date = date,
                    mealType = sheet.targetMeal,
                    food = sheet.selectedFood,
                    quantityInput = sheet.quantityInput
                )
            )
        ) {
            is MealPreviewResult.Valid -> sheet.copy(
                caloriesPreview = result.calories,
                quantityError = null
            )

            is MealPreviewResult.Invalid -> sheet.copy(
                caloriesPreview = null,
                quantityError = result.error
            )
        }
    }

    private fun submitSelectedFood() {
        val state = _uiState.value
        val sheet = state.addFoodSheet ?: return
        val date = state.selectedDate ?: return
        if (
            date != LocalDate.now(clock) ||
            sheet.isSubmitting ||
            sheet.customForm != null
        ) return

        val input = AddMealInput(
            date = date,
            mealType = sheet.targetMeal,
            food = sheet.selectedFood,
            quantityInput = sheet.quantityInput
        )
        setSubmitting(true)

        viewModelScope.launch {
            try {
                when (val result = addMealEntry(input)) {
                    is AddMealResult.Success -> closeSheetAfterSuccess()
                    is AddMealResult.Invalid -> {
                        if (result.error == AddMealError.DATE_NOT_TODAY) {
                            setSubmitting(false)
                            _effects.send(DiaryEffect.ShowAddTodayOnly)
                            return@launch
                        }
                        _uiState.update { current ->
                            val currentSheet = current.addFoodSheet
                                ?: return@update current
                            current.copy(
                                addFoodSheet = currentSheet.copy(
                                    caloriesPreview = null,
                                    quantityError = result.error,
                                    isSubmitting = false
                                )
                            )
                        }
                    }

                    AddMealResult.Failure -> {
                        setSubmitting(false)
                        _effects.send(DiaryEffect.ShowAddFailed)
                    }
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                setSubmitting(false)
                _effects.send(DiaryEffect.ShowAddFailed)
            }
        }
    }

    private fun openCustomFoodForm() {
        val sheet = _uiState.value.addFoodSheet ?: return
        if (sheet.isSubmitting || sheet.customForm != null) return

        searchQuery.value = null
        _uiState.update { state ->
            val currentSheet = state.addFoodSheet ?: return@update state
            state.copy(
                addFoodSheet = currentSheet.copy(
                    customForm = CustomFoodFormUiState(),
                    isSearching = false,
                    searchFailed = false
                )
            )
        }
    }

    private fun closeCustomFoodForm() {
        val sheet = _uiState.value.addFoodSheet ?: return
        if (sheet.isSubmitting || sheet.customForm == null) return

        _uiState.update { state ->
            val currentSheet = state.addFoodSheet ?: return@update state
            state.copy(
                addFoodSheet = currentSheet.copy(customForm = null)
            )
        }
        searchQuery.value = sheet.query
    }

    private fun updateCustomNameVi(value: String) {
        updateCustomForm { form ->
            form.copy(
                nameViInput = value,
                errors = form.errors - CustomFoodField.NAME
            )
        }
    }

    private fun updateCustomNameEn(value: String) {
        updateCustomForm { form -> form.copy(nameEnInput = value) }
    }

    private fun updateCustomCalories(value: String) {
        updateCustomForm { form ->
            form.copy(
                caloriesInput = value,
                errors = form.errors - CustomFoodField.CALORIES
            )
        }
    }

    private fun updateCustomUnit(value: String) {
        updateCustomForm { form ->
            form.copy(
                unitInput = value,
                errors = form.errors - CustomFoodField.UNIT
            )
        }
    }

    private fun updateCustomQuantity(value: String) {
        updateCustomForm { form ->
            form.copy(
                quantityInput = value,
                errors = form.errors - CustomFoodField.QUANTITY
            )
        }
    }

    private fun adjustCustomQuantity(delta: Double) {
        val sheet = _uiState.value.addFoodSheet ?: return
        val form = sheet.customForm ?: return
        if (sheet.isSubmitting) return

        updateCustomQuantity(MealQuantityRules.adjust(form.quantityInput, delta))
    }

    private fun updateCustomForm(
        transform: (CustomFoodFormUiState) -> CustomFoodFormUiState
    ) {
        _uiState.update { state ->
            val sheet = state.addFoodSheet ?: return@update state
            val form = sheet.customForm ?: return@update state
            if (sheet.isSubmitting) return@update state

            val updatedForm = transform(form)
            state.copy(
                addFoodSheet = sheet.copy(
                    customForm = updatedForm.copy(
                        caloriesPreview = addCustomFoodAndEntry.previewCalories(
                            caloriesInput = updatedForm.caloriesInput,
                            quantityInput = updatedForm.quantityInput,
                        )
                    )
                )
            )
        }
    }

    private fun submitCustomFood() {
        val state = _uiState.value
        val sheet = state.addFoodSheet ?: return
        val form = sheet.customForm ?: return
        val date = state.selectedDate ?: return
        if (date != LocalDate.now(clock) || sheet.isSubmitting) return

        val input = AddCustomFoodInput(
            date = date,
            mealType = sheet.targetMeal,
            nameVi = form.nameViInput,
            nameEn = form.nameEnInput,
            caloriesInput = form.caloriesInput,
            unit = form.unitInput,
            quantityInput = form.quantityInput
        )
        setSubmitting(true)

        viewModelScope.launch {
            try {
                when (val result = addCustomFoodAndEntry(input)) {
                    is AddCustomFoodResult.Success -> closeSheetAfterSuccess()
                    is AddCustomFoodResult.Invalid -> {
                        _uiState.update { current ->
                            val currentSheet = current.addFoodSheet
                                ?: return@update current
                            val currentForm = currentSheet.customForm
                                ?: return@update current
                            current.copy(
                                addFoodSheet = currentSheet.copy(
                                    customForm = currentForm.copy(errors = result.errors),
                                    isSubmitting = false
                                )
                            )
                        }
                    }

                    AddCustomFoodResult.DateNotAllowed -> {
                        setSubmitting(false)
                        _effects.send(DiaryEffect.ShowAddTodayOnly)
                    }

                    AddCustomFoodResult.Failure -> {
                        setSubmitting(false)
                        _effects.send(DiaryEffect.ShowCustomFoodSaveFailed)
                    }
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                setSubmitting(false)
                _effects.send(DiaryEffect.ShowCustomFoodSaveFailed)
            }
        }
    }

    private fun deleteEntry(entry: MealEntry) {
        if (_uiState.value.isMutating) return
        _uiState.update { it.copy(isMutating = true) }

        viewModelScope.launch {
            val result = deleteMealEntry(entry)
            _uiState.update { it.copy(isMutating = false) }
            if (result.isSuccess) {
                _effects.send(DiaryEffect.ShowUndoDelete(entry))
            } else {
                _effects.send(DiaryEffect.ShowDeleteFailed)
            }
        }
    }

    private fun restoreEntry(entry: MealEntry) {
        if (_uiState.value.isMutating) return
        _uiState.update { it.copy(isMutating = true) }

        viewModelScope.launch {
            val result = restoreMealEntry(entry)
            _uiState.update { it.copy(isMutating = false) }
            if (result.isFailure) {
                _effects.send(DiaryEffect.ShowRestoreFailed)
            }
        }
    }

    private fun setSearchLoading(query: String) {
        _uiState.update { state ->
            val sheet = state.addFoodSheet ?: return@update state
            if (sheet.customForm != null || sheet.query.trim() != query) state
            else state.copy(
                addFoodSheet = sheet.copy(
                    isSearching = true,
                    searchFailed = false
                )
            )
        }
    }

    private fun setSearchFailed(query: String) {
        _uiState.update { state ->
            val sheet = state.addFoodSheet ?: return@update state
            if (sheet.customForm != null || sheet.query.trim() != query) state
            else state.copy(
                addFoodSheet = sheet.copy(
                    isSearching = false,
                    searchFailed = true
                )
            )
        }
    }

    private fun setSubmitting(value: Boolean) {
        _uiState.update { state ->
            val sheet = state.addFoodSheet ?: return@update state
            state.copy(addFoodSheet = sheet.copy(isSubmitting = value))
        }
    }

    private fun closeSheetAfterSuccess() {
        searchQuery.value = null
        _uiState.update { it.copy(addFoodSheet = null) }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 300L
    }
}
