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

internal val PerformanceLightAccents = PaletteAccentColors(
    primary = Color(0xFFA94726),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFF6DED5),
    onPrimaryContainer = Color(0xFF4A1B0C),
    secondary = Color(0xFF675C58),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFEAE3E0),
    onSecondaryContainer = Color(0xFF282220),
    tertiary = Color(0xFF56636D),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDFE7EC),
    onTertiaryContainer = Color(0xFF1B2B35),
)

internal val PerformanceDarkAccents = PaletteAccentColors(
    primary = Color(0xFFE39A7C),
    onPrimary = Color(0xFF4D1706),
    primaryContainer = Color(0xFF6E321D),
    onPrimaryContainer = Color(0xFFFFDCCE),
    secondary = Color(0xFFCBBAB3),
    onSecondary = Color(0xFF332A27),
    secondaryContainer = Color(0xFF4B413D),
    onSecondaryContainer = Color(0xFFEAE3E0),
    tertiary = Color(0xFFB8C5CE),
    onTertiary = Color(0xFF23313A),
    tertiaryContainer = Color(0xFF3C4952),
    onTertiaryContainer = Color(0xFFDFE7EC),
)

internal val PerformanceLightSurfaces = PaletteSurfaceColors(
    background = Color(0xFFF7F5F4),
    onBackground = Color(0xFF201A18),
    surface = Color(0xFFFFFDFC),
    surfaceDim = Color(0xFFDED9D6),
    surfaceBright = Color(0xFFFFFDFC),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF5F2F0),
    surfaceContainer = Color(0xFFEFECEA),
    surfaceContainerHigh = Color(0xFFE8E4E1),
    surfaceContainerHighest = Color(0xFFE0DBD8),
    surfaceVariant = Color(0xFFE0DBD8),
    onSurface = Color(0xFF201A18),
    onSurfaceVariant = Color(0xFF645B57),
    outline = Color(0xFF8C817C),
    outlineVariant = Color(0xFFD4CBC7),
    inverseSurface = Color(0xFF352F2C),
    inverseOnSurface = Color(0xFFF8EFEC),
)

internal val PerformanceDarkSurfaces = PaletteSurfaceColors(
    background = Color(0xFF121212),
    onBackground = Color(0xFFE9E5E3),
    surface = Color(0xFF191919),
    surfaceDim = Color(0xFF121212),
    surfaceBright = Color(0xFF393737),
    surfaceContainerLowest = Color(0xFF0E0E0E),
    surfaceContainerLow = Color(0xFF1D1D1D),
    surfaceContainer = Color(0xFF242424),
    surfaceContainerHigh = Color(0xFF2A2A2A),
    surfaceContainerHighest = Color(0xFF323232),
    surfaceVariant = Color(0xFF323232),
    onSurface = Color(0xFFE9E5E3),
    onSurfaceVariant = Color(0xFFC1B8B4),
    outline = Color(0xFF8A817D),
    outlineVariant = Color(0xFF47413E),
    inverseSurface = Color(0xFFE9E5E3),
    inverseOnSurface = Color(0xFF322D2B),
)

internal val OrganicLightAccents = PaletteAccentColors(
    primary = Color(0xFF267A4B),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFDCEDE3),
    onPrimaryContainer = Color(0xFF123C27),
    secondary = Color(0xFF5E6B61),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE5EBE6),
    onSecondaryContainer = Color(0xFF253028),
    tertiary = Color(0xFF786745),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFEEE8DB),
    onTertiaryContainer = Color(0xFF382E1C),
)

internal val OrganicDarkAccents = PaletteAccentColors(
    primary = Color(0xFF7FB996),
    onPrimary = Color(0xFF173B25),
    primaryContainer = Color(0xFF28523A),
    onPrimaryContainer = Color(0xFFDCEDE3),
    secondary = Color(0xFFBAC6BC),
    onSecondary = Color(0xFF2A342D),
    secondaryContainer = Color(0xFF414C44),
    onSecondaryContainer = Color(0xFFE5EBE6),
    tertiary = Color(0xFFC8B58D),
    onTertiary = Color(0xFF392F1C),
    tertiaryContainer = Color(0xFF52462F),
    onTertiaryContainer = Color(0xFFEEE8DB),
)

