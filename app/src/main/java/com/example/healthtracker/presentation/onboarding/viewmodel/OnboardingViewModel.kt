package com.example.healthtracker.presentation.onboarding.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.UserProfile
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.CalculateAgeUseCase
import com.example.healthtracker.domain.usecase.CalculateBmiUseCase
import com.example.healthtracker.domain.usecase.CalculateTdeeUseCase
import com.example.healthtracker.presentation.onboarding.state.OnboardingUiState
import com.example.healthtracker.presentation.onboarding.ui.OnboardingField
import com.example.healthtracker.presentation.onboarding.ui.OnboardingLimit
import com.example.healthtracker.presentation.onboarding.ui.OnboardingStep
import com.example.healthtracker.presentation.onboarding.ui.ValidationError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

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

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val calculateAge: CalculateAgeUseCase,
    private val calculateBmi: CalculateBmiUseCase,
    private val calculateTdee: CalculateTdeeUseCase,
    private val userRepository: UserRepository
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

    private fun clearError(field: OnboardingField) {
        _uiState.update { state ->
            state.copy(errors = state.errors - field)
        }
    }

    private fun updateName(value: String) {
        _uiState.update {
            it.copy(name = value)
        }
        clearError(OnboardingField.NAME)
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
        TODO("Not yet implemented")
    }

    private fun updateHeight(value: String) {
        TODO("Not yet implemented")
    }

    private fun updateActivity(value: ActivityLevel) {
        TODO("Not yet implemented")
    }

    private fun updateGoal(value: Goal) {
        TODO("Not yet implemented")
    }

    private fun moveToNextStep() {
        val state = _uiState.value
        if (state.currentStep.isLast || state.isSaving) return

        val errors = validateCurrentStep(state)
        if (errors.isNotEmpty()) {
            _uiState.update { it.copy(errors = errors) }
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

    private fun finishOnboarding() {
        TODO("Not yet implemented")
    }

    private fun recomputeBmiPreview() {
        _uiState.update { state ->
            val weight = state.weightInput.toDoubleOrNull()
            val height = state.heightInput.toDoubleOrNull()

            val preview = if (
                weight != null && weight in OnboardingLimit.WEIGHT_MIN.value..OnboardingLimit.WEIGHT_MAX.value &&
                height != null && height in OnboardingLimit.HEIGHT_MIN.value..OnboardingLimit.HEIGHT_MAX.value
            ) {
                calculateBmi(weight, height)
            } else {
                null
            }

            state.copy(bmiPreview = preview)
        }
    }

    private fun recomputeTdeePreview() {
        _uiState.update { state ->
            val profile = state.toUserProfileOrNull()
            state.copy(
                tdeePreview = profile?.let { calculateTdee(it) }
            )
        }
    }

    private fun OnboardingUiState.toUserProfileOrNull(): UserProfile? {
        val validName = name.trim().takeIf { it.isNotEmpty() } ?: return null
        val validBirthDate = birthDate ?: return null
        val validGender = gender ?: return null
        val validWeight = weightInput.toDoubleOrNull()
            ?.takeIf { it in OnboardingLimit.WEIGHT_MIN.value..OnboardingLimit.WEIGHT_MAX.value }
            ?: return null
        val validHeight = heightInput.toDoubleOrNull()
            ?.takeIf { it in OnboardingLimit.HEIGHT_MIN.value..OnboardingLimit.HEIGHT_MAX.value }
            ?: return null
        val validActivity = activityLevel ?: return null
        val validGoal = goal ?: return null

        return UserProfile(
            name = validName,
            birthDate = validBirthDate,
            gender = validGender,
            weightKg = validWeight,
            heightCm = validHeight,
            activityLevel = validActivity,
            goal = validGoal
        )
    }

    private fun validateCurrentStep(
        state: OnboardingUiState,
        today: LocalDate = LocalDate.now()
    ): Map<OnboardingField, ValidationError> = buildMap {
        when (state.currentStep) {
            OnboardingStep.NAME -> {
                when {
                    state.name.isBlank() -> put(OnboardingField.NAME, ValidationError.REQUIRED)
                    state.name.trim().length > 50 ->
                        put(OnboardingField.NAME, ValidationError.NAME_TOO_LONG)
                }
            }

            OnboardingStep.PERSONAL_INFO -> {
                val birthDate = state.birthDate
                when {
                    birthDate == null ->
                        put(OnboardingField.BIRTH_DATE, ValidationError.REQUIRED)

                    birthDate.isAfter(today) ->
                        put(OnboardingField.BIRTH_DATE, ValidationError.BIRTH_DATE_IN_FUTURE)

                    calculateAge(birthDate, today) !in 10..100 ->
                        put(OnboardingField.BIRTH_DATE, ValidationError.AGE_OUT_OF_RANGE)
                }
                if (state.gender == null) {
                    put(OnboardingField.GENDER, ValidationError.REQUIRED)
                }
            }

            OnboardingStep.BODY_METRICS -> {
                val weight = state.weightInput.toDoubleOrNull()
                val height = state.heightInput.toDoubleOrNull()

                when {
                    state.weightInput.isBlank() ->
                        put(OnboardingField.WEIGHT, ValidationError.REQUIRED)

                    weight == null ->
                        put(OnboardingField.WEIGHT, ValidationError.INVALID_NUMBER)

                    weight !in 20.0..300.0 ->
                        put(OnboardingField.WEIGHT, ValidationError.WEIGHT_OUT_OF_RANGE)
                }

                when {
                    state.heightInput.isBlank() ->
                        put(OnboardingField.HEIGHT, ValidationError.REQUIRED)

                    height == null ->
                        put(OnboardingField.HEIGHT, ValidationError.INVALID_NUMBER)

                    height !in 100.0..250.0 ->
                        put(OnboardingField.HEIGHT, ValidationError.HEIGHT_OUT_OF_RANGE)
                }
            }

            OnboardingStep.ACTIVITY_LEVEL -> {
                if (state.activityLevel == null) {
                    put(OnboardingField.ACTIVITY_LEVEL, ValidationError.REQUIRED)
                }
            }

            OnboardingStep.GOAL -> {
                if (state.goal == null) {
                    put(OnboardingField.GOAL, ValidationError.REQUIRED)
                }
            }
        }
    }

    private fun validateAllFields(
        state: OnboardingUiState,
        today: LocalDate = LocalDate.now()
    ): Map<OnboardingField, ValidationError> =
        OnboardingStep.entries
            .flatMap { step ->
                validateCurrentStep(
                    state = state.copy(currentStep = step),
                    today = today
                ).entries
            }
            .associate { it.key to it.value }
}