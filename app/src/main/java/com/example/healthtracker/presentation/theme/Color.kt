package com.example.healthtracker.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
internal data class PaletteAccentColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
)

@Immutable
internal data class PaletteSurfaceColors(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val surfaceDim: Color,
    val surfaceBright: Color,
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val surfaceVariant: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val outlineVariant: Color,
    val inverseSurface: Color,
    val inverseOnSurface: Color,
)

internal val FreshMintLightAccents = PaletteAccentColors(
    primary = Color(0xFF007A55),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFA7F3D0),
    onPrimaryContainer = Color(0xFF003829),
    secondary = Color(0xFF4D7C0F),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD9F99D),
    onSecondaryContainer = Color(0xFF1A2E05),
    tertiary = Color(0xFF875900),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFEF08A),
    onTertiaryContainer = Color(0xFF3B2600),
)

internal val FreshMintDarkAccents = PaletteAccentColors(
    primary = Color(0xFF34D399),
    onPrimary = Color(0xFF003829),
    primaryContainer = Color(0xFF00513A),
    onPrimaryContainer = Color(0xFFA7F3D0),
    secondary = Color(0xFFA3E635),
    onSecondary = Color(0xFF263500),
    secondaryContainer = Color(0xFF3A5800),
    onSecondaryContainer = Color(0xFFD9F99D),
    tertiary = Color(0xFFFACC15),
    onTertiary = Color(0xFF3A2F00),
    tertiaryContainer = Color(0xFF5A4700),
    onTertiaryContainer = Color(0xFFFEF08A),
)

internal val FreshMintLightSurfaces = PaletteSurfaceColors(
    background = Color(0xFFF7FBF8),
    onBackground = Color(0xFF142019),
    surface = Color(0xFFFCFFFC),
    surfaceDim = Color(0xFFDCE7DF),
    surfaceBright = Color(0xFFFCFFFC),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF1F8F3),
    surfaceContainer = Color(0xFFEAF3ED),
    surfaceContainerHigh = Color(0xFFE2ECE6),
    surfaceContainerHighest = Color(0xFFD9E5DD),
    surfaceVariant = Color(0xFFD9E5DD),
    onSurface = Color(0xFF142019),
    onSurfaceVariant = Color(0xFF4B6355),
    outline = Color(0xFF73897B),
    outlineVariant = Color(0xFFC1D2C7),
    inverseSurface = Color(0xFF29332D),
    inverseOnSurface = Color(0xFFF0F7F2),
)

internal val FreshMintDarkSurfaces = PaletteSurfaceColors(
    background = Color(0xFF071510),
    onBackground = Color(0xFFE1F0E7),
    surface = Color(0xFF0D1B16),
    surfaceDim = Color(0xFF071510),
    surfaceBright = Color(0xFF2D3C35),
    surfaceContainerLowest = Color(0xFF04100C),
    surfaceContainerLow = Color(0xFF0B1813),
    surfaceContainer = Color(0xFF12231B),
    surfaceContainerHigh = Color(0xFF1A2C23),
    surfaceContainerHighest = Color(0xFF23362C),
    surfaceVariant = Color(0xFF23362C),
    onSurface = Color(0xFFE1F0E7),
    onSurfaceVariant = Color(0xFFAAC4B5),
    outline = Color(0xFF748E7F),
    outlineVariant = Color(0xFF354A3E),
    inverseSurface = Color(0xFFE1F0E7),
    inverseOnSurface = Color(0xFF26332C),
)

internal val OceanPulseLightAccents = PaletteAccentColors(
    primary = Color(0xFF0067C5),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD6E8FF),
    onPrimaryContainer = Color(0xFF001B3F),
    secondary = Color(0xFF007F75),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFA8F2E7),
    onSecondaryContainer = Color(0xFF00201C),
    tertiary = Color(0xFFB52E4B),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFD9DE),
    onTertiaryContainer = Color(0xFF3F0011),
)

internal val OceanPulseDarkAccents = PaletteAccentColors(
    primary = Color(0xFF60A5FA),
    onPrimary = Color(0xFF002D5A),
    primaryContainer = Color(0xFF004A8F),
    onPrimaryContainer = Color(0xFFD6E8FF),
    secondary = Color(0xFF2DD4BF),
    onSecondary = Color(0xFF003731),
    secondaryContainer = Color(0xFF005047),
    onSecondaryContainer = Color(0xFFA8F2E7),
    tertiary = Color(0xFFFB7185),
    onTertiary = Color(0xFF550018),
    tertiaryContainer = Color(0xFF7C1730),
    onTertiaryContainer = Color(0xFFFFD9DE),
)