internal val OrganicLightSurfaces = PaletteSurfaceColors(
    background = Color(0xFFF8F9FA),
    onBackground = Color(0xFF19201C),
    surface = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFDDE2DF),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF3F5F4),
    surfaceContainer = Color(0xFFEEF1EF),
    surfaceContainerHigh = Color(0xFFE9ECEF),
    surfaceContainerHighest = Color(0xFFE2E6E3),
    surfaceVariant = Color(0xFFE2E6E3),
    onSurface = Color(0xFF19201C),
    onSurfaceVariant = Color(0xFF59645E),
    outline = Color(0xFF818B85),
    outlineVariant = Color(0xFFD0D7D3),
    inverseSurface = Color(0xFF2E3531),
    inverseOnSurface = Color(0xFFF1F5F2),
)

internal val OrganicDarkSurfaces = PaletteSurfaceColors(
    background = Color(0xFF111513),
    onBackground = Color(0xFFE5EAE7),
    surface = Color(0xFF1A201D),
    surfaceDim = Color(0xFF111513),
    surfaceBright = Color(0xFF39423D),
    surfaceContainerLowest = Color(0xFF0C100E),
    surfaceContainerLow = Color(0xFF171C1A),
    surfaceContainer = Color(0xFF1E2521),
    surfaceContainerHigh = Color(0xFF252D28),
    surfaceContainerHighest = Color(0xFF2E3732),
    surfaceVariant = Color(0xFF2E3732),
    onSurface = Color(0xFFE5EAE7),
    onSurfaceVariant = Color(0xFFB6C0BA),
    outline = Color(0xFF7F8A84),
    outlineVariant = Color(0xFF404944),
    inverseSurface = Color(0xFFE5EAE7),
    inverseOnSurface = Color(0xFF2B332F),
)

internal val AnalyticalLightAccents = PaletteAccentColors(
    primary = Color(0xFF356FA8),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFDCE8F5),
    onPrimaryContainer = Color(0xFF173959),
    secondary = Color(0xFF596979),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE5EAF0),
    onSecondaryContainer = Color(0xFF263440),
    tertiary = Color(0xFF6C6075),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFECE5EF),
    onTertiaryContainer = Color(0xFF382E3D),
)

internal val AnalyticalDarkAccents = PaletteAccentColors(
    primary = Color(0xFF8AAED2),
    onPrimary = Color(0xFF17334D),
    primaryContainer = Color(0xFF2D4E6D),
    onPrimaryContainer = Color(0xFFDCE8F5),
    secondary = Color(0xFFB8C5D1),
    onSecondary = Color(0xFF273642),
    secondaryContainer = Color(0xFF414E5A),
    onSecondaryContainer = Color(0xFFE5EAF0),
    tertiary = Color(0xFFC6B9CD),
    onTertiary = Color(0xFF352C3A),
    tertiaryContainer = Color(0xFF4D4452),
    onTertiaryContainer = Color(0xFFECE5EF),
)

internal val AnalyticalLightSurfaces = PaletteSurfaceColors(
    background = Color(0xFFF8FAFC),
    onBackground = Color(0xFF18202A),
    surface = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFDCE2E8),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF4F7FA),
    surfaceContainer = Color(0xFFF0F4F8),
    surfaceContainerHigh = Color(0xFFE8EDF3),
    surfaceContainerHighest = Color(0xFFDFE6ED),
    surfaceVariant = Color(0xFFDFE6ED),
    onSurface = Color(0xFF18202A),
    onSurfaceVariant = Color(0xFF566574),
    outline = Color(0xFF7E8B98),
    outlineVariant = Color(0xFFCCD5DE),
    inverseSurface = Color(0xFF2D3540),
    inverseOnSurface = Color(0xFFF1F4F8),
)

