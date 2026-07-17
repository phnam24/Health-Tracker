package com.example.healthtracker.presentation.dashboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun DashboardQuickActionSession(
    onAddMealClick: () -> Unit,
    onAddActivityClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingLarge)
    ) {
        QuickActionButton(
            title = stringResource(R.string.dashboard_add_meal),
            icon = {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.dimensions.OptionIconContainerSize)
                        .clip(CircleShape)
                        .background(MaterialTheme.healthColors.caloriesConsumedContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Restaurant,
                        contentDescription = "Add meal icon",
                        tint = MaterialTheme.healthColors.caloriesConsumed,
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            onClick = onAddMealClick,
            modifier = Modifier.weight(1f)
        )

        QuickActionButton(
            title = stringResource(R.string.dashboard_add_activity),
            icon = {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.dimensions.OptionIconContainerSize)
                        .clip(CircleShape)
                        .background(MaterialTheme.healthColors.caloriesBurnedContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.DirectionsRun,
                        contentDescription = "Add activity icon",
                        tint = MaterialTheme.healthColors.caloriesBurned,
                        modifier = Modifier.size(MaterialTheme.dimensions.LargeIconSize)
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            onClick = onAddActivityClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun QuickActionButton(
    title: String,
    icon: @Composable () -> Unit,
    containerColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier.height(MaterialTheme.dimensions.ButtonCardHeight),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimensions.SpacingSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingSmall)
        ) {
            icon()

            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
