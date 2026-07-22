package com.example.healthtracker.presentation.settings.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.PrimaryButton
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun SettingsLoadingSkeleton(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.cardSpacing),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.24f)
                .height(MaterialTheme.dimensions.spacingLarge),
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.healthColors.subtleContainer,
        ) {}
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimensions.chartHeight),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.healthColors.subtleContainer,
        ) {}
    }
}

@Composable
fun SettingsLoadFailedState(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        AppCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimensions.spacingExtraLarge),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
            ) {
                Surface(
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CloudOff,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(MaterialTheme.dimensions.spacingLarge)
                            .size(MaterialTheme.dimensions.emptyStateIconSize),
                    )
                }
                Text(
                    text = stringResource(R.string.settings_load_failed_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = stringResource(R.string.settings_load_failed_message),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
                PrimaryButton(
                    text = stringResource(R.string.action_retry),
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
