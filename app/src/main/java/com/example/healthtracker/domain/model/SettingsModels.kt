package com.example.healthtracker.domain.model

enum class ThemeMode(val storageId: String) {
    LIGHT("light"), DARK("dark"), SYSTEM("system")
}

enum class ThemePalette(
    val storageId: String,
    private val legacyStorageIds: Set<String> = emptySet(),
) {
    FRESH_MINT(
        storageId = "fresh_mint",
        legacyStorageIds = setOf("forest"),
    ),
    OCEAN_PULSE(
        storageId = "ocean_pulse",
        legacyStorageIds = setOf("teal"),
    ),
    CORAL_ENERGY(
        storageId = "coral_energy",
        legacyStorageIds = setOf("indigo"),
    ),
    AURORA_VIOLET(
        storageId = "aurora_violet",
        legacyStorageIds = setOf("violet"),
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
    val palette: ThemePalette = ThemePalette.FRESH_MINT,
    val fontScale: AppFontScale = AppFontScale.MEDIUM,
)
