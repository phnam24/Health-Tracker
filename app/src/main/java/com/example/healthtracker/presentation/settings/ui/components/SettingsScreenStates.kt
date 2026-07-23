package com.example.healthtracker.presentation.settings.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.PrimaryButton
import com.example.healthtracker.presentation.components.ScreenMessageState
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
            ScreenMessageState(
                icon = Icons.Outlined.CloudOff,
                iconContainerColor = MaterialTheme.colorScheme.errorContainer,
                iconContainerContentColor = MaterialTheme.colorScheme.onErrorContainer,
                title = stringResource(R.string.settings_load_failed_title),
                titleColor = MaterialTheme.colorScheme.error,
                message = stringResource(R.string.settings_load_failed_message),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimensions.spacingExtraLarge),
                action = {
                    PrimaryButton(
                        text = stringResource(R.string.action_retry),
                        onClick = onRetry,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            )
        }
    }
}
