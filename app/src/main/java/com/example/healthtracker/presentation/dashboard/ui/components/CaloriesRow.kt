package com.example.healthtracker.presentation.dashboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GolfCourse
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DailySummary
import com.example.healthtracker.presentation.theme.AppDimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun CaloriesSummaryRow(
    dailySummary: DailySummary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(AppDimensions.SpacingSmall),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CaloriesRowItem(
            title = stringResource(R.string.dashboard_goal),
            value = dailySummary.goalCalories ?: 0,
            icon = Icons.Filled.GolfCourse,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f)
        )

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    horizontal = AppDimensions.SpacingMediumLarge,
                    vertical = AppDimensions.SpacingSmall
                )
        )

        CaloriesRowItem(
            title = stringResource(R.string.dashboard_eaten),
            value = dailySummary.eatenCalories ?: 0,
            icon = Icons.Filled.Restaurant,
            tint = MaterialTheme.healthColors.caloriesConsumed,
            modifier = Modifier.weight(1f)
        )

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    horizontal = AppDimensions.SpacingMediumLarge,
                    vertical = AppDimensions.SpacingSmall
                )
        )

        CaloriesRowItem(
            title = stringResource(R.string.dashboard_burned),
            value = dailySummary.burnedCalories ?: 0,
            icon = Icons.Filled.LocalFireDepartment,
            tint = MaterialTheme.healthColors.caloriesBurned,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CaloriesRowItem(
    title: String,
    value: Int,
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingExtraSmall),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = tint,
            modifier = Modifier.size(AppDimensions.LargeIconSize)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}