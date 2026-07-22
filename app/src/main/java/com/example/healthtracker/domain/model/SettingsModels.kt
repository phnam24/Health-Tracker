package com.example.healthtracker.domain.model

enum class ThemeMode(val storageId: String) {
    LIGHT("light"), DARK("dark"), SYSTEM("system")
}

enum class ThemePalette(val storageId: String) {
    FOREST("forest"), TEAL("teal"), INDIGO("indigo"), VIOLET("violet")
}

enum class AppFontScale(val storageId: String, val multiplier: Float) {
    SMALL("small", 0.85f), MEDIUM("medium", 1.0f), LARGE("large", 1.15f)
}

enum class AppLanguage(val languageTag: String) {
    VIETNAMESE("vi"),
    ENGLISH("en"),
}

data class AppSettings(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val palette: ThemePalette = ThemePalette.FOREST,
    val fontScale: AppFontScale = AppFontScale.MEDIUM,
)