package com.example.healthtracker.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.domain.model.OnboardingValidationError
import com.example.healthtracker.domain.usecase.CalculateAgeUseCase
import com.example.healthtracker.domain.usecase.CompleteOnboardingResult
import com.example.healthtracker.domain.usecase.CompleteOnboardingUseCase
import com.example.healthtracker.domain.usecase.GetBmiPreviewUseCase
import com.example.healthtracker.domain.usecase.GetTdeePreviewUseCase
import com.example.healthtracker.domain.usecase.ValidateOnboardingUseCase
import com.example.healthtracker.presentation.components.normalizeDecimalInput
import com.example.healthtracker.presentation.onboarding.OnboardingStep
import com.example.healthtracker.presentation.onboarding.state.OnboardingUiState
import com.example.healthtracker.presentation.onboarding.state.toDraft
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

sealed interface OnboardingEvent {
    data class NameChanged(val value: String) : OnboardingEvent
    data class BirthDateSelected(val value: LocalDate) : OnboardingEvent
    data class GenderSelected(val value: Gender) : OnboardingEvent
    data class WeightChanged(val value: String) : OnboardingEvent
    data class HeightChanged(val value: String) : OnboardingEvent
    data class ActivityLevelSelected(val value: ActivityLevel) : OnboardingEvent
    data class GoalSelected(val value: Goal) : OnboardingEvent
    data object NextClicked : OnboardingEvent
    data object BackClicked : OnboardingEvent
    data object FinishClicked : OnboardingEvent
}

sealed interface OnboardingEffect {
    data object NavigateToDashboard : OnboardingEffect
    data object SaveFailed : OnboardingEffect
}

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val calculateAge: CalculateAgeUseCase,
    private val validateOnboarding: ValidateOnboardingUseCase,
    private val getBmiPreview: GetBmiPreviewUseCase,
    private val getTdeePreview: GetTdeePreviewUseCase,
    private val completeOnboarding: CompleteOnboardingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _effects = Channel<OnboardingEffect>(Channel.BUFFERED)
    val effects: Flow<OnboardingEffect> = _effects.receiveAsFlow()

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.NameChanged -> updateName(event.value)
            is OnboardingEvent.BirthDateSelected -> updateBirthDate(event.value)
            is OnboardingEvent.GenderSelected -> updateGender(event.value)
            is OnboardingEvent.WeightChanged -> updateWeight(event.value)
            is OnboardingEvent.HeightChanged -> updateHeight(event.value)
            is OnboardingEvent.ActivityLevelSelected -> updateActivity(event.value)
            is OnboardingEvent.GoalSelected -> updateGoal(event.value)
            OnboardingEvent.NextClicked -> moveToNextStep()
            OnboardingEvent.BackClicked -> moveToPreviousStep()
            OnboardingEvent.FinishClicked -> finishOnboarding()
        }
    }

    private fun updateName(value: String) {
        _uiState.update {
            it.copy(
                name = value,
                errors = it.errors - OnboardingField.NAME
            )
        }
    }

    private fun updateBirthDate(value: LocalDate) {
        val age = if (!value.isAfter(LocalDate.now())) {
            calculateAge(value)
        } else {
            null
        }
        _uiState.update {
            it.copy(
                birthDate = value,
                age = age,
                errors = it.errors - OnboardingField.BIRTH_DATE
            )
        }
    }

    private fun updateGender(value: Gender) {
        _uiState.update {
            it.copy(gender = value, errors = it.errors - OnboardingField.GENDER)
        }
    }

    private fun updateWeight(value: String) {
        _uiState.update {
            it.copy(
                weightInput = normalizeDecimalInput(value),
                errors = it.errors - OnboardingField.WEIGHT
            )
        }
        recomputeBmiPreview()
    }

    private fun updateHeight(value: String) {
        _uiState.update {
            it.copy(
                heightInput = normalizeDecimalInput(value),
                errors = it.errors - OnboardingField.HEIGHT
            )
        }
        recomputeBmiPreview()
    }

    private fun updateActivity(value: ActivityLevel) {
        _uiState.update {
            it.copy(
                activityLevel = value,
                errors = it.errors - OnboardingField.ACTIVITY_LEVEL
            )
        }
        recomputeTdeePreview()
    }

    private fun updateGoal(value: Goal) {
        _uiState.update {
            it.copy(goal = value, errors = it.errors - OnboardingField.GOAL)
        }
        recomputeTdeePreview()
    }

    private fun moveToNextStep() {
        val state = _uiState.value

        if (state.currentStep.isLast || state.isSaving) return

        val allErrors = validateOnboarding(state.toDraft())

        val currentStepErrors = allErrors.filterKeys { field ->
            field in state.currentStep.fields
        }

        if (currentStepErrors.isNotEmpty()) {
            _uiState.update {
                it.copy(errors = currentStepErrors)
            }
            return
        }

        _uiState.update {
            it.copy(
                currentStep = it.currentStep.next(),
                errors = emptyMap()
            )
        }

        if (_uiState.value.currentStep == OnboardingStep.GOAL) {
            recomputeTdeePreview()
        }
    }

    private fun moveToPreviousStep() {
        _uiState.update { state ->
            if (state.isSaving || state.currentStep.isFirst) state
            else state.copy(
                currentStep = state.currentStep.previous(),
                errors = emptyMap()
            )
        }
    }

    private fun recomputeBmiPreview() {
        _uiState.update { state ->
            state.copy(
                bmiPreview = getBmiPreview(state.toDraft())
            )
        }
    }

    private fun recomputeTdeePreview() {
        _uiState.update { state ->
            state.copy(
                tdeePreview = getTdeePreview(state.toDraft())
            )
        }
    }

    private fun finishOnboarding() {
        if (_uiState.value.isSaving) return

        _uiState.update {
            it.copy(isSaving = true)
        }

        viewModelScope.launch {
            try {
                when (
                    val result = completeOnboarding(
                        _uiState.value.toDraft()
                    )
                ) {
                    CompleteOnboardingResult.Success -> {
                        _effects.send(
                            OnboardingEffect.NavigateToDashboard
                        )
                    }

                    is CompleteOnboardingResult.InvalidData -> {
                        val firstInvalidStep =
                            findFirstInvalidStep(result.errors)

                        _uiState.update {
                            it.copy(
                                currentStep = firstInvalidStep,
                                errors = result.errors,
                                isSaving = false
                            )
                        }
                    }
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _uiState.update {
                    it.copy(isSaving = false)
                }

                _effects.send(
                    OnboardingEffect.SaveFailed
                )
            }
        }
    }

    private val OnboardingStep.fields: Set<OnboardingField>
        get() = when (this) {
            OnboardingStep.NAME -> setOf(
                OnboardingField.NAME
            )

            OnboardingStep.PERSONAL_INFO -> setOf(
                OnboardingField.BIRTH_DATE,
                OnboardingField.GENDER
            )

            OnboardingStep.BODY_METRICS -> setOf(
                OnboardingField.WEIGHT,
                OnboardingField.HEIGHT
            )

            OnboardingStep.ACTIVITY_LEVEL -> setOf(
                OnboardingField.ACTIVITY_LEVEL
            )

            OnboardingStep.GOAL -> setOf(
                OnboardingField.GOAL
            )
        }

    private fun findFirstInvalidStep(
        errors: Map<OnboardingField, OnboardingValidationError>
    ): OnboardingStep =
        OnboardingStep.entries.first { step ->
            step.fields.any(errors::containsKey)
        }

}