internal val AnalyticalDarkSurfaces = PaletteSurfaceColors(
    background = Color(0xFF11151A),
    onBackground = Color(0xFFE4E8ED),
    surface = Color(0xFF181E25),
    surfaceDim = Color(0xFF11151A),
    surfaceBright = Color(0xFF38414B),
    surfaceContainerLowest = Color(0xFF0C1014),
    surfaceContainerLow = Color(0xFF151B21),
    surfaceContainer = Color(0xFF1C232B),
    surfaceContainerHigh = Color(0xFF222A33),
    surfaceContainerHighest = Color(0xFF2B343E),
    surfaceVariant = Color(0xFF2B343E),
    onSurface = Color(0xFFE4E8ED),
    onSurfaceVariant = Color(0xFFB2BEC9),
    outline = Color(0xFF7D8A97),
    outlineVariant = Color(0xFF3D4853),
    inverseSurface = Color(0xFFE4E8ED),
    inverseOnSurface = Color(0xFF29313A),
)

internal val BalancedLightAccents = PaletteAccentColors(
    primary = Color(0xFF70529B),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEBE2F3),
    onPrimaryContainer = Color(0xFF332041),
    secondary = Color(0xFF69626C),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFEBE7EC),
    onSecondaryContainer = Color(0xFF302B32),
    tertiary = Color(0xFF536B68),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFE1EBE9),
    onTertiaryContainer = Color(0xFF213532),
)

internal val BalancedDarkAccents = PaletteAccentColors(
    primary = Color(0xFFB19AC8),
    onPrimary = Color(0xFF36234B),
    primaryContainer = Color(0xFF533B6B),
    onPrimaryContainer = Color(0xFFEBE2F3),
    secondary = Color(0xFFC7C0C9),
    onSecondary = Color(0xFF332E35),
    secondaryContainer = Color(0xFF49434C),
    onSecondaryContainer = Color(0xFFEBE7EC),
    tertiary = Color(0xFFB7C9C5),
    onTertiary = Color(0xFF273936),
    tertiaryContainer = Color(0xFF3E514E),
    onTertiaryContainer = Color(0xFFE1EBE9),
)

internal val BalancedLightSurfaces = PaletteSurfaceColors(
    background = Color(0xFFFAFAFA),
    onBackground = Color(0xFF201D22),
    surface = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFE0DEE1),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF6F5F6),
    surfaceContainer = Color(0xFFF0F1F3),
    surfaceContainerHigh = Color(0xFFE7E8EA),
    surfaceContainerHighest = Color(0xFFDEE0E3),
    surfaceVariant = Color(0xFFDEE0E3),
    onSurface = Color(0xFF201D22),
    onSurfaceVariant = Color(0xFF625D65),
    outline = Color(0xFF85828A),
    outlineVariant = Color(0xFFD1D5DB),
    inverseSurface = Color(0xFF343136),
    inverseOnSurface = Color(0xFFF6F2F7),
)

internal val BalancedDarkSurfaces = PaletteSurfaceColors(
    background = Color(0xFF141217),
    onBackground = Color(0xFFE9E5EB),
    surface = Color(0xFF1C1920),
    surfaceDim = Color(0xFF141217),
    surfaceBright = Color(0xFF3E3942),
    surfaceContainerLowest = Color(0xFF0F0D11),
    surfaceContainerLow = Color(0xFF19161C),
    surfaceContainer = Color(0xFF211E25),
    surfaceContainerHigh = Color(0xFF29252D),
    surfaceContainerHighest = Color(0xFF322E36),
    surfaceVariant = Color(0xFF322E36),
    onSurface = Color(0xFFE9E5EB),
    onSurfaceVariant = Color(0xFFC0B9C4),
    outline = Color(0xFF89818D),
    outlineVariant = Color(0xFF48414C),
    inverseSurface = Color(0xFFE9E5EB),
    inverseOnSurface = Color(0xFF312D34),
)

internal val ErrorLight = Color(0xFFB3261E)
internal val OnErrorLight = Color(0xFFFFFFFF)
internal val ErrorContainerLight = Color(0xFFF9DEDC)
internal val OnErrorContainerLight = Color(0xFF410E0B)
internal val ErrorDark = Color(0xFFF2B8B5)
internal val OnErrorDark = Color(0xFF601410)
internal val ErrorContainerDark = Color(0xFF8C1D18)
internal val OnErrorContainerDark = Color(0xFFF9DEDC)

