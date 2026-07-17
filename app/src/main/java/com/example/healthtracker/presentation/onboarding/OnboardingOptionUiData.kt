package com.example.healthtracker.presentation.onboarding

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingFlat
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.presentation.theme.healthColors

enum class OnboardingColorRole {
    ACTIVITY_SEDENTARY,
    ACTIVITY_LIGHT,
    ACTIVITY_MODERATE,
    ACTIVITY_ACTIVE,
    ACTIVITY_VERY_ACTIVE,
    GOAL_LOSE,
    GOAL_MAINTAIN,
    GOAL_GAIN,
}

data class OnboardingOptionUiData(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val icon: ImageVector,
    val colorRole: OnboardingColorRole,
)

fun ActivityLevel.getUiData(): OnboardingOptionUiData = when (this) {
    ActivityLevel.SEDENTARY -> OnboardingOptionUiData(
        titleRes = R.string.activity_sedentary_title,
        descriptionRes = R.string.activity_sedentary_description,
        icon = Icons.Filled.AirlineSeatReclineNormal,
        colorRole = OnboardingColorRole.ACTIVITY_SEDENTARY,
    )

    ActivityLevel.LIGHT -> OnboardingOptionUiData(
        titleRes = R.string.activity_light_title,
        descriptionRes = R.string.activity_light_description,
        icon = Icons.AutoMirrored.Filled.DirectionsWalk,
        colorRole = OnboardingColorRole.ACTIVITY_LIGHT,
    )

    ActivityLevel.MODERATE -> OnboardingOptionUiData(
        titleRes = R.string.activity_moderate_title,
        descriptionRes = R.string.activity_moderate_description,
        icon = Icons.AutoMirrored.Filled.DirectionsRun,
        colorRole = OnboardingColorRole.ACTIVITY_MODERATE,
    )

    ActivityLevel.ACTIVE -> OnboardingOptionUiData(
        titleRes = R.string.activity_active_title,
        descriptionRes = R.string.activity_active_description,
        icon = Icons.Filled.FitnessCenter,
        colorRole = OnboardingColorRole.ACTIVITY_ACTIVE,
    )

    ActivityLevel.VERY_ACTIVE -> OnboardingOptionUiData(
        titleRes = R.string.activity_very_active_title,
        descriptionRes = R.string.activity_very_active_description,
        icon = Icons.Filled.Whatshot,
        colorRole = OnboardingColorRole.ACTIVITY_VERY_ACTIVE,
    )
}

fun Goal.getUiData(): OnboardingOptionUiData = when (this) {
    Goal.LOSE -> OnboardingOptionUiData(
        titleRes = R.string.goal_lose_weight_title,
        descriptionRes = R.string.goal_lose_weight_description,
        icon = Icons.AutoMirrored.Filled.TrendingDown,
        colorRole = OnboardingColorRole.GOAL_LOSE,
    )

    Goal.MAINTAIN -> OnboardingOptionUiData(
        titleRes = R.string.goal_maintain_weight_title,
        descriptionRes = R.string.goal_maintain_weight_description,
        icon = Icons.AutoMirrored.Filled.TrendingFlat,
        colorRole = OnboardingColorRole.GOAL_MAINTAIN,
    )

    Goal.GAIN -> OnboardingOptionUiData(
        titleRes = R.string.goal_gain_weight_title,
        descriptionRes = R.string.goal_gain_weight_description,
        icon = Icons.AutoMirrored.Filled.TrendingUp,
        colorRole = OnboardingColorRole.GOAL_GAIN,
    )
}

@Composable
fun OnboardingColorRole.resolveColor(): Color = when (this) {
    OnboardingColorRole.ACTIVITY_SEDENTARY -> MaterialTheme.healthColors.activitySedentary
    OnboardingColorRole.ACTIVITY_LIGHT -> MaterialTheme.healthColors.activityLight
    OnboardingColorRole.ACTIVITY_MODERATE -> MaterialTheme.healthColors.activityModerate
    OnboardingColorRole.ACTIVITY_ACTIVE -> MaterialTheme.healthColors.activityActive
    OnboardingColorRole.ACTIVITY_VERY_ACTIVE -> MaterialTheme.healthColors.activityVeryActive
    OnboardingColorRole.GOAL_LOSE -> MaterialTheme.healthColors.caloriesBurned
    OnboardingColorRole.GOAL_MAINTAIN -> MaterialTheme.healthColors.success
    OnboardingColorRole.GOAL_GAIN -> MaterialTheme.healthColors.caloriesConsumed
}