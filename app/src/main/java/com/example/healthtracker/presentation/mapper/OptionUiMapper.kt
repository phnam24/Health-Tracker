package com.example.healthtracker.presentation.mapper

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
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Goal

data class OptionUiData(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val icon: ImageVector,
)

fun ActivityLevel.toOptionUiData(): OptionUiData = when (this) {
    ActivityLevel.SEDENTARY -> OptionUiData(
        titleRes = R.string.activity_sedentary_title,
        descriptionRes = R.string.activity_sedentary_description,
        icon = Icons.Filled.AirlineSeatReclineNormal,
    )

    ActivityLevel.LIGHT -> OptionUiData(
        titleRes = R.string.activity_light_title,
        descriptionRes = R.string.activity_light_description,
        icon = Icons.AutoMirrored.Filled.DirectionsWalk,
    )

    ActivityLevel.MODERATE -> OptionUiData(
        titleRes = R.string.activity_moderate_title,
        descriptionRes = R.string.activity_moderate_description,
        icon = Icons.AutoMirrored.Filled.DirectionsRun,
    )

    ActivityLevel.ACTIVE -> OptionUiData(
        titleRes = R.string.activity_active_title,
        descriptionRes = R.string.activity_active_description,
        icon = Icons.Filled.FitnessCenter,
    )

    ActivityLevel.VERY_ACTIVE -> OptionUiData(
        titleRes = R.string.activity_very_active_title,
        descriptionRes = R.string.activity_very_active_description,
        icon = Icons.Filled.Whatshot,
    )
}

fun Goal.toOptionUiData(): OptionUiData = when (this) {
    Goal.LOSE -> OptionUiData(
        titleRes = R.string.goal_lose_weight_title,
        descriptionRes = R.string.goal_lose_weight_description,
        icon = Icons.AutoMirrored.Filled.TrendingDown,
    )

    Goal.MAINTAIN -> OptionUiData(
        titleRes = R.string.goal_maintain_weight_title,
        descriptionRes = R.string.goal_maintain_weight_description,
        icon = Icons.AutoMirrored.Filled.TrendingFlat,
    )

    Goal.GAIN -> OptionUiData(
        titleRes = R.string.goal_gain_weight_title,
        descriptionRes = R.string.goal_gain_weight_description,
        icon = Icons.AutoMirrored.Filled.TrendingUp,
    )
}