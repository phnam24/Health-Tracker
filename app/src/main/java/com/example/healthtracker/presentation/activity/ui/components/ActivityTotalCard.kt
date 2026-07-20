package com.example.healthtracker.presentation.activity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityDay
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun ActivityTotalCard(
    day: ActivityDay,
    modifier: Modifier = Modifier,
) {
    val summaryDescription = stringResource(
        R.string.cd_activity_total_summary,
        day.totalBurnedCalories,
        day.totalDurationMinutes,
        day.activityCount,
    )

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = MaterialTheme.dimensions.activityTotalCardMinHeight)
            .clearAndSetSemantics { contentDescription = summaryDescription },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    modifier = Modifier.size(MaterialTheme.dimensions.activityHeroIconSize),
                    shape = CircleShape,
                    color = MaterialTheme.healthColors.caloriesBurnedContainer,
                    contentColor = MaterialTheme.healthColors.caloriesBurned,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocalFireDepartment,
                        contentDescription = null,
                        modifier = Modifier.padding(MaterialTheme.dimensions.spacingLarge),
                    )
                }

                Column {
                    Text(
                        text = stringResource(R.string.activity_total_burned),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = stringResource(
                            R.string.activity_total_burned_value,
                            day.totalBurnedCalories,
                        ),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.healthColors.caloriesBurned,
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            Row(modifier = Modifier.fillMaxWidth()) {
                ActivitySummaryMetric(
                    icon = { Icon(Icons.Outlined.Timer, contentDescription = null) },
                    value = stringResource(
                        R.string.activity_total_duration_value,
                        day.totalDurationMinutes,
                    ),
                    modifier = Modifier.weight(1f),
                )
                ActivitySummaryMetric(
                    icon = { Icon(Icons.Outlined.DirectionsRun, contentDescription = null) },
                    value = stringResource(R.string.activity_count_value, day.activityCount),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun ActivitySummaryMetric(
    icon: @Composable () -> Unit,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier.size(MaterialTheme.dimensions.activitySummaryIconSize),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier.padding(MaterialTheme.dimensions.spacingSmall),
                contentAlignment = Alignment.Center,
            ) { icon() }
        }
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
