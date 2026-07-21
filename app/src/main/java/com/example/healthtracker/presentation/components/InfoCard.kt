package com.example.healthtracker.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    containerColor: Color = MaterialTheme.healthColors.subtleContainer,
    contentColor: Color = MaterialTheme.healthColors.onSubtleContainer,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = containerColor,
        contentColor = contentColor,
    ) {
        Row(
            modifier = Modifier.padding(MaterialTheme.dimensions.cardPadding),
            verticalAlignment = Alignment.Top,
        ) {
            if (icon != null) {
                Surface(
                    modifier = Modifier.size(MaterialTheme.dimensions.optionIconContainerSize),
                    shape = CircleShape,
                    color = MaterialTheme.healthColors.neutralIconContainer,
                    contentColor = MaterialTheme.healthColors.onNeutralIconContainer,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(MaterialTheme.dimensions.standardIconSize),
                        )
                    }
                }

                Spacer(Modifier.width(MaterialTheme.dimensions.spacingMedium))
            }

            Column(
                modifier = Modifier.weight(1f),
                content = content,
            )
        }
    }
}
