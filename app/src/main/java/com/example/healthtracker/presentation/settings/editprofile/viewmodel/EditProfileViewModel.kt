package com.example.healthtracker.presentation.settings.editprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.OnboardingDraft
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.GetBmiPreviewUseCase
import com.example.healthtracker.domain.usecase.GetTdeePreviewUseCase
import com.example.healthtracker.domain.usecase.UpdateUserProfileResult
import com.example.healthtracker.domain.usecase.UpdateUserProfileUseCase
import com.example.healthtracker.domain.usecase.ValidateOnboardingUseCase
import com.example.healthtracker.presentation.components.normalizeDecimalInput
import com.example.healthtracker.presentation.settings.editprofile.state.EditProfileUiState
import com.example.healthtracker.presentation.settings.editprofile.state.toDraft
import com.example.healthtracker.presentation.settings.editprofile.state.toEditProfileUiState
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

sealed interface EditProfileEvent {
    data class NameChanged(val value: String) : EditProfileEvent
    data class BirthDateSelected(val value: LocalDate) : EditProfileEvent
    data class GenderSelected(val value: Gender) : EditProfileEvent
    data class WeightChanged(val value: String) : EditProfileEvent
    data class HeightChanged(val value: String) : EditProfileEvent
    data class ActivityLevelSelected(val value: ActivityLevel) : EditProfileEvent
    data class GoalSelected(val value: Goal) : EditProfileEvent
    data object RetryClicked : EditProfileEvent
    data object SaveClicked : EditProfileEvent
    data object BackClicked : EditProfileEvent
    data object DiscardConfirmed : EditProfileEvent
}

sealed interface EditProfileEffect {
    data class Saved(val newTargetCalories: Int) : EditProfileEffect
    data object SaveFailed : EditProfileEffect
    data object NavigateBack : EditProfileEffect
    data object ConfirmDiscard : EditProfileEffect
}

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val validate: ValidateOnboardingUseCase,
    private val getBmiPreview: GetBmiPreviewUseCase,
    private val getTdeePreview: GetTdeePreviewUseCase,
    private val updateUserProfile: UpdateUserProfileUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val _effects = Channel<EditProfileEffect>(Channel.BUFFERED)
    val effects: Flow<EditProfileEffect> = _effects.receiveAsFlow()

    private var initialDraft: OnboardingDraft? = null
    private var loadInProgress: Boolean = false
    private var navigateBackRequested: Boolean = false

    init {
        loadProfile()
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.NameChanged -> updateForm { copy(name = event.value) }
            is EditProfileEvent.BirthDateSelected -> updateForm {
                copy(birthDate = event.value)
            }

            is EditProfileEvent.GenderSelected -> updateForm { copy(gender = event.value) }
            is EditProfileEvent.WeightChanged -> updateForm {
                copy(weightInput = normalizeDecimalInput(event.value))
            }

            is EditProfileEvent.HeightChanged -> updateForm {
                copy(heightInput = normalizeDecimalInput(event.value))
            }

            is EditProfileEvent.ActivityLevelSelected -> updateForm {
                copy(activityLevel = event.value)
            }

            is EditProfileEvent.GoalSelected -> updateForm { copy(goal = event.value) }
            EditProfileEvent.RetryClicked -> retryLoad()
            EditProfileEvent.SaveClicked -> saveProfile()
            EditProfileEvent.BackClicked -> handleBack()
            EditProfileEvent.DiscardConfirmed -> discardChanges()
        }
    }

    private fun loadProfile() {
        if (loadInProgress || _uiState.value.isSaving) return
        loadInProgress = true

        _uiState.update {
            it.copy(
                isLoading = true,
                loadFailed = false,
                isSaving = false,
            )
        }

        viewModelScope.launch {
            try {
                val profile = userRepository.getProfile()
                if (profile == null) {
                    showLoadFailed()
                    return@launch
                }

                val loadedState = profile.toEditProfileUiState()
                initialDraft = loadedState.toDraft()
                _uiState.value = loadedState.withDerivedValues()
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                showLoadFailed()
            } finally {
                loadInProgress = false
            }
        }
    }

    private fun showLoadFailed() {
        initialDraft = null
        _uiState.value = EditProfileUiState(
            isLoading = false,
            loadFailed = true,
        )
    }

    private fun retryLoad() {
        if (!_uiState.value.loadFailed) return
        loadProfile()
    }

    private fun updateForm(
        transform: EditProfileUiState.() -> EditProfileUiState,
    ) {
        val state = _uiState.value
        if (state.isLoading || state.loadFailed || state.isSaving) return

        _uiState.value = state
            .transform()
            .withDerivedValues()
    }

    private fun EditProfileUiState.withDerivedValues(): EditProfileUiState {
        val draft = toDraft()
        val validationErrors = validate(draft)

        return copy(
            bmiPreview = getBmiPreview(draft),
            tdeePreview = if (validationErrors.isEmpty()) {
                getTdeePreview(draft)
            } else {
                null
            },
            errors = validationErrors,
            isFormValid = validationErrors.isEmpty(),
            isDirty = initialDraft?.let { draft != it } == true,
        )
    }

    private fun saveProfile() {
        val state = _uiState.value
        if (!state.canSave) return

        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            try {
                when (val result = updateUserProfile(_uiState.value.toDraft())) {
                    is UpdateUserProfileResult.Success -> {
                        _effects.send(
                            EditProfileEffect.Saved(result.newTargetCalories),
                        )
                    }

                    is UpdateUserProfileResult.InvalidData -> {
                        _uiState.update {
                            it.copy(
                                errors = result.errors,
                                isFormValid = false,
                                isSaving = false,
                                tdeePreview = null,
                            )
                        }
                    }
                }
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _uiState.update { it.copy(isSaving = false) }
                _effects.send(EditProfileEffect.SaveFailed)
            }
        }
    }

    private fun handleBack() {
        val state = _uiState.value
        if (state.isSaving) return

        if (state.isDirty) {
            emitEffect(EditProfileEffect.ConfirmDiscard)
        } else {
            requestNavigateBack()
        }
    }

    private fun discardChanges() {
        if (_uiState.value.isSaving) return
        requestNavigateBack()
    }

    private fun requestNavigateBack() {
        if (navigateBackRequested) return
        navigateBackRequested = true
        emitEffect(EditProfileEffect.NavigateBack)
    }

    private fun emitEffect(effect: EditProfileEffect) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }
}