package com.example.healthtracker.presentation.onboarding.ui.step

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.presentation.components.AppTextField
import com.example.healthtracker.presentation.components.FeatureHeader
import com.example.healthtracker.presentation.mapper.toStringRes
import com.example.healthtracker.presentation.onboarding.state.OnboardingUiState
import com.example.healthtracker.presentation.onboarding.viewmodel.OnboardingEvent
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun NameStep(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        NameStepHeader()

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingMediumLarge))

        ActivityColumn()

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingMediumLarge))

        NameStepInputField(
            uiState = uiState,
            onEvent = onEvent,
        )
    }
}

@Composable
private fun NameStepHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(MaterialTheme.dimensions.splashLogoSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painterResource(R.drawable.ic_healthtracker_logo),
                    contentDescription = "Health Tracker Logo",
                    modifier = Modifier.size(MaterialTheme.dimensions.splashLogoMediumSize),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingMediumLarge))

        Text(
            text = stringResource(R.string.onboarding_welcome_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingSmall))

        Text(
            text = stringResource(R.string.onboarding_welcome_description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun NameStepInputField(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        FeatureHeader(
            title = stringResource(R.string.onboarding_name_label),
            description = stringResource(R.string.onboarding_name_description),
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingMedium))

        AppTextField(
            value = uiState.name,
            onValueChange = { newValue ->
                onEvent(OnboardingEvent.NameChanged(newValue))
            },
            label = stringResource(R.string.onboarding_name_label),
            placeholder = stringResource(R.string.onboarding_name_placeholder),
            leadingIcon = Icons.Default.Person,
            isError = uiState.errors.isNotEmpty(),
            supportingText = uiState.errors[OnboardingField.NAME]?.let {
                stringResource(it.toStringRes())
            },
        )
    }
}

@Composable
private fun ActivityColumn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                shape = MaterialTheme.shapes.medium,
                width = MaterialTheme.dimensions.dividerThickness,
                color = MaterialTheme.colorScheme.outline,
            )
            .background(
                color = MaterialTheme.healthColors.cardContainer,
                shape = MaterialTheme.shapes.medium,
            ),
        verticalArrangement = Arrangement.Center,
    ) {
        ActivityRowIcon(
            label = stringResource(R.string.onboarding_benefit_tracking_title),
            description = stringResource(R.string.onboarding_benefit_tracking_description),
            icon = Icons.AutoMirrored.Filled.MenuBook,
        )

        HorizontalDivider(
            thickness = MaterialTheme.dimensions.dividerThickness,
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        ActivityRowIcon(
            label = stringResource(R.string.onboarding_benefit_insight_title),
            description = stringResource(R.string.onboarding_benefit_insight_description),
            icon = Icons.Filled.CoPresent,
        )

        HorizontalDivider(
            thickness = MaterialTheme.dimensions.dividerThickness,
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        ActivityRowIcon(
            label = stringResource(R.string.onboarding_benefit_goal_title),
            description = stringResource(R.string.onboarding_benefit_goal_description),
            icon = Icons.Filled.TrackChanges,
        )
    }
}

@Composable
private fun ActivityRowIcon(
    label: String,
    description: String,
    icon: ImageVector,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimensions.spacingMedium),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.dimensions.optionIconContainerSize)
                .clip(CircleShape)
                .background(MaterialTheme.healthColors.neutralIconContainer),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(MaterialTheme.dimensions.standardIconSize),
                tint = MaterialTheme.healthColors.onNeutralIconContainer,
            )
        }

        Spacer(modifier = Modifier.width(MaterialTheme.dimensions.spacingSmall))

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingExtraSmall))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
            )
        }
    }
}