internal val OceanPulseLightSurfaces = PaletteSurfaceColors(
    background = Color(0xFFF7FAFF),
    onBackground = Color(0xFF17202B),
    surface = Color(0xFFFCFCFF),
    surfaceDim = Color(0xFFDDE4ED),
    surfaceBright = Color(0xFFFCFCFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF1F5FB),
    surfaceContainer = Color(0xFFEBF0F7),
    surfaceContainerHigh = Color(0xFFE3EAF3),
    surfaceContainerHighest = Color(0xFFDAE3EE),
    surfaceVariant = Color(0xFFDAE3EE),
    onSurface = Color(0xFF17202B),
    onSurfaceVariant = Color(0xFF4A6173),
    outline = Color(0xFF71879A),
    outlineVariant = Color(0xFFBFCFDE),
    inverseSurface = Color(0xFF29333F),
    inverseOnSurface = Color(0xFFF0F4FA),
)

internal val OceanPulseDarkSurfaces = PaletteSurfaceColors(
    background = Color(0xFF071321),
    onBackground = Color(0xFFDCE5F2),
    surface = Color(0xFF0D1A29),
    surfaceDim = Color(0xFF071321),
    surfaceBright = Color(0xFF2D3B4B),
    surfaceContainerLowest = Color(0xFF040E19),
    surfaceContainerLow = Color(0xFF0A1725),
    surfaceContainer = Color(0xFF122232),
    surfaceContainerHigh = Color(0xFF1A2C3D),
    surfaceContainerHighest = Color(0xFF233647),
    surfaceVariant = Color(0xFF233647),
    onSurface = Color(0xFFDCE5F2),
    onSurfaceVariant = Color(0xFFA9BED0),
    outline = Color(0xFF738A9E),
    outlineVariant = Color(0xFF354A5C),
    inverseSurface = Color(0xFFDCE5F2),
    inverseOnSurface = Color(0xFF263441),
)

internal val CoralEnergyLightAccents = PaletteAccentColors(
    primary = Color(0xFFB93828),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDAD3),
    onPrimaryContainer = Color(0xFF3D0600),
    secondary = Color(0xFFB45309),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDCB8),
    onSecondaryContainer = Color(0xFF351000),
    tertiary = Color(0xFFA52668),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFD8E8),
    onTertiaryContainer = Color(0xFF3C0022),
)

internal val CoralEnergyDarkAccents = PaletteAccentColors(
    primary = Color(0xFFFF826F),
    onPrimary = Color(0xFF5B0B02),
    primaryContainer = Color(0xFF842016),
    onPrimaryContainer = Color(0xFFFFDAD3),
    secondary = Color(0xFFFDBA4B),
    onSecondary = Color(0xFF442A00),
    secondaryContainer = Color(0xFF6A3D00),
    onSecondaryContainer = Color(0xFFFFDCB8),
    tertiary = Color(0xFFF472B6),
    onTertiary = Color(0xFF57002F),
    tertiaryContainer = Color(0xFF7E1450),
    onTertiaryContainer = Color(0xFFFFD8E8),
)

internal val CoralEnergyLightSurfaces = PaletteSurfaceColors(
    background = Color(0xFFFFF8F6),
    onBackground = Color(0xFF251917),
    surface = Color(0xFFFFFBFA),
    surfaceDim = Color(0xFFEADDD9),
    surfaceBright = Color(0xFFFFFBFA),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFFF1ED),
    surfaceContainer = Color(0xFFF9EAE6),
    surfaceContainerHigh = Color(0xFFF2E2DD),
    surfaceContainerHighest = Color(0xFFEAD9D4),
    surfaceVariant = Color(0xFFEAD9D4),
    onSurface = Color(0xFF251917),
    onSurfaceVariant = Color(0xFF6A5651),
    outline = Color(0xFF927B75),
    outlineVariant = Color(0xFFDDC3BC),
    inverseSurface = Color(0xFF3A2D2A),
    inverseOnSurface = Color(0xFFFFEDEA),
)

