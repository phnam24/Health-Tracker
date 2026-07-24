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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
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

private val PerformanceLightColorScheme = lightPaletteColorScheme(
    accents = PerformanceLightAccents,
    surfaces = PerformanceLightSurfaces,
    inversePrimary = PerformanceDarkAccents.primary,
)

private val PerformanceDarkColorScheme = darkPaletteColorScheme(
    accents = PerformanceDarkAccents,
    surfaces = PerformanceDarkSurfaces,
    inversePrimary = PerformanceLightAccents.primary,
)

private val OrganicLightColorScheme = lightPaletteColorScheme(
    accents = OrganicLightAccents,
    surfaces = OrganicLightSurfaces,
    inversePrimary = OrganicDarkAccents.primary,
)

private val OrganicDarkColorScheme = darkPaletteColorScheme(
    accents = OrganicDarkAccents,
    surfaces = OrganicDarkSurfaces,
    inversePrimary = OrganicLightAccents.primary,
)

private val AnalyticalLightColorScheme = lightPaletteColorScheme(
    accents = AnalyticalLightAccents,
    surfaces = AnalyticalLightSurfaces,
    inversePrimary = AnalyticalDarkAccents.primary,
)

private val AnalyticalDarkColorScheme = darkPaletteColorScheme(
    accents = AnalyticalDarkAccents,
    surfaces = AnalyticalDarkSurfaces,
    inversePrimary = AnalyticalLightAccents.primary,
)

private val BalancedLightColorScheme = lightPaletteColorScheme(
    accents = BalancedLightAccents,
    surfaces = BalancedLightSurfaces,
    inversePrimary = BalancedDarkAccents.primary,
)

private val BalancedDarkColorScheme = darkPaletteColorScheme(
    accents = BalancedDarkAccents,
    surfaces = BalancedDarkSurfaces,
    inversePrimary = BalancedLightAccents.primary,
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

@Composable
fun rememberScreenBackgroundBrush(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
): Brush {
    val primary = colorScheme.primary
    val background = colorScheme.background

    return remember(primary, background) {
        val isDark = background.luminance() < 0.5f
        val startAlpha = if (isDark) 0.18f else 0.20f
        val middleAlpha = if (isDark) 0.07f else 0.08f

        Brush.verticalGradient(
            colors = listOf(
                primary.copy(alpha = startAlpha).compositeOver(background),
                primary.copy(alpha = middleAlpha).compositeOver(background),
                background,
            ),
        )
    }
}

fun colorSchemeFor(
    palette: ThemePalette,
    darkTheme: Boolean,
): ColorScheme = when (palette) {
    ThemePalette.PERFORMANCE -> {
        if (darkTheme) PerformanceDarkColorScheme else PerformanceLightColorScheme
    }

    ThemePalette.ORGANIC -> {
        if (darkTheme) OrganicDarkColorScheme else OrganicLightColorScheme
    }

    ThemePalette.ANALYTICAL -> {
        if (darkTheme) AnalyticalDarkColorScheme else AnalyticalLightColorScheme
    }

    ThemePalette.BALANCED -> {
        if (darkTheme) BalancedDarkColorScheme else BalancedLightColorScheme
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
        subtleContainer = materialColors.surfaceContainerHigh,
        onSubtleContainer = materialColors.onSurface,
        neutralIconContainer = materialColors.surfaceContainerHighest,
        onNeutralIconContainer = materialColors.onSurfaceVariant,
    )
}

@Composable
fun HealthTrackerTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    palette: ThemePalette = ThemePalette.ORGANIC,
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
