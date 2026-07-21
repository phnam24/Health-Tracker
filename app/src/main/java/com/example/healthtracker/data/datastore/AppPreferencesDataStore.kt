package com.example.healthtracker.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val APP_SETTINGS_DATA_STORE_NAME = "health_tracker_settings"

internal val Context.appSettingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_SETTINGS_DATA_STORE_NAME,
)