package com.example.healthtracker.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

// Fresh Emerald — Light
internal val GreenPrimaryLight = Color(0xFF22C55E)
internal val GreenOnPrimaryLight = Color(0xFF052E16)
internal val GreenPrimaryContainerLight = Color(0xFFDCFCE7)
internal val GreenOnPrimaryContainerLight = Color(0xFF14532D)

// Fresh Emerald — Dark
internal val GreenPrimaryDark = Color(0xFF4ADE80)
internal val GreenOnPrimaryDark = Color(0xFF052E16)
internal val GreenPrimaryContainerDark = Color(0xFF166534)
internal val GreenOnPrimaryContainerDark = Color(0xFFDCFCE7)

// Shared secondary palette
internal val SecondaryLight = Color(0xFF0F766E)
internal val OnSecondaryLight = Color(0xFFFFFFFF)
internal val SecondaryContainerLight = Color(0xFFCCFBF1)
internal val OnSecondaryContainerLight = Color(0xFF134E4A)
internal val SecondaryDark = Color(0xFF5EEAD4)
internal val OnSecondaryDark = Color(0xFF042F2E)
internal val SecondaryContainerDark = Color(0xFF115E59)
internal val OnSecondaryContainerDark = Color(0xFFCCFBF1)

// Neutral palette — Light
internal val BackgroundLight = Color(0xFFF8FCF9)
internal val OnBackgroundLight = Color(0xFF132017)
internal val SurfaceLight = Color(0xFFFFFFFF)
internal val SurfaceDimLight = Color(0xFFE8F1EA)
internal val SurfaceBrightLight = Color(0xFFFFFFFF)
internal val SurfaceContainerLowestLight = Color(0xFFFFFFFF)
internal val SurfaceContainerLowLight = Color(0xFFF5FAF6)
internal val SurfaceContainerLight = Color(0xFFF0F8F2)
internal val SurfaceContainerHighLight = Color(0xFFE8F2EA)
internal val SurfaceContainerHighestLight = Color(0xFFDDEBE0)
internal val SurfaceVariantLight = Color(0xFFDDEBE0)
internal val OnSurfaceLight = Color(0xFF132017)
internal val OnSurfaceVariantLight = Color(0xFF586B5D)
internal val OutlineLight = Color(0xFFB4C9B9)
internal val OutlineVariantLight = Color(0xFFD5E2D7)
internal val InverseSurfaceLight = Color(0xFF29352C)
internal val InverseOnSurfaceLight = Color(0xFFF0F8F2)

// Neutral palette — Dark
internal val BackgroundDark = Color(0xFF09120C)
internal val OnBackgroundDark = Color(0xFFE4F0E7)
internal val SurfaceDark = Color(0xFF101A13)
internal val SurfaceDimDark = Color(0xFF09120C)
internal val SurfaceBrightDark = Color(0xFF344037)
internal val SurfaceContainerLowestDark = Color(0xFF060D08)
internal val SurfaceContainerLowDark = Color(0xFF101A13)
internal val SurfaceContainerDark = Color(0xFF17231A)
internal val SurfaceContainerHighDark = Color(0xFF1D2A20)
internal val SurfaceContainerHighestDark = Color(0xFF243329)
internal val SurfaceVariantDark = Color(0xFF243329)
internal val OnSurfaceDark = Color(0xFFE4F0E7)
internal val OnSurfaceVariantDark = Color(0xFFB2C4B6)
internal val OutlineDark = Color(0xFF46594B)
internal val OutlineVariantDark = Color(0xFF2E4033)
internal val InverseSurfaceDark = Color(0xFFE4F0E7)
internal val InverseOnSurfaceDark = Color(0xFF29352C)

// Error palette from ui_design_prompt.md
internal val ErrorLight = Color(0xFFDC2626)
internal val OnErrorLight = Color(0xFFFFFFFF)
internal val ErrorContainerLight = Color(0xFFFEE2E2)
internal val OnErrorContainerLight = Color(0xFF7F1D1D)
internal val ErrorDark = Color(0xFFF87171)
internal val OnErrorDark = Color(0xFF450A0A)
internal val ErrorContainerDark = Color(0xFF7F1D1D)
internal val OnErrorContainerDark = Color(0xFFFEE2E2)

