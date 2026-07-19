package com.example.healthtracker.presentation.diary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DiaryDay
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.theme.dimensions

@Composable
fun CaloriesSummaryCard(
    day: DiaryDay
) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.dimensions.spacingSmall
            )
        ) {
            Text(
                text = stringResource(R.string.diary_day_total),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = if (day.goalCalories > 0) {
                    stringResource(
                        R.string.diary_day_total_value,
                        day.totalCalories,
                        day.goalCalories
                    )
                } else {
                    "${day.totalCalories} ${stringResource(R.string.common_kcal)}"
                },
                style = MaterialTheme.typography.headlineSmall
            )
            if (day.goalCalories <= 0) {
                Text(
                    text = stringResource(R.string.diary_goal_unavailable),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
