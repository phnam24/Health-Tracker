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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.PrimaryButton
import com.example.healthtracker.presentation.components.ScreenMessageState
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun StatisticsRecentEmptyContent(
    modifier: Modifier = Modifier,
) {
    ScreenMessageState(
        icon = Icons.Outlined.Restaurant,
        title = stringResource(R.string.statistics_recent_empty_title),
        message = stringResource(R.string.statistics_recent_empty_message),
        iconContainerColor = MaterialTheme.healthColors.caloriesConsumedContainer,
        iconContainerContentColor = MaterialTheme.healthColors.onCaloriesConsumedContainer,
        titleStyle = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(vertical = MaterialTheme.dimensions.spacingExtraLarge),
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
        ScreenMessageState(
            icon = Icons.Outlined.CalendarMonth,
            title = stringResource(
                if (isCurrentWeek) R.string.statistics_week_empty_title
                else R.string.statistics_week_empty_history_title,
            ),
            message = stringResource(R.string.statistics_week_empty_message),
            iconContainerColor = MaterialTheme.healthColors.neutralIconContainer,
            iconContainerContentColor = MaterialTheme.healthColors.onNeutralIconContainer,
            titleStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = MaterialTheme.dimensions.spacingExtraLarge),
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
        ScreenMessageState(
            icon = Icons.Outlined.CalendarMonth,
            title = stringResource(R.string.statistics_empty_title),
            message = stringResource(R.string.statistics_empty_message),
            iconContainerColor = MaterialTheme.healthColors.neutralIconContainer,
            iconContainerContentColor = MaterialTheme.healthColors.onNeutralIconContainer,
            titleStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = MaterialTheme.dimensions.spacingExtraLarge),
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
        ScreenMessageState(
            icon = Icons.Outlined.CloudOff,
            iconContainerColor = MaterialTheme.colorScheme.errorContainer,
            iconContainerContentColor = MaterialTheme.colorScheme.onErrorContainer,
            title = stringResource(R.string.statistics_load_failed),
            titleColor = MaterialTheme.colorScheme.error,
            message = stringResource(R.string.statistics_load_failed_message),
            action = {
                PrimaryButton(
                    text = stringResource(R.string.statistics_retry),
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
        )
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
