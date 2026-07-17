package com.example.healthtracker.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.presentation.theme.dimensions

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
        modifier = modifier.heightIn(min = MaterialTheme.dimensions.PrimaryButtonHeight),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(horizontal = MaterialTheme.dimensions.SpacingMediumLarge),
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(MaterialTheme.dimensions.SpacingMediumLarge),
                strokeWidth = MaterialTheme.dimensions.FocusedBorderThickness,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            Spacer(Modifier.width(MaterialTheme.dimensions.SpacingSmall))
        }

        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 2,
            textAlign = TextAlign.Center,
        )
    }
}
