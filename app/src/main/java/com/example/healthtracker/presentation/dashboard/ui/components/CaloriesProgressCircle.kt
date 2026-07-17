package com.example.healthtracker.presentation.dashboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DailySummary
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun CaloriesProgressCircle(
    dailySummary: DailySummary
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(MaterialTheme.dimensions.CalorieRingSize),
                color = MaterialTheme.colorScheme.surfaceVariant,
                strokeWidth = MaterialTheme.dimensions.CalorieRingStrokeWidth,
            )

            CircularProgressIndicator(
                progress = {
                    dailySummary.balanceCalories.toFloat() / dailySummary.goalCalories
                },
                modifier = Modifier.size(MaterialTheme.dimensions.CalorieRingSize),
                color = if (dailySummary.eatenCalories > dailySummary.goalCalories) {
                    MaterialTheme.healthColors.warning
                } else {
                    MaterialTheme.colorScheme.primary
                },
                strokeWidth = MaterialTheme.dimensions.CalorieRingStrokeWidth,
                trackColor = MaterialTheme.healthColors.transparent,
                strokeCap = StrokeCap.Round
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingExtraSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dailySummary.remainingCalories.toString(),
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = stringResource(R.string.dashboard_remaining),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = stringResource(R.string.common_kcal),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}