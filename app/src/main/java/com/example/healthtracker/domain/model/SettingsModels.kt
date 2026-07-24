package com.example.healthtracker.domain.model

enum class ThemeMode(val storageId: String) {
    LIGHT("light"), DARK("dark"), SYSTEM("system")
}

enum class ThemePalette(
    val storageId: String,
    private val legacyStorageIds: Set<String> = emptySet(),
) {
    PERFORMANCE(
        storageId = "performance",
        legacyStorageIds = setOf("coral_energy", "indigo"),
    ),
    ORGANIC(
        storageId = "organic",
        legacyStorageIds = setOf("fresh_mint", "forest"),
    ),
    ANALYTICAL(
        storageId = "analytical",
        legacyStorageIds = setOf("ocean_pulse", "teal"),
    ),
    BALANCED(
        storageId = "balanced",
        legacyStorageIds = setOf("aurora_violet", "violet"),
    ),
    ;

    fun matchesStorageId(value: String?): Boolean =
        value != null && (value == storageId || value in legacyStorageIds)
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
    val palette: ThemePalette = ThemePalette.ORGANIC,
    val fontScale: AppFontScale = AppFontScale.MEDIUM,
)
