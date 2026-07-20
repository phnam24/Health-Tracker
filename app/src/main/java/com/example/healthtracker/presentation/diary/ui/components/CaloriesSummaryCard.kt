package com.example.healthtracker.presentation.diary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DiaryDay
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.theme.dimensions
import kotlin.math.roundToInt

@Composable
fun CaloriesSummaryCard(
    day: DiaryDay,
    modifier: Modifier = Modifier,
) {
    val hasGoal = day.goalCalories > 0
    val rawProgress = if (hasGoal) {
        day.totalCalories.toFloat() / day.goalCalories
    } else {
        0f
    }
    val progress = rawProgress.coerceIn(0f, 1f)
    val percentage = (rawProgress.coerceAtLeast(0f) * 100).roundToInt()
    val overGoalBy = (day.totalCalories - day.goalCalories).coerceAtLeast(0)
    val progressColor = if (overGoalBy > 0) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }
    val progressDescription = if (hasGoal) {
        stringResource(
            R.string.cd_diary_calorie_progress,
            day.totalCalories,
            day.goalCalories,
        )
    } else {
        stringResource(R.string.diary_goal_unavailable)
    }

    AppCard(modifier = modifier.fillMaxWidth()) {
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
                text = if (hasGoal) {
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

            if (hasGoal) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.dimensions.progressIndicatorHeight)
                        .clip(CircleShape)
                        .semantics { contentDescription = progressDescription },
                    color = progressColor,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    strokeCap = StrokeCap.Round,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.diary_progress_percentage, percentage),
                        style = MaterialTheme.typography.labelLarge,
                        color = progressColor,
                    )
                    if (overGoalBy > 0) {
                        Text(
                            text = stringResource(R.string.diary_over_goal, overGoalBy),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            } else {
                Text(
                    text = stringResource(R.string.diary_goal_unavailable),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
