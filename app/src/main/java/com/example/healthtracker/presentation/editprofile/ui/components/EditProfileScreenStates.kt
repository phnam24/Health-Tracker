package com.example.healthtracker.presentation.editprofile.ui.components

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
fun EditProfileLoadingState(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(MaterialTheme.dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
    ) {
        repeat(5) { index ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth(if (index == 0) 1f else 0.9f)
                    .height(MaterialTheme.dimensions.textFieldHeight),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.healthColors.subtleContainer,
            ) {}
        }
    }
}

@Composable
fun EditProfileLoadFailedState(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(MaterialTheme.dimensions.screenPadding),
        verticalArrangement = Arrangement.Center,
    ) {
        AppCard(modifier = Modifier.fillMaxWidth()) {
            ScreenMessageState(
                icon = Icons.Outlined.CloudOff,
                iconContainerColor = MaterialTheme.colorScheme.errorContainer,
                iconContainerContentColor = MaterialTheme.colorScheme.onErrorContainer,
                title = stringResource(R.string.edit_profile_load_failed_title),
                message = stringResource(R.string.edit_profile_load_failed_message),
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
