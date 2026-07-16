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
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
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
import com.example.healthtracker.presentation.dashboard.viewmodel.DashboardEvent
import com.example.healthtracker.presentation.theme.AppDimensions
import com.example.healthtracker.presentation.theme.BluePrimaryLight
import com.example.healthtracker.presentation.theme.CaloriesConsumed

@Composable
fun DashboardQuickActionSession(
    onAddMealClick: () -> Unit,
    onAddActivityClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
    ) {
        QuickActionButton(
            title = stringResource(R.string.dashboard_add_meal),
            icon = {
                Box(
                    modifier = Modifier
                        .size(AppDimensions.OptionIconContainerSize)
                        .clip(CircleShape)
                        .background(CaloriesConsumed.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Restaurant,
                        contentDescription = "Add meal icon",
                        tint = CaloriesConsumed,
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
                        .size(AppDimensions.OptionIconContainerSize)
                        .clip(CircleShape)
                        .background(BluePrimaryLight.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.DirectionsRun,
                        contentDescription = "Add activity icon",
                        tint = BluePrimaryLight,
                        modifier = Modifier.size(AppDimensions.LargeIconSize)
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
    OutlinedCard (
        onClick = onClick,
        modifier = modifier.height(AppDimensions.ButtonCardHeight),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimensions.SpacingSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall)
        ) {
            icon()

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}