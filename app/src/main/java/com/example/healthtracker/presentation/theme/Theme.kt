package com.example.healthtracker.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

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
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceVariant = SurfaceVariantLight,
    onSurface = OnSurfaceLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
)

private val GreenDarkColorScheme = darkColorScheme(
    primary = GreenPrimaryDark,
    onPrimary = GreenOnPrimaryDark,
    primaryContainer = GreenPrimaryContainerDark,
    onPrimaryContainer = GreenOnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceVariant = SurfaceVariantDark,
    onSurface = OnSurfaceDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
)

private val BlueLightColorScheme = GreenLightColorScheme.copy(
    primary = BluePrimaryLight,
    onPrimary = BlueOnPrimaryLight,
    primaryContainer = BluePrimaryContainerLight,
    onPrimaryContainer = BlueOnPrimaryContainerLight,
)

private val BlueDarkColorScheme = GreenDarkColorScheme.copy(
    primary = BluePrimaryDark,
    onPrimary = BlueOnPrimaryDark,
    primaryContainer = BluePrimaryContainerDark,
    onPrimaryContainer = BlueOnPrimaryContainerDark,
)

private val OrangeLightColorScheme = GreenLightColorScheme.copy(
    primary = OrangePrimaryLight,
    onPrimary = OrangeOnPrimaryLight,
    primaryContainer = OrangePrimaryContainerLight,
    onPrimaryContainer = OrangeOnPrimaryContainerLight,
)

private val OrangeDarkColorScheme = GreenDarkColorScheme.copy(
    primary = OrangePrimaryDark,
    onPrimary = OrangeOnPrimaryDark,
    primaryContainer = OrangePrimaryContainerDark,
    onPrimaryContainer = OrangeOnPrimaryContainerDark,
)

private val PurpleLightColorScheme = GreenLightColorScheme.copy(
    primary = PurplePrimaryLight,
    onPrimary = PurpleOnPrimaryLight,
    primaryContainer = PurplePrimaryContainerLight,
    onPrimaryContainer = PurpleOnPrimaryContainerLight,
)

private val PurpleDarkColorScheme = GreenDarkColorScheme.copy(
    primary = PurplePrimaryDark,
    onPrimary = PurpleOnPrimaryDark,
    primaryContainer = PurplePrimaryContainerDark,
    onPrimaryContainer = PurpleOnPrimaryContainerDark,
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

    MaterialTheme(
        colorScheme = colorSchemeFor(palette, darkTheme),
        typography = HealthTrackerTypography,
        shapes = HealthTrackerShapes,
        content = content,
    )
}