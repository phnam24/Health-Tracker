package com.example.healthtracker.presentation.activity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityEntry
import com.example.healthtracker.presentation.activity.localizedName
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun ActivityEntryList(
    entries: List<ActivityEntry>,
    metByActivityTypeId: Map<Long, Double>,
    canDelete: Boolean,
    onDelete: (ActivityEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(),
    ) {
        Column {
            entries.forEachIndexed { index, entry ->
                ActivityEntryRow(
                    entry = entry,
                    met = metByActivityTypeId[entry.activityTypeId],
                    canDelete = canDelete,
                    onDelete = { onDelete(entry) },
                )
                if (index != entries.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            start = MaterialTheme.dimensions.activityEntryDividerInset,
                        ),
                        color = MaterialTheme.colorScheme.outlineVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityEntryRow(
    entry: ActivityEntry,
    met: Double?,
    canDelete: Boolean,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val displayName = entry.localizedName(
        LocalConfiguration.current.locales[0].language,
    )
    val rowDescription = stringResource(
        R.string.cd_activity_entry,
        displayName,
        entry.durationMinutes,
        entry.caloriesBurned,
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = MaterialTheme.dimensions.activityEntryRowMinHeight)
            .semantics(mergeDescendants = true) { stateDescription = rowDescription }
            .padding(
                start = MaterialTheme.dimensions.spacingMedium,
                end = MaterialTheme.dimensions.spacingSmall,
                top = MaterialTheme.dimensions.spacingSmall,
                bottom = MaterialTheme.dimensions.spacingSmall,
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier.size(MaterialTheme.dimensions.optionIconContainerSize),
            shape = CircleShape,
            color = MaterialTheme.healthColors.caloriesBurnedContainer,
            contentColor = MaterialTheme.healthColors.onCaloriesBurnedContainer,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.DirectionsRun,
                contentDescription = null,
                modifier = Modifier.padding(MaterialTheme.dimensions.spacingSmall),
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = displayName,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = if (met != null) {
                    stringResource(
                        R.string.activity_entry_metadata,
                        entry.durationMinutes,
                        met,
                    )
                } else {
                    stringResource(R.string.activity_entry_duration, entry.durationMinutes)
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Text(
            text = stringResource(R.string.activity_entry_calories, entry.caloriesBurned),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.healthColors.caloriesBurned,
        )

        if (canDelete) {
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Outlined.DeleteOutline,
                    contentDescription = stringResource(
                        R.string.cd_delete_activity,
                        displayName,
                    ),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
