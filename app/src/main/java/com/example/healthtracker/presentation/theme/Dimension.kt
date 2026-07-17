package com.example.healthtracker.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class HealthTrackerDimensions(
    val ScreenWidth: Dp = 360.dp,
    val ScreenHeight: Dp = 800.dp,
    val Grid: Dp = 4.dp,
    val SpacingExtraSmall: Dp = 4.dp,
    val SpacingSmall: Dp = 8.dp,
    val SpacingMedium: Dp = 12.dp,
    val SpacingLarge: Dp = 16.dp,
    val SpacingMediumLarge: Dp = 20.dp,
    val SpacingExtraLarge: Dp = 24.dp,
    val SpacingDoubleExtraLarge: Dp = 32.dp,
    val ScreenPadding: Dp = 16.dp,
    val CardSpacing: Dp = 12.dp,
    val CardPadding: Dp = 16.dp,
    val ControlCornerRadius: Dp = 12.dp,
    val CardCornerRadius: Dp = 16.dp,
    val BottomSheetCornerRadius: Dp = 24.dp,
    val PrimaryButtonHeight: Dp = 52.dp,
    val TextFieldHeight: Dp = 56.dp,
    val ListItemHeight: Dp = 60.dp,
    val BottomNavigationHeight: Dp = 80.dp,
    val FabSize: Dp = 56.dp,
    val ButtonCardHeight: Dp = 64.dp,
    val LargeIconSize: Dp = 30.dp,
    val StandardIconSize: Dp = 24.dp,
    val SmallIconSize: Dp = 16.dp,
    val MinimumTouchTarget: Dp = 48.dp,
    val OptionIconContainerSize: Dp = 40.dp,
    val StatChipHeight: Dp = 28.dp,
    val CalorieRingSize: Dp = 220.dp,
    val CalorieRingStrokeWidth: Dp = 14.dp,
    val CaloriesRingGapSize: Dp = 0.dp,
    val ChartHeight: Dp = 180.dp,
    val ChartBarWidth: Dp = 24.dp,
    val ChartPointSize: Dp = 6.dp,
    val ProgressIndicatorHeight: Dp = 6.dp,
    val BmiIndicatorHeight: Dp = 8.dp,
    val DividerThickness: Dp = 1.dp,
    val FocusedBorderThickness: Dp = 2.dp,
    val BottomSheetDragHandleWidth: Dp = 32.dp,
    val BottomSheetDragHandleHeight: Dp = 4.dp,
    val CardElevation: Dp = 1.dp,
    val RaisedComponentElevation: Dp = 2.dp,
    val BottomSheetElevation: Dp = 3.dp,
    val SplashLogoSize: Dp = 128.dp,
    val SplashLogoMediumSize: Dp = 96.dp,
    val SplashVersionBottomPadding: Dp = 48.dp,
)

internal val DefaultHealthTrackerDimensions = HealthTrackerDimensions()