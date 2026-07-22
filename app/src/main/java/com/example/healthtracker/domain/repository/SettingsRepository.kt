package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.AppFontScale
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.domain.model.ThemeMode
import com.example.healthtracker.domain.model.ThemePalette
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeSettings(): Flow<AppSettings>

    suspend fun setThemeMode(value: ThemeMode)

    suspend fun setPalette(value: ThemePalette)

    suspend fun setFontScale(value: AppFontScale)
}