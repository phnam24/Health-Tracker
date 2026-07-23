package com.example.healthtracker.presentation.dashboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.AppCardVariant
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors
import kotlin.math.abs

@Composable
fun CaloriesStatCard(
    dailySummary: DailySummary,
    dailyAdvice: DailyAdvice,
) {
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        variant = AppCardVariant.OUTLINED,
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.healthColors.cardContainer,
        contentColor = MaterialTheme.healthColors.onCardContainer,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
        ) {
            CaloriesStatCardHeader()

            HorizontalDivider()

            CaloriesStatEatenPercent(
                dailySummary = dailySummary,
            )

            HorizontalDivider()

            CaloriesStatAdvice(
                dailySummary = dailySummary,
                dailyAdvice = dailyAdvice,
            )
        }

    }
}


@Composable
private fun CaloriesStatCardHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.dimensions.spacingDoubleExtraLarge)
                .background(
                    color = MaterialTheme.healthColors.neutralIconContainer,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.BarChart,
                contentDescription = null,
                tint = MaterialTheme.healthColors.onNeutralIconContainer,
                modifier = Modifier.size(MaterialTheme.dimensions.spacingMediumLarge),
            )
        }

        Spacer(modifier = Modifier.width(MaterialTheme.dimensions.spacingSmall))

        Text(
            text = stringResource(R.string.dashboard_analysis_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun CaloriesStatEatenPercent(
    dailySummary: DailySummary,
) {
    val progress = dailySummary.toCalorieProgressUi()
    val progressColor = if (progress.isOverGoal) {
        MaterialTheme.healthColors.warning
    } else {
        MaterialTheme.colorScheme.primary
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(
                    R.string.dashboard_percentage_value,
                    progress.percentage,
                ),
                style = MaterialTheme.typography.headlineMedium,
                color = progressColor,
                modifier = Modifier.alignByBaseline(),
            )

            Spacer(modifier = Modifier.width(MaterialTheme.dimensions.spacingExtraSmall))

            Text(
                text = stringResource(R.string.dashboard_goal_progress),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.alignByBaseline(),
            )
        }

        Box(
            contentAlignment = Alignment.Center,
        ) {
            LinearProgressIndicator(
                progress = { 1f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimensions.spacingSmall),
                color = MaterialTheme.colorScheme.outlineVariant,
            )

            LinearProgressIndicator(
                progress = { progress.indicatorFraction },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimensions.spacingSmall),
                color = progressColor,
                trackColor = MaterialTheme.healthColors.transparent,
                strokeCap = StrokeCap.Round,
            )
        }
    }
}

@Composable
private fun CaloriesStatAdvice(
    dailySummary: DailySummary,
    dailyAdvice: DailyAdvice,
) {
    val adviceContainerColor = if (dailyAdvice.type == DailyAdviceType.OVER) {
        MaterialTheme.healthColors.warningContainer
    } else {
        MaterialTheme.healthColors.subtleContainer
    }
    val adviceContentColor = if (dailyAdvice.type == DailyAdviceType.OVER) {
        MaterialTheme.healthColors.onWarningContainer
    } else {
        MaterialTheme.healthColors.onSubtleContainer
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Balance,
                contentDescription = "Balance Icon",
                modifier = Modifier.size(MaterialTheme.dimensions.spacingDoubleExtraLarge),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.width(MaterialTheme.dimensions.spacingSmall))

            Column {
                Text(
                    text = stringResource(R.string.dashboard_energy_balance),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingExtraSmall))

                Text(

                    text = if (dailySummary.balanceCalories == 0) stringResource(R.string.dashboard_balance_equal)
                    else if (dailySummary.balanceCalories > 0) stringResource(R.string.dashboard_balance_positive)
                    else stringResource(R.string.dashboard_balance_negative),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(
                    R.string.dashboard_balance_signed_value,
                    dailySummary.balanceCalories,
                ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        AppCard(
            variant = AppCardVariant.OUTLINED,
            shape = MaterialTheme.shapes.large,
            containerColor = adviceContainerColor,
            contentColor = adviceContentColor,
            contentPadding = PaddingValues(MaterialTheme.dimensions.spacingSmall),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Advice Icon",
                    modifier = Modifier.size(MaterialTheme.dimensions.spacingDoubleExtraLarge),
                    tint = adviceContentColor,
                )

                Spacer(Modifier.width(MaterialTheme.dimensions.spacingMedium))

                Text(
                    text = when (dailyAdvice.type) {
                        DailyAdviceType.NEED_MORE -> stringResource(
                            R.string.dashboard_advice_need_more,
                            abs(dailyAdvice.differenceCalories),
                        )

                        DailyAdviceType.ON_TARGET -> stringResource(R.string.dashboard_advice_on_target)
                        DailyAdviceType.OVER -> stringResource(
                            R.string.dashboard_advice_over,
                            abs(dailyAdvice.differenceCalories),
                        )
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = adviceContentColor,
                )
            }
        }
    }
}
