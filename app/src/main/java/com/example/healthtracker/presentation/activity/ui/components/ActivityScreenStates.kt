package com.example.healthtracker.presentation.activity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.ScreenMessageState
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun ActivityEmptyState(
    isToday: Boolean,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ScreenMessageState(
        icon = Icons.Outlined.DirectionsRun,
        iconTint = MaterialTheme.colorScheme.outline,
        title = stringResource(
            if (isToday) R.string.activity_empty_title
            else R.string.activity_empty_history_title
        ),
        message = stringResource(
            if (isToday) R.string.activity_empty_message
            else R.string.activity_empty_history_message
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimensions.activityEmptyVerticalPadding),
        action = if (isToday) {
            {
                OutlinedButton(onClick = onAdd) {
                    Icon(
                        Icons.Outlined.AddCircleOutline,
                        contentDescription = null,
                    )
                    Text(stringResource(R.string.activity_add))
                }
            }
        } else {
            null
        },
    )
}

@Composable
fun ActivityLoadFailedState(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ScreenMessageState(
        icon = Icons.Outlined.CloudOff,
        iconTint = MaterialTheme.colorScheme.error,
        title = stringResource(R.string.activity_load_failed),
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = MaterialTheme.dimensions.activityErrorVerticalPadding),
        action = {
            Button(onClick = onRetry) {
                Icon(
                    Icons.Outlined.Refresh,
                    contentDescription = null,
                )
                Text(stringResource(R.string.common_retry))
            }
        },
    )
}

@Composable
fun ActivityLoadingSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
    ) {
        SkeletonBlock(height = MaterialTheme.dimensions.activityTotalCardMinHeight)
        repeat(3) {
            SkeletonBlock(height = MaterialTheme.dimensions.activityEntryRowMinHeight)
        }
    }
}

@Composable
private fun SkeletonBlock(height: Dp) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.healthColors.subtleContainer,
    ) { Box(Modifier.fillMaxWidth()) }
}
