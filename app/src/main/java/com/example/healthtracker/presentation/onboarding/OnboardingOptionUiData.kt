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
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.presentation.theme.ActivityActive
import com.example.healthtracker.presentation.theme.ActivityLight
import com.example.healthtracker.presentation.theme.ActivityModerate
import com.example.healthtracker.presentation.theme.ActivitySedentary
import com.example.healthtracker.presentation.theme.ActivityVeryActive

data class OnboardingOptionUiData(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val icon: ImageVector,
    val tintColor: Color
)

@Composable
fun ActivityLevel.getUiData(): OnboardingOptionUiData {
    return when (this) {
        ActivityLevel.SEDENTARY -> OnboardingOptionUiData(
            titleRes = R.string.activity_sedentary_title,
            descriptionRes = R.string.activity_sedentary_description,
            icon = Icons.Filled.AirlineSeatReclineNormal,
            tintColor = ActivitySedentary
        )

        ActivityLevel.LIGHT -> OnboardingOptionUiData(
            titleRes = R.string.activity_light_title,
            descriptionRes = R.string.activity_light_description,
            icon = Icons.AutoMirrored.Filled.DirectionsWalk,
            tintColor = ActivityLight
        )

        ActivityLevel.MODERATE -> OnboardingOptionUiData(
            titleRes = R.string.activity_moderate_title,
            descriptionRes = R.string.activity_moderate_description,
            icon = Icons.AutoMirrored.Filled.DirectionsRun,
            tintColor = ActivityModerate
        )

        ActivityLevel.ACTIVE -> OnboardingOptionUiData(
            titleRes = R.string.activity_active_title,
            descriptionRes = R.string.activity_active_description,
            icon = Icons.Filled.FitnessCenter,
            tintColor = ActivityActive
        )

        ActivityLevel.VERY_ACTIVE -> OnboardingOptionUiData(
            titleRes = R.string.activity_very_active_title,
            descriptionRes = R.string.activity_very_active_description,
            icon = Icons.Filled.Whatshot,
            tintColor = ActivityVeryActive
        )
    }
}

@Composable
fun Goal.getUiData(): OnboardingOptionUiData {
    return when (this) {
        Goal.LOSE -> OnboardingOptionUiData(
            titleRes = R.string.goal_lose_weight_title,
            descriptionRes = R.string.goal_lose_weight_description,
            icon = Icons.AutoMirrored.Filled.TrendingDown,
            tintColor = ActivitySedentary
        )

        Goal.MAINTAIN -> OnboardingOptionUiData(
            titleRes = R.string.goal_maintain_weight_title,
            descriptionRes = R.string.goal_maintain_weight_description,
            icon = Icons.AutoMirrored.Filled.TrendingFlat,
            tintColor = ActivityLight
        )

        Goal.GAIN -> OnboardingOptionUiData(
            titleRes = R.string.goal_gain_weight_title,
            descriptionRes = R.string.goal_gain_weight_description,
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            tintColor = ActivityVeryActive
        )
    }
}