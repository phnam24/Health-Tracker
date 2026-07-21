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

enum class ThemePalette {
    FOREST,
    TEAL,
    INDIGO,
    VIOLET,
}

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM,
}

private val ForestLightColorScheme = lightColorScheme(
    primary = ForestPrimaryLight,
    onPrimary = ForestOnPrimaryLight,
    primaryContainer = ForestPrimaryContainerLight,
    onPrimaryContainer = ForestOnPrimaryContainerLight,
    surfaceTint = ForestPrimaryLight,
    secondary = TealPrimaryLight,
    onSecondary = TealOnPrimaryLight,
    secondaryContainer = TealPrimaryContainerLight,
    onSecondaryContainer = TealOnPrimaryContainerLight,
    tertiary = VioletPrimaryLight,
    onTertiary = VioletOnPrimaryLight,
    tertiaryContainer = VioletPrimaryContainerLight,
    onTertiaryContainer = VioletOnPrimaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    surfaceDim = SurfaceDimLight,
    surfaceBright = SurfaceBrightLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight,
    surfaceVariant = SurfaceVariantLight,
    onSurface = OnSurfaceLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    inverseSurface = InverseSurfaceLight,
    inverseOnSurface = InverseOnSurfaceLight,
    inversePrimary = ForestPrimaryDark,
)