internal val CoralEnergyDarkSurfaces = PaletteSurfaceColors(
    background = Color(0xFF190D10),
    onBackground = Color(0xFFF4DEDA),
    surface = Color(0xFF211316),
    surfaceDim = Color(0xFF190D10),
    surfaceBright = Color(0xFF473337),
    surfaceContainerLowest = Color(0xFF12080A),
    surfaceContainerLow = Color(0xFF1D1013),
    surfaceContainer = Color(0xFF29191C),
    surfaceContainerHigh = Color(0xFF342225),
    surfaceContainerHighest = Color(0xFF402C2F),
    surfaceVariant = Color(0xFF402C2F),
    onSurface = Color(0xFFF4DEDA),
    onSurfaceVariant = Color(0xFFD5B7B1),
    outline = Color(0xFF9B7D78),
    outlineVariant = Color(0xFF57403C),
    inverseSurface = Color(0xFFF4DEDA),
    inverseOnSurface = Color(0xFF392B2D),
)

internal val AuroraVioletLightAccents = PaletteAccentColors(
    primary = Color(0xFF7040D8),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF25005A),
    secondary = Color(0xFFB82872),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFD8E8),
    onSecondaryContainer = Color(0xFF3E0023),
    tertiary = Color(0xFF007F89),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFA6EFF4),
    onTertiaryContainer = Color(0xFF002023),
)

internal val AuroraVioletDarkAccents = PaletteAccentColors(
    primary = Color(0xFFA78BFA),
    onPrimary = Color(0xFF321070),
    primaryContainer = Color(0xFF5230A5),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFF472B6),
    onSecondary = Color(0xFF54002F),
    secondaryContainer = Color(0xFF7C1551),
    onSecondaryContainer = Color(0xFFFFD8E8),
    tertiary = Color(0xFF22D3EE),
    onTertiary = Color(0xFF00363C),
    tertiaryContainer = Color(0xFF00515A),
    onTertiaryContainer = Color(0xFFA6EFF4),
)

internal val AuroraVioletLightSurfaces = PaletteSurfaceColors(
    background = Color(0xFFFCF8FF),
    onBackground = Color(0xFF211A29),
    surface = Color(0xFFFFFBFF),
    surfaceDim = Color(0xFFE6DDEB),
    surfaceBright = Color(0xFFFFFBFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF8F1FC),
    surfaceContainer = Color(0xFFF2EAF7),
    surfaceContainerHigh = Color(0xFFEBE2F0),
    surfaceContainerHighest = Color(0xFFE3D9E9),
    surfaceVariant = Color(0xFFE3D9E9),
    onSurface = Color(0xFF211A29),
    onSurfaceVariant = Color(0xFF62566D),
    outline = Color(0xFF887A93),
    outlineVariant = Color(0xFFD3C4DD),
    inverseSurface = Color(0xFF352E3B),
    inverseOnSurface = Color(0xFFF8EDFC),
)

internal val AuroraVioletDarkSurfaces = PaletteSurfaceColors(
    background = Color(0xFF120B1D),
    onBackground = Color(0xFFE9DFF1),
    surface = Color(0xFF1A1225),
    surfaceDim = Color(0xFF120B1D),
    surfaceBright = Color(0xFF403449),
    surfaceContainerLowest = Color(0xFF0C0714),
    surfaceContainerLow = Color(0xFF171020),
    surfaceContainer = Color(0xFF221A2D),
    surfaceContainerHigh = Color(0xFF2D2438),
    surfaceContainerHighest = Color(0xFF382F43),
    surfaceVariant = Color(0xFF382F43),
    onSurface = Color(0xFFE9DFF1),
    onSurfaceVariant = Color(0xFFCAB9D4),
    outline = Color(0xFF907D9C),
    outlineVariant = Color(0xFF4C3E57),
    inverseSurface = Color(0xFFE9DFF1),
    inverseOnSurface = Color(0xFF342B3C),
)

internal val ErrorLight = Color(0xFFDC2626)
internal val OnErrorLight = Color(0xFFFFFFFF)
internal val ErrorContainerLight = Color(0xFFFEE2E2)
internal val OnErrorContainerLight = Color(0xFF7F1D1D)
internal val ErrorDark = Color(0xFFFF8A80)
internal val OnErrorDark = Color(0xFF4A0B12)
internal val ErrorContainerDark = Color(0xFF4E1D23)
internal val OnErrorContainerDark = Color(0xFFFFDAD6)

