package com.example.healthtracker.presentation.statistics.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.PrimaryButton
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun StatisticsRecentEmptyContent(
    modifier: Modifier = Modifier,
) {
    StatisticsEmptyContent(
        icon = Icons.Outlined.Restaurant,
        title = stringResource(R.string.statistics_recent_empty_title),
        message = stringResource(R.string.statistics_recent_empty_message),
        iconContainerColor = MaterialTheme.healthColors.caloriesConsumedContainer,
        iconContentColor = MaterialTheme.healthColors.onCaloriesConsumedContainer,
        modifier = modifier,
    )
}

@Composable
fun StatisticsWeekEmptyCard(
    isCurrentWeek: Boolean,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = MaterialTheme.dimensions.statisticsStateCardMinHeight),
    ) {
        StatisticsEmptyContent(
            icon = Icons.Outlined.CalendarMonth,
            title = stringResource(
                if (isCurrentWeek) R.string.statistics_week_empty_title
                else R.string.statistics_week_empty_history_title,
            ),
            message = stringResource(R.string.statistics_week_empty_message),
            iconContainerColor = MaterialTheme.healthColors.neutralIconContainer,
            iconContentColor = MaterialTheme.healthColors.onNeutralIconContainer,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun StatisticsFullEmptyState(
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = MaterialTheme.dimensions.statisticsStateCardMinHeight),
    ) {
        StatisticsEmptyContent(
            icon = Icons.Outlined.CalendarMonth,
            title = stringResource(R.string.statistics_empty_title),
            message = stringResource(R.string.statistics_empty_message),
            iconContainerColor = MaterialTheme.healthColors.neutralIconContainer,
            iconContentColor = MaterialTheme.healthColors.onNeutralIconContainer,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun StatisticsLoadFailedState(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = MaterialTheme.dimensions.statisticsStateCardMinHeight),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
        ) {
            Surface(
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
            ) {
                Icon(
                    imageVector = Icons.Outlined.CloudOff,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(MaterialTheme.dimensions.spacingLarge)
                        .size(MaterialTheme.dimensions.emptyStateIconSize),
                )
            }
            Text(
                text = stringResource(R.string.statistics_load_failed),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(R.string.statistics_load_failed_message),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            PrimaryButton(
                text = stringResource(R.string.statistics_retry),
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun StatisticsLoadingSkeleton(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.cardSpacing),
    ) {
        SkeletonCard(height = MaterialTheme.dimensions.chartHeight)
        SkeletonCard(height = MaterialTheme.dimensions.statisticsSelectorMinHeight)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
        ) {
            repeat(3) {
                SkeletonCard(
                    height = MaterialTheme.dimensions.statisticsSummaryCardMinHeight,
                    modifier = Modifier.weight(1f),
                )
            }
        }
        SkeletonCard(height = MaterialTheme.dimensions.chartHeight)
    }
}

@Composable
private fun StatisticsEmptyContent(
    icon: ImageVector,
    title: String,
    message: String,
    iconContainerColor: Color,
    iconContentColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimensions.spacingExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = iconContainerColor,
            contentColor = iconContentColor,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(MaterialTheme.dimensions.spacingLarge)
                    .size(MaterialTheme.dimensions.emptyStateIconSize),
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SkeletonCard(
    height: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.healthColors.subtleContainer,
    ) {
        Box(Modifier.fillMaxSize())
    }
}