internal val WarningLight = Color(0xFF9A6300)
internal val OnWarningLight = Color(0xFFFFFFFF)
internal val WarningContainerLight = Color(0xFFF4E8CF)
internal val OnWarningContainerLight = Color(0xFF4A3200)
internal val WarningDark = Color(0xFFD6AD61)
internal val OnWarningDark = Color(0xFF3C2B04)
internal val WarningContainerDark = Color(0xFF4A3818)
internal val OnWarningContainerDark = Color(0xFFF3E3BE)

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
    cardContainer = OrganicLightSurfaces.surfaceContainerLow,
    onCardContainer = OrganicLightSurfaces.onSurface,
    subtleContainer = OrganicLightSurfaces.surfaceContainerHigh,
    onSubtleContainer = OrganicLightSurfaces.onSurface,
    neutralIconContainer = OrganicLightSurfaces.surfaceContainerHighest,
    onNeutralIconContainer = OrganicLightSurfaces.onSurfaceVariant,
    caloriesConsumed = Color(0xFFB65B32),
    onCaloriesConsumed = Color(0xFFFFFFFF),
    caloriesConsumedContainer = Color(0xFFF5E3DA),
    onCaloriesConsumedContainer = Color(0xFF5B2715),
    caloriesBurned = Color(0xFF416C9F),
    onCaloriesBurned = Color(0xFFFFFFFF),
    caloriesBurnedContainer = Color(0xFFDEE8F4),
    onCaloriesBurnedContainer = Color(0xFF193A61),
    success = Color(0xFF2F7D52),
    onSuccess = Color(0xFFFFFFFF),
    successContainer = Color(0xFFDDEDE4),
    onSuccessContainer = Color(0xFF17442C),
    warning = WarningLight,
    onWarning = OnWarningLight,
    warningContainer = WarningContainerLight,
    onWarningContainer = OnWarningContainerLight,
    bmiUnderweight = Color(0xFF5479A5),
    bmiNormal = Color(0xFF4F8064),
    bmiOverweight = Color(0xFFB07827),
    bmiObese = Color(0xFFB64D49),
    transparent = Color.Transparent,
)

internal val DarkHealthTrackerColorScheme = HealthTrackerColorScheme(
    cardContainer = OrganicDarkSurfaces.surfaceContainerLow,
    onCardContainer = OrganicDarkSurfaces.onSurface,
    subtleContainer = OrganicDarkSurfaces.surfaceContainerHigh,
    onSubtleContainer = OrganicDarkSurfaces.onSurface,
    neutralIconContainer = OrganicDarkSurfaces.surfaceContainerHighest,
    onNeutralIconContainer = OrganicDarkSurfaces.onSurfaceVariant,
    caloriesConsumed = Color(0xFFD98B65),
    onCaloriesConsumed = Color(0xFF431D0D),
    caloriesConsumedContainer = Color(0xFF4A2A1D),
    onCaloriesConsumedContainer = Color(0xFFF5D8C9),
    caloriesBurned = Color(0xFF86A8D0),
    onCaloriesBurned = Color(0xFF16334F),
    caloriesBurnedContainer = Color(0xFF263D58),
    onCaloriesBurnedContainer = Color(0xFFDDE8F6),
    success = Color(0xFF7FC59A),
    onSuccess = Color(0xFF153923),
    successContainer = Color(0xFF244B33),
    onSuccessContainer = Color(0xFFD9F1E1),
    warning = WarningDark,
    onWarning = OnWarningDark,
    warningContainer = WarningContainerDark,
    onWarningContainer = OnWarningContainerDark,
    bmiUnderweight = Color(0xFF86A8D0),
    bmiNormal = Color(0xFF7FC59A),
    bmiOverweight = Color(0xFFD6AD61),
    bmiObese = Color(0xFFE48C88),
    transparent = Color.Transparent,
)
