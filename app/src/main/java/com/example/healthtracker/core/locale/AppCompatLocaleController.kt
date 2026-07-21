package com.example.healthtracker.core.locale

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.healthtracker.domain.model.AppLanguage
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppCompatLocaleController @Inject constructor() : LocaleController {
    override fun getCurrentLanguage(): AppLanguage {
        val languageTag = AppCompatDelegate.getApplicationLocales()
            .get(0)
            ?.language
            ?: Locale.getDefault().language

        return AppLanguage.entries.firstOrNull { language ->
            language.languageTag.equals(languageTag, ignoreCase = true)
        } ?: AppLanguage.VIETNAMESE
    }

    override fun setLanguage(language: AppLanguage) {
        val currentLanguageTag = AppCompatDelegate.getApplicationLocales()
            .get(0)
            ?.language

        if (language.languageTag.equals(currentLanguageTag, ignoreCase = true)) return

        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(language.languageTag),
        )
    }
}
