package com.example.healthtracker.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

enum class ThemePalette {
    GREEN,
    BLUE,
    ORANGE,
    PURPLE,
}

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM,
}

private val GreenLightColorScheme = lightColorScheme(
    primary = GreenPrimaryLight,
    onPrimary = GreenOnPrimaryLight,
    primaryContainer = GreenPrimaryContainerLight,
    onPrimaryContainer = GreenOnPrimaryContainerLight,
    surfaceTint = GreenPrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
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
    inversePrimary = GreenPrimaryDark,
)

private val GreenDarkColorScheme = darkColorScheme(
    primary = GreenPrimaryDark,
    onPrimary = GreenOnPrimaryDark,
    primaryContainer = GreenPrimaryContainerDark,
    onPrimaryContainer = GreenOnPrimaryContainerDark,
    surfaceTint = GreenPrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
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
    inversePrimary = GreenPrimaryLight,
)

private val LocalHealthTrackerColors = staticCompositionLocalOf {
    LightHealthTrackerColorScheme
}

val MaterialTheme.healthColors: HealthTrackerColorScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalHealthTrackerColors.current

private val BlueLightColorScheme = GreenLightColorScheme.copy(
    primary = BluePrimaryLight,
    onPrimary = BlueOnPrimaryLight,
    primaryContainer = BluePrimaryContainerLight,
    onPrimaryContainer = BlueOnPrimaryContainerLight,
    surfaceTint = BluePrimaryLight,
    inversePrimary = BluePrimaryDark,
)

private val BlueDarkColorScheme = GreenDarkColorScheme.copy(
    primary = BluePrimaryDark,
    onPrimary = BlueOnPrimaryDark,
    primaryContainer = BluePrimaryContainerDark,
    onPrimaryContainer = BlueOnPrimaryContainerDark,
    surfaceTint = BluePrimaryDark,
    inversePrimary = BluePrimaryLight,
)

private val OrangeLightColorScheme = GreenLightColorScheme.copy(
    primary = OrangePrimaryLight,
    onPrimary = OrangeOnPrimaryLight,
    primaryContainer = OrangePrimaryContainerLight,
    onPrimaryContainer = OrangeOnPrimaryContainerLight,
    surfaceTint = OrangePrimaryLight,
    inversePrimary = OrangePrimaryDark,
)

private val OrangeDarkColorScheme = GreenDarkColorScheme.copy(
    primary = OrangePrimaryDark,
    onPrimary = OrangeOnPrimaryDark,
    primaryContainer = OrangePrimaryContainerDark,
    onPrimaryContainer = OrangeOnPrimaryContainerDark,
    surfaceTint = OrangePrimaryDark,
    inversePrimary = OrangePrimaryLight,
)

private val PurpleLightColorScheme = GreenLightColorScheme.copy(
    primary = PurplePrimaryLight,
    onPrimary = PurpleOnPrimaryLight,
    primaryContainer = PurplePrimaryContainerLight,
    onPrimaryContainer = PurpleOnPrimaryContainerLight,
    surfaceTint = PurplePrimaryLight,
    inversePrimary = PurplePrimaryDark,
)

private val PurpleDarkColorScheme = GreenDarkColorScheme.copy(
    primary = PurplePrimaryDark,
    onPrimary = PurpleOnPrimaryDark,
    primaryContainer = PurplePrimaryContainerDark,
    onPrimaryContainer = PurpleOnPrimaryContainerDark,
    surfaceTint = PurplePrimaryDark,
    inversePrimary = PurplePrimaryLight,
)

fun colorSchemeFor(
    palette: ThemePalette,
    darkTheme: Boolean,
): ColorScheme = when (palette) {
    ThemePalette.GREEN -> if (darkTheme) GreenDarkColorScheme else GreenLightColorScheme
    ThemePalette.BLUE -> if (darkTheme) BlueDarkColorScheme else BlueLightColorScheme
    ThemePalette.ORANGE -> if (darkTheme) OrangeDarkColorScheme else OrangeLightColorScheme
    ThemePalette.PURPLE -> if (darkTheme) PurpleDarkColorScheme else PurpleLightColorScheme
}

@Composable
fun HealthTrackerTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    palette: ThemePalette = ThemePalette.GREEN,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val healthColors = if (darkTheme) {
        DarkHealthTrackerColorScheme
    } else {
        LightHealthTrackerColorScheme
    }

    CompositionLocalProvider(LocalHealthTrackerColors provides healthColors) {
        MaterialTheme(
            colorScheme = colorSchemeFor(palette, darkTheme),
            typography = HealthTrackerTypography,
            shapes = HealthTrackerShapes,
            content = content,
        )
    }
}