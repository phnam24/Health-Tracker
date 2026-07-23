package com.example.healthtracker.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

enum class AppCardVariant {
    ELEVATED,
    OUTLINED,
    SUBTLE,
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    variant: AppCardVariant = AppCardVariant.ELEVATED,
    shape: Shape = MaterialTheme.shapes.medium,
    containerColor: Color = when (variant) {
        AppCardVariant.ELEVATED,
        AppCardVariant.OUTLINED -> MaterialTheme.healthColors.cardContainer

        AppCardVariant.SUBTLE -> MaterialTheme.healthColors.subtleContainer
    },
    contentColor: Color = when (variant) {
        AppCardVariant.ELEVATED,
        AppCardVariant.OUTLINED -> MaterialTheme.healthColors.onCardContainer

        AppCardVariant.SUBTLE -> MaterialTheme.healthColors.onSubtleContainer
    },
    contentPadding: PaddingValues = PaddingValues(MaterialTheme.dimensions.cardPadding),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = when (variant) {
        AppCardVariant.ELEVATED -> MaterialTheme.dimensions.cardElevation
        AppCardVariant.OUTLINED,
        AppCardVariant.SUBTLE -> 0.dp
    },
    border: BorderStroke? = when (variant) {
        AppCardVariant.OUTLINED -> BorderStroke(
            width = MaterialTheme.dimensions.dividerThickness,
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        AppCardVariant.ELEVATED,
        AppCardVariant.SUBTLE -> null
    },
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border,
    ) {
        Box(modifier = Modifier.padding(contentPadding)) {
            content()
        }
    }
}
