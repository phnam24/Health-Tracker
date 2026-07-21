package com.example.healthtracker.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

internal val ForestPrimaryLight = Color(0xFF15803D)
internal val ForestOnPrimaryLight = Color(0xFFFFFFFF)
internal val ForestPrimaryContainerLight = Color(0xFFDCFCE7)
internal val ForestOnPrimaryContainerLight = Color(0xFF14532D)
internal val ForestPrimaryDark = Color(0xFF86EFAC)
internal val ForestOnPrimaryDark = Color(0xFF052E16)
internal val ForestPrimaryContainerDark = Color(0xFF14532D)
internal val ForestOnPrimaryContainerDark = Color(0xFFDCFCE7)

internal val TealPrimaryLight = Color(0xFF0F766E)
internal val TealOnPrimaryLight = Color(0xFFFFFFFF)
internal val TealPrimaryContainerLight = Color(0xFFCCFBF1)
internal val TealOnPrimaryContainerLight = Color(0xFF134E4A)
internal val TealPrimaryDark = Color(0xFF5EEAD4)
internal val TealOnPrimaryDark = Color(0xFF042F2E)
internal val TealPrimaryContainerDark = Color(0xFF164E4A)
internal val TealOnPrimaryContainerDark = Color(0xFFCCFBF1)

internal val IndigoPrimaryLight = Color(0xFF4F46E5)
internal val IndigoOnPrimaryLight = Color(0xFFFFFFFF)
internal val IndigoPrimaryContainerLight = Color(0xFFE0E7FF)
internal val IndigoOnPrimaryContainerLight = Color(0xFF312E81)
internal val IndigoPrimaryDark = Color(0xFFA5B4FC)
internal val IndigoOnPrimaryDark = Color(0xFF1E1B4B)
internal val IndigoPrimaryContainerDark = Color(0xFF37306B)
internal val IndigoOnPrimaryContainerDark = Color(0xFFE0E7FF)

internal val VioletPrimaryLight = Color(0xFF6D28D9)
internal val VioletOnPrimaryLight = Color(0xFFFFFFFF)
internal val VioletPrimaryContainerLight = Color(0xFFEDE9FE)
internal val VioletOnPrimaryContainerLight = Color(0xFF4C1D95)
internal val VioletPrimaryDark = Color(0xFFC4B5FD)
internal val VioletOnPrimaryDark = Color(0xFF2F1065)
internal val VioletPrimaryContainerDark = Color(0xFF453477)
internal val VioletOnPrimaryContainerDark = Color(0xFFEDE9FE)

internal val FuchsiaSecondaryLight = Color(0xFFA21CAF)
internal val FuchsiaOnSecondaryLight = Color(0xFFFFFFFF)
internal val FuchsiaSecondaryContainerLight = Color(0xFFFAE8FF)
internal val FuchsiaOnSecondaryContainerLight = Color(0xFF701A75)
internal val FuchsiaSecondaryDark = Color(0xFFF0ABFC)
internal val FuchsiaOnSecondaryDark = Color(0xFF4A044E)
internal val FuchsiaSecondaryContainerDark = Color(0xFF5B2163)
internal val FuchsiaOnSecondaryContainerDark = Color(0xFFFCE7FF)

internal val BackgroundLight = Color(0xFFF7F9F8)
internal val OnBackgroundLight = Color(0xFF18201C)
internal val SurfaceLight = Color(0xFFFFFFFF)
internal val SurfaceDimLight = Color(0xFFE3E8E5)
internal val SurfaceBrightLight = Color(0xFFFFFFFF)
internal val SurfaceContainerLowestLight = Color(0xFFFFFFFF)
internal val SurfaceContainerLowLight = Color(0xFFF3F6F4)
internal val SurfaceContainerLight = Color(0xFFEEF2EF)
internal val SurfaceContainerHighLight = Color(0xFFE7ECE8)
internal val SurfaceContainerHighestLight = Color(0xFFDEE5E0)
internal val SurfaceVariantLight = Color(0xFFDEE5E0)
internal val OnSurfaceLight = Color(0xFF18201C)
internal val OnSurfaceVariantLight = Color(0xFF56625B)
internal val OutlineLight = Color(0xFF829087)
internal val OutlineVariantLight = Color(0xFFD3DDD6)
internal val InverseSurfaceLight = Color(0xFF2B322E)
internal val InverseOnSurfaceLight = Color(0xFFF1F5F2)

internal val BackgroundDark = Color(0xFF0C0F0D)
internal val OnBackgroundDark = Color(0xFFE6ECE8)
internal val SurfaceDark = Color(0xFF121614)
internal val SurfaceDimDark = Color(0xFF0C0F0D)
internal val SurfaceBrightDark = Color(0xFF343A36)
internal val SurfaceContainerLowestDark = Color(0xFF080A09)
internal val SurfaceContainerLowDark = Color(0xFF101311)
internal val SurfaceContainerDark = Color(0xFF1A1F1C)
internal val SurfaceContainerHighDark = Color(0xFF222824)
internal val SurfaceContainerHighestDark = Color(0xFF2B322D)
internal val SurfaceVariantDark = Color(0xFF2B322D)
internal val OnSurfaceDark = Color(0xFFE6ECE8)
internal val OnSurfaceVariantDark = Color(0xFFB8C2BC)
internal val OutlineDark = Color(0xFF858F89)
internal val OutlineVariantDark = Color(0xFF3D4640)
internal val InverseSurfaceDark = Color(0xFFE6ECE8)
internal val InverseOnSurfaceDark = Color(0xFF2B322D)

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
    cardContainer = SurfaceLight,
    onCardContainer = OnSurfaceLight,
    subtleContainer = SurfaceContainerLight,
    onSubtleContainer = OnSurfaceLight,
    neutralIconContainer = SurfaceContainerHighestLight,
    onNeutralIconContainer = OnSurfaceVariantLight,
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
    cardContainer = SurfaceContainerDark,
    onCardContainer = OnSurfaceDark,
    subtleContainer = SurfaceContainerHighDark,
    onSubtleContainer = OnSurfaceDark,
    neutralIconContainer = SurfaceContainerHighestDark,
    onNeutralIconContainer = OnSurfaceVariantDark,
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
