package com.example.healthtracker.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class HealthTrackerDimensions(
    val screenWidth: Dp = 360.dp,
    val screenHeight: Dp = 800.dp,
    val grid: Dp = 4.dp,
    val spacingExtraSmall: Dp = 4.dp,
    val spacingSmall: Dp = 8.dp,
    val spacingMedium: Dp = 12.dp,
    val spacingLarge: Dp = 16.dp,
    val spacingMediumLarge: Dp = 20.dp,
    val spacingExtraLarge: Dp = 24.dp,
    val spacingDoubleExtraLarge: Dp = 32.dp,
    val screenPadding: Dp = 16.dp,
    val cardSpacing: Dp = 12.dp,
    val cardPadding: Dp = 16.dp,
    val controlCornerRadius: Dp = 12.dp,
    val cardCornerRadius: Dp = 16.dp,
    val bottomSheetCornerRadius: Dp = 24.dp,
    val primaryButtonHeight: Dp = 52.dp,
    val textFieldHeight: Dp = 56.dp,
    val listItemHeight: Dp = 60.dp,
    val bottomNavigationHeight: Dp = 80.dp,
    val fabSize: Dp = 56.dp,
    val buttonCardHeight: Dp = 64.dp,
    val largeIconSize: Dp = 30.dp,
    val standardIconSize: Dp = 24.dp,
    val smallIconSize: Dp = 16.dp,
    val minimumTouchTarget: Dp = 48.dp,
    val emptyStateIconSize: Dp = 64.dp,
    val optionIconContainerSize: Dp = 40.dp,
    val statChipHeight: Dp = 28.dp,
    val calorieRingSize: Dp = 200.dp,
    val calorieRingStrokeWidth: Dp = 12.dp,
    val caloriesRingGapSize: Dp = 0.dp,
    val chartHeight: Dp = 180.dp,
    val chartBarWidth: Dp = 24.dp,
    val chartPointSize: Dp = 6.dp,
    val progressIndicatorHeight: Dp = 6.dp,
    val bmiIndicatorHeight: Dp = 8.dp,
    val dividerThickness: Dp = 1.dp,
    val focusedBorderThickness: Dp = 2.dp,
    val bottomSheetDragHandleWidth: Dp = 32.dp,
    val bottomSheetDragHandleHeight: Dp = 4.dp,
    val cardElevation: Dp = 1.dp,
    val raisedComponentElevation: Dp = 2.dp,
    val bottomSheetElevation: Dp = 3.dp,
    val splashLogoSize: Dp = 128.dp,
    val splashLogoMediumSize: Dp = 96.dp,
    val splashVersionBottomPadding: Dp = 48.dp,
)

internal val defaultHealthTrackerDimensions = HealthTrackerDimensions()
