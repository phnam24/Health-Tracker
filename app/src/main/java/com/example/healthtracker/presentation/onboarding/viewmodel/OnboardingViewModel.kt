package com.example.healthtracker.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.CalculateAgeUseCase
import com.example.healthtracker.domain.usecase.CalculateBmiUseCase
import com.example.healthtracker.domain.usecase.CalculateTdeeUseCase
import com.example.healthtracker.presentation.onboarding.state.OnboardingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private fun updateName(value: String) {
        TODO("Not yet implemented")
    }

    private fun updateBirthDate(value: LocalDate) {
        TODO("Not yet implemented")
    }

    private fun updateGender(value: Gender) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    private fun moveToPreviousStep() {
        TODO("Not yet implemented")
    }

    private fun finishOnboarding() {
        TODO("Not yet implemented")
    }
}