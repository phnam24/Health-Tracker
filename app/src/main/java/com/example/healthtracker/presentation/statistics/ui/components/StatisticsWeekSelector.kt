package com.example.healthtracker.presentation.statistics.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.healthtracker.R
import com.example.healthtracker.helper.toLocalizedDateString
import com.example.healthtracker.presentation.theme.dimensions
import java.time.LocalDate

@Composable
fun StatisticsWeekSelector(
    weekStart: LocalDate,
    weekEnd: LocalDate,
    isCurrentWeek: Boolean,
    canGoNext: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onCurrentWeek: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val range = statisticsRangeText(weekStart, weekEnd)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        WeekNavigationButton(
            icon = Icons.Outlined.ChevronLeft,
            contentDescription = stringResource(R.string.cd_statistics_previous_week),
            enabled = enabled,
            onClick = onPrevious,
        )

        Surface(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = MaterialTheme.dimensions.statisticsSelectorMinHeight)
                .semantics { contentDescription = range }
                .clickable(
                    enabled = enabled && !isCurrentWeek,
                    onClick = onCurrentWeek,
                ),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface,
            border = BorderStroke(
                MaterialTheme.dimensions.dividerThickness,
                if (enabled) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outlineVariant,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimensions.spacingSmall),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    MaterialTheme.dimensions.spacingExtraSmall,
                ),
            ) {
                if (isCurrentWeek) {
                    Text(
                        text = stringResource(R.string.statistics_current_week),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (enabled) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Text(
                    text = range,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (enabled) MaterialTheme.colorScheme.onSurface
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        WeekNavigationButton(
            icon = Icons.Outlined.ChevronRight,
            contentDescription = stringResource(R.string.cd_statistics_next_week),
            enabled = enabled && canGoNext,
            onClick = onNext,
        )
    }
}

@Composable
private fun WeekNavigationButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.sizeIn(
            minWidth = MaterialTheme.dimensions.minimumTouchTarget,
            minHeight = MaterialTheme.dimensions.statisticsSelectorMinHeight,
        ),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(
            MaterialTheme.dimensions.dividerThickness,
            MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        IconButton(
            onClick = onClick,
            enabled = enabled,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = if (enabled) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            )
        }
    }
}

@Composable
internal fun statisticsRangeText(
    startDate: LocalDate,
    endDate: LocalDate,
): String {
    val locale = LocalConfiguration.current.locales[0]
    val pattern = stringResource(R.string.date_format_short)
    return stringResource(
        R.string.statistics_date_range,
        startDate.toLocalizedDateString(pattern, locale),
        endDate.toLocalizedDateString(pattern, locale),
    )
}
