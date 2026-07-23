package com.example.healthtracker.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.healthtracker.domain.model.AppFontScale
import com.example.healthtracker.domain.model.ThemeMode
import com.example.healthtracker.domain.model.ThemePalette

private fun lightPaletteColorScheme(
    accents: PaletteAccentColors,
    surfaces: PaletteSurfaceColors,
    inversePrimary: Color,
): ColorScheme = lightColorScheme(
    primary = accents.primary,
    onPrimary = accents.onPrimary,
    primaryContainer = accents.primaryContainer,
    onPrimaryContainer = accents.onPrimaryContainer,
    surfaceTint = accents.primary,
    secondary = accents.secondary,
    onSecondary = accents.onSecondary,
    secondaryContainer = accents.secondaryContainer,
    onSecondaryContainer = accents.onSecondaryContainer,
    tertiary = accents.tertiary,
    onTertiary = accents.onTertiary,
    tertiaryContainer = accents.tertiaryContainer,
    onTertiaryContainer = accents.onTertiaryContainer,
    background = surfaces.background,
    onBackground = surfaces.onBackground,
    surface = surfaces.surface,
    surfaceDim = surfaces.surfaceDim,
    surfaceBright = surfaces.surfaceBright,
    surfaceContainerLowest = surfaces.surfaceContainerLowest,
    surfaceContainerLow = surfaces.surfaceContainerLow,
    surfaceContainer = surfaces.surfaceContainer,
    surfaceContainerHigh = surfaces.surfaceContainerHigh,
    surfaceContainerHighest = surfaces.surfaceContainerHighest,
    surfaceVariant = surfaces.surfaceVariant,
    onSurface = surfaces.onSurface,
    onSurfaceVariant = surfaces.onSurfaceVariant,
    outline = surfaces.outline,
    outlineVariant = surfaces.outlineVariant,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    inverseSurface = surfaces.inverseSurface,
    inverseOnSurface = surfaces.inverseOnSurface,
    inversePrimary = inversePrimary,
)

private fun darkPaletteColorScheme(
    accents: PaletteAccentColors,
    surfaces: PaletteSurfaceColors,
    inversePrimary: Color,
): ColorScheme = darkColorScheme(
    primary = accents.primary,
    onPrimary = accents.onPrimary,
    primaryContainer = accents.primaryContainer,
    onPrimaryContainer = accents.onPrimaryContainer,
    surfaceTint = accents.primary,
    secondary = accents.secondary,
    onSecondary = accents.onSecondary,
    secondaryContainer = accents.secondaryContainer,
    onSecondaryContainer = accents.onSecondaryContainer,
    tertiary = accents.tertiary,
    onTertiary = accents.onTertiary,
    tertiaryContainer = accents.tertiaryContainer,
    onTertiaryContainer = accents.onTertiaryContainer,
    background = surfaces.background,
    onBackground = surfaces.onBackground,
    surface = surfaces.surface,
    surfaceDim = surfaces.surfaceDim,
    surfaceBright = surfaces.surfaceBright,
    surfaceContainerLowest = surfaces.surfaceContainerLowest,
    surfaceContainerLow = surfaces.surfaceContainerLow,
    surfaceContainer = surfaces.surfaceContainer,
    surfaceContainerHigh = surfaces.surfaceContainerHigh,
    surfaceContainerHighest = surfaces.surfaceContainerHighest,
    surfaceVariant = surfaces.surfaceVariant,
    onSurface = surfaces.onSurface,
    onSurfaceVariant = surfaces.onSurfaceVariant,
    outline = surfaces.outline,
    outlineVariant = surfaces.outlineVariant,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    inverseSurface = surfaces.inverseSurface,
    inverseOnSurface = surfaces.inverseOnSurface,
    inversePrimary = inversePrimary,
)

private val FreshMintLightColorScheme = lightPaletteColorScheme(
    accents = FreshMintLightAccents,
    surfaces = FreshMintLightSurfaces,
    inversePrimary = FreshMintDarkAccents.primary,
)

private val FreshMintDarkColorScheme = darkPaletteColorScheme(
    accents = FreshMintDarkAccents,
    surfaces = FreshMintDarkSurfaces,
    inversePrimary = FreshMintLightAccents.primary,
)

