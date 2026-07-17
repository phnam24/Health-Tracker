package com.example.healthtracker.presentation.dashboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DailyAdvice
import com.example.healthtracker.domain.model.DailyAdviceType
import com.example.healthtracker.domain.model.DailySummary
import com.example.healthtracker.presentation.theme.AppDimensions
import com.example.healthtracker.presentation.theme.CardShape
import com.example.healthtracker.presentation.theme.healthColors
import kotlin.math.abs

@Composable
fun CaloriesStatCard(
    dailySummary: DailySummary,
    dailyAdvice: DailyAdvice
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = CardShape,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(AppDimensions.SpacingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
        ) {
            CaloriesStatCardHeader()

            HorizontalDivider()

            CaloriesStatEatenPercent(
                dailySummary = dailySummary
            )

            HorizontalDivider()

            CaloriesStatAdvice(
                dailySummary = dailySummary,
                dailyAdvice = dailyAdvice
            )
        }

    }
}


@Composable
fun CaloriesStatCardHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(AppDimensions.SpacingDoubleExtraLarge)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.BarChart,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(AppDimensions.SpacingMediumLarge)
            )
        }

        Spacer(modifier = Modifier.width(AppDimensions.SpacingSmall))

        Text(
            text = stringResource(R.string.dashboard_analysis_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun CaloriesStatEatenPercent(
    dailySummary: DailySummary
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${dailySummary.eatenCalories.toFloat() / dailySummary.goalCalories}%",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.alignByBaseline()
            )

            Spacer(modifier = Modifier.width(AppDimensions.SpacingExtraSmall))

            Text(
                text = stringResource(R.string.dashboard_goal_progress),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.alignByBaseline()
            )
        }

        Box(
            contentAlignment = Alignment.Center
        ) {
            LinearProgressIndicator(
                progress = { 1f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.SpacingSmall),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            LinearProgressIndicator(
                progress = { dailySummary.eatenCalories.toFloat() / dailySummary.goalCalories },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.SpacingSmall),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.healthColors.transparent,
                strokeCap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun CaloriesStatAdvice(
    dailySummary: DailySummary,
    dailyAdvice: DailyAdvice
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Balance,
                contentDescription = "Balance Icon",
                modifier = Modifier.size(AppDimensions.SpacingDoubleExtraLarge),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(AppDimensions.SpacingSmall))

            Column {
                Text(
                    text = stringResource(R.string.dashboard_energy_balance),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(AppDimensions.SpacingExtraSmall))

                Text(

                    text = if (dailySummary.balanceCalories == 0) stringResource(R.string.dashboard_balance_equal)
                    else if (dailySummary.balanceCalories > 0) stringResource(R.string.dashboard_balance_positive)
                    else stringResource(R.string.dashboard_balance_negative),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(
                    R.string.dashboard_balance_signed_value,
                    dailySummary.balanceCalories
                ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        OutlinedCard(
            shape = CardShape,
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.SpacingSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Advice Icon",
                    modifier = Modifier.size(AppDimensions.SpacingDoubleExtraLarge),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(Modifier.width(AppDimensions.SpacingMedium))

                Text(
                    text = when (dailyAdvice.type) {
                        DailyAdviceType.NEED_MORE -> stringResource(
                            R.string.dashboard_advice_need_more,
                            abs(dailyAdvice.differenceCalories)
                        )

                        DailyAdviceType.ON_TARGET -> stringResource(R.string.dashboard_advice_on_target)
                        DailyAdviceType.OVER -> stringResource(
                            R.string.dashboard_advice_over,
                            abs(dailyAdvice.differenceCalories)
                        )
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
