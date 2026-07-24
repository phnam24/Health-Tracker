package com.example.healthtracker.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.healthtracker.domain.model.AppFontScale
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.domain.model.ThemeMode
import com.example.healthtracker.domain.model.ThemePalette
import com.example.healthtracker.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
) : SettingsRepository {
    private val dataStore = context.appSettingsDataStore

    override fun observeSettings(): Flow<AppSettings> =
        dataStore.data
            .catch { error ->
                if (error is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw error
                }
            }
            .map { preferences -> preferences.toAppSettings() }

    override suspend fun setThemeMode(value: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = value.storageId
        }
    }

    override suspend fun setPalette(value: ThemePalette) {
        dataStore.edit { preferences ->
            preferences[THEME_PALETTE_KEY] = value.storageId
        }
    }

    override suspend fun setFontScale(value: AppFontScale) {
        dataStore.edit { preferences ->
            preferences[FONT_SCALE_KEY] = value.storageId
        }
    }

    private fun Preferences.toAppSettings(): AppSettings =
        AppSettings(
            themeMode = ThemeMode.entries.firstOrNull {
                it.storageId == this[THEME_MODE_KEY]
            } ?: ThemeMode.SYSTEM,
            palette = ThemePalette.entries.firstOrNull {
                it.matchesStorageId(this[THEME_PALETTE_KEY])
            } ?: ThemePalette.ORGANIC,
            fontScale = AppFontScale.entries.firstOrNull {
                it.storageId == this[FONT_SCALE_KEY]
            } ?: AppFontScale.MEDIUM,
        )

    private companion object {
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        val THEME_PALETTE_KEY = stringPreferencesKey("theme_palette")
        val FONT_SCALE_KEY = stringPreferencesKey("font_scale")
    }
}
