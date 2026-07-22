package com.example.healthtracker.presentation.settings.state

import com.example.healthtracker.domain.model.AppLanguage
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.domain.model.BmiResult
import com.example.healthtracker.domain.model.UserProfile

data class SettingsUiState(
    val isLoading: Boolean = true,
    val profile: UserProfile? = null,
    val age: Int? = null,
    val bmi: BmiResult? = null,
    val settings: AppSettings = AppSettings(),
    val language: AppLanguage = AppLanguage.VIETNAMESE,
    val preferenceUpdateInProgress: Boolean = false,
    val loadFailed: Boolean = false,
)