private val OceanPulseLightColorScheme = lightPaletteColorScheme(
    accents = OceanPulseLightAccents,
    surfaces = OceanPulseLightSurfaces,
    inversePrimary = OceanPulseDarkAccents.primary,
)

private val OceanPulseDarkColorScheme = darkPaletteColorScheme(
    accents = OceanPulseDarkAccents,
    surfaces = OceanPulseDarkSurfaces,
    inversePrimary = OceanPulseLightAccents.primary,
)

private val CoralEnergyLightColorScheme = lightPaletteColorScheme(
    accents = CoralEnergyLightAccents,
    surfaces = CoralEnergyLightSurfaces,
    inversePrimary = CoralEnergyDarkAccents.primary,
)

private val CoralEnergyDarkColorScheme = darkPaletteColorScheme(
    accents = CoralEnergyDarkAccents,
    surfaces = CoralEnergyDarkSurfaces,
    inversePrimary = CoralEnergyLightAccents.primary,
)

private val AuroraVioletLightColorScheme = lightPaletteColorScheme(
    accents = AuroraVioletLightAccents,
    surfaces = AuroraVioletLightSurfaces,
    inversePrimary = AuroraVioletDarkAccents.primary,
)

private val AuroraVioletDarkColorScheme = darkPaletteColorScheme(
    accents = AuroraVioletDarkAccents,
    surfaces = AuroraVioletDarkSurfaces,
    inversePrimary = AuroraVioletLightAccents.primary,
)

private val LocalHealthTrackerColors = staticCompositionLocalOf {
    LightHealthTrackerColorScheme
}

private val LocalHealthTrackerDimensions = staticCompositionLocalOf {
    defaultHealthTrackerDimensions
}

val MaterialTheme.healthColors: HealthTrackerColorScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthTrackerColors.current

val MaterialTheme.dimensions: HealthTrackerDimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthTrackerDimensions.current

fun colorSchemeFor(
    palette: ThemePalette,
    darkTheme: Boolean,
): ColorScheme = when (palette) {
    ThemePalette.FOREST -> {
        if (darkTheme) FreshMintDarkColorScheme else FreshMintLightColorScheme
    }

    ThemePalette.TEAL -> {
        if (darkTheme) OceanPulseDarkColorScheme else OceanPulseLightColorScheme
    }

    ThemePalette.INDIGO -> {
        if (darkTheme) CoralEnergyDarkColorScheme else CoralEnergyLightColorScheme
    }

    ThemePalette.VIOLET -> {
        if (darkTheme) AuroraVioletDarkColorScheme else AuroraVioletLightColorScheme
    }
}

fun healthColorSchemeFor(
    palette: ThemePalette,
    darkTheme: Boolean,
): HealthTrackerColorScheme {
    val materialColors = colorSchemeFor(palette, darkTheme)
    val semanticColors = if (darkTheme) {
        DarkHealthTrackerColorScheme
    } else {
        LightHealthTrackerColorScheme
    }

    return semanticColors.copy(
        cardContainer = materialColors.surfaceContainerLow,
        onCardContainer = materialColors.onSurface,
        subtleContainer = materialColors.secondaryContainer,
        onSubtleContainer = materialColors.onSecondaryContainer,
        neutralIconContainer = materialColors.tertiaryContainer,
        onNeutralIconContainer = materialColors.onTertiaryContainer,
    )
}

@Composable
fun HealthTrackerTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    palette: ThemePalette = ThemePalette.FOREST,
    fontScale: AppFontScale = AppFontScale.MEDIUM,
    dimensions: HealthTrackerDimensions = defaultHealthTrackerDimensions,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val healthColors = healthColorSchemeFor(palette, darkTheme)
    val typography = remember(fontScale) { healthTrackerTypography(fontScale) }
    val shapes = remember(dimensions) { healthTrackerShapes(dimensions) }

    CompositionLocalProvider(
        LocalHealthTrackerColors provides healthColors,
        LocalHealthTrackerDimensions provides dimensions,
    ) {
        MaterialTheme(
            colorScheme = colorSchemeFor(palette, darkTheme),
            typography = typography,
            shapes = shapes,
            content = content,
        )
    }
}