internal val WarningLight = Color(0xFFD56411)
internal val OnWarningLight = Color(0xFFFFFFFF)
internal val WarningContainerLight = Color(0xFFFFEDD5)
internal val OnWarningContainerLight = Color(0xFF7C2D12)
internal val WarningDark = Color(0xFFFBBF24)
internal val OnWarningDark = Color(0xFF422006)
internal val WarningContainerDark = Color(0xFF4D350B)
internal val OnWarningContainerDark = Color(0xFFFFF0B5)

@Immutable
data class HealthTrackerColorScheme(
    val cardContainer: Color,
    val onCardContainer: Color,
    val subtleContainer: Color,
    val onSubtleContainer: Color,
    val neutralIconContainer: Color,
    val onNeutralIconContainer: Color,
    val caloriesConsumed: Color,
    val onCaloriesConsumed: Color,
    val caloriesConsumedContainer: Color,
    val onCaloriesConsumedContainer: Color,
    val caloriesBurned: Color,
    val onCaloriesBurned: Color,
    val caloriesBurnedContainer: Color,
    val onCaloriesBurnedContainer: Color,
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,
    val bmiUnderweight: Color,
    val bmiNormal: Color,
    val bmiOverweight: Color,
    val bmiObese: Color,
    val transparent: Color,
)

internal val LightHealthTrackerColorScheme = HealthTrackerColorScheme(
    cardContainer = FreshMintLightSurfaces.surfaceContainerLow,
    onCardContainer = FreshMintLightSurfaces.onSurface,
    subtleContainer = FreshMintLightAccents.secondaryContainer,
    onSubtleContainer = FreshMintLightAccents.onSecondaryContainer,
    neutralIconContainer = FreshMintLightAccents.tertiaryContainer,
    onNeutralIconContainer = FreshMintLightAccents.onTertiaryContainer,
    caloriesConsumed = Color(0xFFFF7A45),
    onCaloriesConsumed = Color(0xFFFFFFFF),
    caloriesConsumedContainer = Color(0xFFFFE8DE),
    onCaloriesConsumedContainer = Color(0xFF7A2A08),
    caloriesBurned = Color(0xFF3B82F6),
    onCaloriesBurned = Color(0xFFFFFFFF),
    caloriesBurnedContainer = Color(0xFFDBEAFE),
    onCaloriesBurnedContainer = Color(0xFF1E3A8A),
    success = Color(0xFF22C55E),
    onSuccess = Color(0xFF052E16),
    successContainer = Color(0xFFDCFCE7),
    onSuccessContainer = Color(0xFF14532D),
    warning = WarningLight,
    onWarning = OnWarningLight,
    warningContainer = WarningContainerLight,
    onWarningContainer = OnWarningContainerLight,
    bmiUnderweight = Color(0xFF3B82F6),
    bmiNormal = Color(0xFF22C55E),
    bmiOverweight = Color(0xFFF59E0B),
    bmiObese = Color(0xFFEF4444),
    transparent = Color.Transparent,
)

internal val DarkHealthTrackerColorScheme = HealthTrackerColorScheme(
    cardContainer = FreshMintDarkSurfaces.surfaceContainerLow,
    onCardContainer = FreshMintDarkSurfaces.onSurface,
    subtleContainer = FreshMintDarkAccents.secondaryContainer,
    onSubtleContainer = FreshMintDarkAccents.onSecondaryContainer,
    neutralIconContainer = FreshMintDarkAccents.tertiaryContainer,
    onNeutralIconContainer = FreshMintDarkAccents.onTertiaryContainer,
    caloriesConsumed = Color(0xFFFB923C),
    onCaloriesConsumed = Color(0xFF431407),
    caloriesConsumedContainer = Color(0xFF4A2516),
    onCaloriesConsumedContainer = Color(0xFFFFE2D5),
    caloriesBurned = Color(0xFF60A5FA),
    onCaloriesBurned = Color(0xFF082F49),
    caloriesBurnedContainer = Color(0xFF172F4F),
    onCaloriesBurnedContainer = Color(0xFFDCEBFF),
    success = Color(0xFF4ADE80),
    onSuccess = Color(0xFF052E16),
    successContainer = Color(0xFF163C26),
    onSuccessContainer = Color(0xFFCBF7D8),
    warning = WarningDark,
    onWarning = OnWarningDark,
    warningContainer = WarningContainerDark,
    onWarningContainer = OnWarningContainerDark,
    bmiUnderweight = Color(0xFF60A5FA),
    bmiNormal = Color(0xFF4ADE80),
    bmiOverweight = Color(0xFFFBBF24),
    bmiObese = Color(0xFFFF8A80),
    transparent = Color.Transparent,
)
