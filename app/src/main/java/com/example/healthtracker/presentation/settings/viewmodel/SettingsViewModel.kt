package com.example.healthtracker.presentation.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.core.locale.LocaleController
import com.example.healthtracker.domain.model.AppFontScale
import com.example.healthtracker.domain.model.AppLanguage
import com.example.healthtracker.domain.model.ThemeMode
import com.example.healthtracker.domain.model.ThemePalette
import com.example.healthtracker.domain.repository.SettingsRepository
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.CalculateAgeUseCase
import com.example.healthtracker.domain.usecase.CalculateBmiUseCase
import com.example.healthtracker.presentation.settings.state.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

sealed interface SettingsEvent {
    data object RetryClicked : SettingsEvent
    data object EditProfileClicked : SettingsEvent
    data class ThemeModeChanged(val value: ThemeMode) : SettingsEvent
    data class PaletteChanged(val value: ThemePalette) : SettingsEvent
    data class FontScaleChanged(val value: AppFontScale) : SettingsEvent
    data class LanguageChanged(val value: AppLanguage) : SettingsEvent
}

sealed interface SettingsEffect {
    data object NavigateToEditProfile : SettingsEffect
    data object PreferenceUpdateFailed : SettingsEffect
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository,
    private val localeController: LocaleController,
    private val calculateAge: CalculateAgeUseCase,
    private val calculateBmi: CalculateBmiUseCase,
    private val clock: Clock,
) : ViewModel() {
    private val retryTrigger = MutableStateFlow(0)
    private val selectedLanguage = MutableStateFlow(localeController.getCurrentLanguage())
    private val preferenceUpdateInProgress = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val contentState: Flow<SettingsUiState> = retryTrigger
        .flatMapLatest {
            combine(
                userRepository.observeProfile(),
                settingsRepository.observeSettings(),
                selectedLanguage,
            ) { profile, settings, language ->
                val today = LocalDate.now(clock)
                SettingsUiState(
                    isLoading = false,
                    profile = profile,
                    age = profile?.let { calculateAge(it.birthDate, today) },
                    bmi = profile?.let { calculateBmi(it.weightKg, it.heightCm) },
                    settings = settings,
                    language = language,
                    loadFailed = profile == null,
                )
            }
                .onStart {
                    emit(
                        SettingsUiState(
                            language = selectedLanguage.value,
                        )
                    )
                }
                .catch { error ->
                    if (error is CancellationException) throw error
                    emit(
                        SettingsUiState(
                            isLoading = false,
                            language = selectedLanguage.value,
                            loadFailed = true,
                        )
                    )
                }
        }

    val uiState: StateFlow<SettingsUiState> = combine(
        contentState,
        preferenceUpdateInProgress,
    ) { content, updateInProgress ->
        content.copy(preferenceUpdateInProgress = updateInProgress)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingsUiState(
            language = selectedLanguage.value,
        ),
    )

    private val _effects = Channel<SettingsEffect>(Channel.BUFFERED)
    val effects: Flow<SettingsEffect> = _effects.receiveAsFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.RetryClicked -> {
                selectedLanguage.value = localeController.getCurrentLanguage()
                retryTrigger.update { it + 1 }
            }

            SettingsEvent.EditProfileClicked -> emitEffect(SettingsEffect.NavigateToEditProfile)
            is SettingsEvent.ThemeModeChanged -> updatePreference {
                settingsRepository.setThemeMode(event.value)
            }

            is SettingsEvent.PaletteChanged -> updatePreference {
                settingsRepository.setPalette(event.value)
            }

            is SettingsEvent.FontScaleChanged -> updatePreference {
                settingsRepository.setFontScale(event.value)
            }

            is SettingsEvent.LanguageChanged -> updatePreference {
                localeController.setLanguage(event.value)
                selectedLanguage.value = localeController.getCurrentLanguage()
            }
        }
    }

    private fun updatePreference(update: suspend () -> Unit) {
        if (!preferenceUpdateInProgress.compareAndSet(expect = false, update = true)) return

        viewModelScope.launch {
            try {
                update()
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _effects.send(SettingsEffect.PreferenceUpdateFailed)
            } finally {
                preferenceUpdateInProgress.value = false
            }
        }
    }

    private fun emitEffect(effect: SettingsEffect) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }
}
