package com.example.healthtracker.presentation.onboarding.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.presentation.theme.ActivityActive
import com.example.healthtracker.presentation.theme.ActivityLight
import com.example.healthtracker.presentation.theme.ActivityModerate
import com.example.healthtracker.presentation.theme.ActivitySedentary
import com.example.healthtracker.presentation.theme.ActivityVeryActive

data class ActivityLevelUiData(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val icon: ImageVector,
    val tintColor: Color
)

@Composable
fun ActivityLevel.getUiData(): ActivityLevelUiData {
    return when (this) {
        ActivityLevel.SEDENTARY -> ActivityLevelUiData(
            titleRes = R.string.activity_sedentary_title,
            descriptionRes = R.string.activity_sedentary_description,
            icon = Icons.Filled.AirlineSeatReclineNormal,
            tintColor = ActivitySedentary
        )

        ActivityLevel.LIGHT -> ActivityLevelUiData(
            titleRes = R.string.activity_light_title,
            descriptionRes = R.string.activity_light_description,
            icon = Icons.AutoMirrored.Filled.DirectionsWalk,
            tintColor = ActivityLight
        )

        ActivityLevel.MODERATE -> ActivityLevelUiData(
            titleRes = R.string.activity_moderate_title,
            descriptionRes = R.string.activity_moderate_description,
            icon = Icons.AutoMirrored.Filled.DirectionsRun,
            tintColor = ActivityModerate
        )

        ActivityLevel.ACTIVE -> ActivityLevelUiData(
            titleRes = R.string.activity_active_title,
            descriptionRes = R.string.activity_active_description,
            icon = Icons.Filled.FitnessCenter,
            tintColor = ActivityActive
        )

        ActivityLevel.VERY_ACTIVE -> ActivityLevelUiData(
            titleRes = R.string.activity_very_active_title,
            descriptionRes = R.string.activity_very_active_description,
            icon = Icons.Filled.Whatshot,
            tintColor = ActivityVeryActive
        )
    }
}