// Alternate primary palettes
internal val BluePrimaryLight = Color(0xFF2196F3)
internal val BlueOnPrimaryLight = Color(0xFFFFFFFF)
internal val BluePrimaryContainerLight = Color(0xFFBBDEFB)
internal val BlueOnPrimaryContainerLight = Color(0xFF0D47A1)
internal val BluePrimaryDark = Color(0xFF90CAF9)
internal val BlueOnPrimaryDark = Color(0xFF003258)
internal val BluePrimaryContainerDark = Color(0xFF0D4770)
internal val BlueOnPrimaryContainerDark = Color(0xFFBBDEFB)

internal val OrangePrimaryLight = Color(0xFFFF9800)
internal val OrangeOnPrimaryLight = Color(0xFF3E2600)
internal val OrangePrimaryContainerLight = Color(0xFFFFE0B2)
internal val OrangeOnPrimaryContainerLight = Color(0xFF5D3A00)
internal val OrangePrimaryDark = Color(0xFFFFB74D)
internal val OrangeOnPrimaryDark = Color(0xFF4A2800)
internal val OrangePrimaryContainerDark = Color(0xFF633F00)
internal val OrangeOnPrimaryContainerDark = Color(0xFFFFE0B2)

internal val PurplePrimaryLight = Color(0xFF7C4DFF)
internal val PurpleOnPrimaryLight = Color(0xFFFFFFFF)
internal val PurplePrimaryContainerLight = Color(0xFFD1C4E9)
internal val PurpleOnPrimaryContainerLight = Color(0xFF311B92)
internal val PurplePrimaryDark = Color(0xFFB39DDB)
internal val PurpleOnPrimaryDark = Color(0xFF2F1065)
internal val PurplePrimaryContainerDark = Color(0xFF493783)
internal val PurpleOnPrimaryContainerDark = Color(0xFFD1C4E9)

/** Colors with product-specific meaning that Material 3's ColorScheme does not model. */
@Immutable
data class HealthTrackerColorScheme(
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
    val activitySedentary: Color,
    val activityLight: Color,
    val activityModerate: Color,
    val activityActive: Color,
    val activityVeryActive: Color,
    val transparent: Color,
)

internal val LightHealthTrackerColorScheme = HealthTrackerColorScheme(
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
    warning = Color(0xFFEF4444),
    onWarning = Color(0xFFFFFFFF),
    warningContainer = Color(0xFFFEE2E2),
    onWarningContainer = Color(0xFF7F1D1D),
    bmiUnderweight = Color(0xFF3B82F6),
    bmiNormal = Color(0xFF22C55E),
    bmiOverweight = Color(0xFFF59E0B),
    bmiObese = Color(0xFFEF4444),
    activitySedentary = Color(0xFF0F766E),
    activityLight = Color(0xFF22C55E),
    activityModerate = Color(0xFF2E7D32),
    activityActive = Color(0xFF2196F3),
    activityVeryActive = Color(0xFFFF7A45),
    transparent = Color.Transparent,
)

internal val DarkHealthTrackerColorScheme = HealthTrackerColorScheme(
    caloriesConsumed = Color(0xFFFFA07A),
    onCaloriesConsumed = Color(0xFF481500),
    caloriesConsumedContainer = Color(0xFF6B2B10),
    onCaloriesConsumedContainer = Color(0xFFFFDBCC),
    caloriesBurned = Color(0xFF93C5FD),
    onCaloriesBurned = Color(0xFF082F66),
    caloriesBurnedContainer = Color(0xFF1E3A5F),
    onCaloriesBurnedContainer = Color(0xFFDBEAFE),
    success = Color(0xFF4ADE80),
    onSuccess = Color(0xFF052E16),
    successContainer = Color(0xFF166534),
    onSuccessContainer = Color(0xFFDCFCE7),
    warning = Color(0xFFF87171),
    onWarning = Color(0xFF450A0A),
    warningContainer = Color(0xFF7F1D1D),
    onWarningContainer = Color(0xFFFEE2E2),
    bmiUnderweight = Color(0xFF93C5FD),
    bmiNormal = Color(0xFF4ADE80),
    bmiOverweight = Color(0xFFFBBF24),
    bmiObese = Color(0xFFF87171),
    activitySedentary = Color(0xFF5EEAD4),
    activityLight = Color(0xFF4ADE80),
    activityModerate = Color(0xFF86EFAC),
    activityActive = Color(0xFF90CAF9),
    activityVeryActive = Color(0xFFFFA07A),
    transparent = Color.Transparent,
)