private val ForestDarkColorScheme = darkColorScheme(
    primary = ForestPrimaryDark,
    onPrimary = ForestOnPrimaryDark,
    primaryContainer = ForestPrimaryContainerDark,
    onPrimaryContainer = ForestOnPrimaryContainerDark,
    surfaceTint = ForestPrimaryDark,
    secondary = TealPrimaryDark,
    onSecondary = TealOnPrimaryDark,
    secondaryContainer = TealPrimaryContainerDark,
    onSecondaryContainer = TealOnPrimaryContainerDark,
    tertiary = VioletPrimaryDark,
    onTertiary = VioletOnPrimaryDark,
    tertiaryContainer = VioletPrimaryContainerDark,
    onTertiaryContainer = VioletOnPrimaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    surfaceDim = SurfaceDimDark,
    surfaceBright = SurfaceBrightDark,
    surfaceContainerLowest = SurfaceContainerLowestDark,
    surfaceContainerLow = SurfaceContainerLowDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceContainerHighDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,
    surfaceVariant = SurfaceVariantDark,
    onSurface = OnSurfaceDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    inverseSurface = InverseSurfaceDark,
    inverseOnSurface = InverseOnSurfaceDark,
    inversePrimary = ForestPrimaryLight,
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

private val TealLightColorScheme = ForestLightColorScheme.copy(
    primary = TealPrimaryLight,
    onPrimary = TealOnPrimaryLight,
    primaryContainer = TealPrimaryContainerLight,
    onPrimaryContainer = TealOnPrimaryContainerLight,
    surfaceTint = TealPrimaryLight,
    secondary = IndigoPrimaryLight,
    onSecondary = IndigoOnPrimaryLight,
    secondaryContainer = IndigoPrimaryContainerLight,
    onSecondaryContainer = IndigoOnPrimaryContainerLight,
    tertiary = VioletPrimaryLight,
    onTertiary = VioletOnPrimaryLight,
    tertiaryContainer = VioletPrimaryContainerLight,
    onTertiaryContainer = VioletOnPrimaryContainerLight,
    inversePrimary = TealPrimaryDark,
)

private val TealDarkColorScheme = ForestDarkColorScheme.copy(
    primary = TealPrimaryDark,
    onPrimary = TealOnPrimaryDark,
    primaryContainer = TealPrimaryContainerDark,
    onPrimaryContainer = TealOnPrimaryContainerDark,
    surfaceTint = TealPrimaryDark,
    secondary = IndigoPrimaryDark,
    onSecondary = IndigoOnPrimaryDark,
    secondaryContainer = IndigoPrimaryContainerDark,
    onSecondaryContainer = IndigoOnPrimaryContainerDark,
    tertiary = VioletPrimaryDark,
    onTertiary = VioletOnPrimaryDark,
    tertiaryContainer = VioletPrimaryContainerDark,
    onTertiaryContainer = VioletOnPrimaryContainerDark,
    inversePrimary = TealPrimaryLight,
)

private val IndigoLightColorScheme = ForestLightColorScheme.copy(
    primary = IndigoPrimaryLight,
    onPrimary = IndigoOnPrimaryLight,
    primaryContainer = IndigoPrimaryContainerLight,
    onPrimaryContainer = IndigoOnPrimaryContainerLight,
    surfaceTint = IndigoPrimaryLight,
    secondary = VioletPrimaryLight,
    onSecondary = VioletOnPrimaryLight,
    secondaryContainer = VioletPrimaryContainerLight,
    onSecondaryContainer = VioletOnPrimaryContainerLight,
    tertiary = TealPrimaryLight,
    onTertiary = TealOnPrimaryLight,
    tertiaryContainer = TealPrimaryContainerLight,
    onTertiaryContainer = TealOnPrimaryContainerLight,
    inversePrimary = IndigoPrimaryDark,
)

private val IndigoDarkColorScheme = ForestDarkColorScheme.copy(
    primary = IndigoPrimaryDark,
    onPrimary = IndigoOnPrimaryDark,
    primaryContainer = IndigoPrimaryContainerDark,
    onPrimaryContainer = IndigoOnPrimaryContainerDark,
    surfaceTint = IndigoPrimaryDark,
    secondary = VioletPrimaryDark,
    onSecondary = VioletOnPrimaryDark,
    secondaryContainer = VioletPrimaryContainerDark,
    onSecondaryContainer = VioletOnPrimaryContainerDark,
    tertiary = TealPrimaryDark,
    onTertiary = TealOnPrimaryDark,
    tertiaryContainer = TealPrimaryContainerDark,
    onTertiaryContainer = TealOnPrimaryContainerDark,
    inversePrimary = IndigoPrimaryLight,
)

private val VioletLightColorScheme = ForestLightColorScheme.copy(
    primary = VioletPrimaryLight,
    onPrimary = VioletOnPrimaryLight,
    primaryContainer = VioletPrimaryContainerLight,
    onPrimaryContainer = VioletOnPrimaryContainerLight,
    surfaceTint = VioletPrimaryLight,
    secondary = FuchsiaSecondaryLight,
    onSecondary = FuchsiaOnSecondaryLight,
    secondaryContainer = FuchsiaSecondaryContainerLight,
    onSecondaryContainer = FuchsiaOnSecondaryContainerLight,
    tertiary = TealPrimaryLight,
    onTertiary = TealOnPrimaryLight,
    tertiaryContainer = TealPrimaryContainerLight,
    onTertiaryContainer = TealOnPrimaryContainerLight,
    inversePrimary = VioletPrimaryDark,
)

private val VioletDarkColorScheme = ForestDarkColorScheme.copy(
    primary = VioletPrimaryDark,
    onPrimary = VioletOnPrimaryDark,
    primaryContainer = VioletPrimaryContainerDark,
    onPrimaryContainer = VioletOnPrimaryContainerDark,
    surfaceTint = VioletPrimaryDark,
    secondary = FuchsiaSecondaryDark,
    onSecondary = FuchsiaOnSecondaryDark,
    secondaryContainer = FuchsiaSecondaryContainerDark,
    onSecondaryContainer = FuchsiaOnSecondaryContainerDark,
    tertiary = TealPrimaryDark,
    onTertiary = TealOnPrimaryDark,
    tertiaryContainer = TealPrimaryContainerDark,
    onTertiaryContainer = TealOnPrimaryContainerDark,
    inversePrimary = VioletPrimaryLight,
)

fun colorSchemeFor(
    palette: ThemePalette,
    darkTheme: Boolean,
): ColorScheme = when (palette) {
    ThemePalette.FOREST -> if (darkTheme) ForestDarkColorScheme else ForestLightColorScheme
    ThemePalette.TEAL -> if (darkTheme) TealDarkColorScheme else TealLightColorScheme
    ThemePalette.INDIGO -> if (darkTheme) IndigoDarkColorScheme else IndigoLightColorScheme
    ThemePalette.VIOLET -> if (darkTheme) VioletDarkColorScheme else VioletLightColorScheme
}

fun healthColorSchemeFor(darkTheme: Boolean): HealthTrackerColorScheme =
    if (darkTheme) DarkHealthTrackerColorScheme else LightHealthTrackerColorScheme

@Composable
fun HealthTrackerTheme(
    themeMode: ThemeMode = ThemeMode.DARK,
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

    val healthColors = healthColorSchemeFor(darkTheme)
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
