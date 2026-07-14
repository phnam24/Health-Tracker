package com.example.healthtracker.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.healthtracker.presentation.theme.AppDimensions
import com.example.healthtracker.presentation.theme.ControlShape

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        modifier = modifier.height(AppDimensions.PrimaryButtonHeight),
        shape = ControlShape,
        contentPadding = PaddingValues(horizontal = AppDimensions.SpacingMediumLarge),
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(AppDimensions.SpacingMediumLarge),
                strokeWidth = AppDimensions.FocusedBorderThickness,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            Spacer(Modifier.width(AppDimensions.SpacingSmall))
        }

        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
        )
    }
}