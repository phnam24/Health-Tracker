package com.example.healthtracker.core.locale

import com.example.healthtracker.domain.model.AppLanguage

interface LocaleController {
    fun getCurrentLanguage(): AppLanguage
    fun setLanguage(language: AppLanguage)
}