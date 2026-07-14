package com.example.healthtracker.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.healthtracker.presentation.theme.AppDimensions
import com.example.healthtracker.presentation.theme.OnBackgroundLight
import com.example.healthtracker.presentation.theme.OnSurfaceVariantLight

@Composable
fun DescriptionText(
    label: String,
    description: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            color = OnBackgroundLight
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = OnSurfaceVariantLight,
            maxLines = 2
        )
    }
}