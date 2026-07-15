package com.example.healthtracker.presentation.onboarding.ui.step

import androidx.compose.foundation.Image
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
import com.example.healthtracker.presentation.components.DescriptionText
import com.example.healthtracker.presentation.onboarding.state.OnboardingUiState
import com.example.healthtracker.presentation.onboarding.toStringRes
import com.example.healthtracker.presentation.onboarding.viewmodel.OnboardingEvent
import com.example.healthtracker.presentation.theme.AppDimensions
import com.example.healthtracker.presentation.theme.ControlShape
import com.example.healthtracker.presentation.theme.GreenPrimaryContainerLight
import com.example.healthtracker.presentation.theme.GreenPrimaryLight
import com.example.healthtracker.presentation.theme.OnBackgroundLight
import com.example.healthtracker.presentation.theme.OnSecondaryLight
import com.example.healthtracker.presentation.theme.OnSurfaceVariantDark
import com.example.healthtracker.presentation.theme.OnSurfaceVariantLight

@Composable
fun NameStep(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        NameStepHeader()

        Spacer(modifier = Modifier.height(AppDimensions.SpacingMediumLarge))

        ActivityColumn()

        Spacer(modifier = Modifier.height(AppDimensions.SpacingMediumLarge))

        NameStepInputField(
            uiState = uiState,
            onEvent = onEvent
        )
    }
}

@Composable
fun NameStepHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(AppDimensions.SplashLogoSize)
                    .clip(CircleShape)
                    .background(GreenPrimaryContainerLight),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(R.drawable.ic_healthtracker_logo),
                    contentDescription = "Health Tracker Logo",
                    modifier = Modifier.size(AppDimensions.SplashLogoMediumSize)
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimensions.SpacingMediumLarge))

        Text(
            text = stringResource(R.string.onboarding_welcome_title),
            style = MaterialTheme.typography.titleLarge,
            color = OnBackgroundLight
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall))

        Text(
            text = stringResource(R.string.onboarding_welcome_description),
            style = MaterialTheme.typography.bodyLarge,
            color = OnSurfaceVariantLight,
            maxLines = 2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NameStepInputField(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        DescriptionText(
            label = stringResource(R.string.onboarding_name_label),
            description = stringResource(R.string.onboarding_name_description)
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingMedium))

        AppTextField(
            value = uiState.name,
            onValueChange = { newValue ->
                onEvent(OnboardingEvent.NameChanged(newValue))
            },
            label = stringResource(R.string.onboarding_name_label),
            placeholder = stringResource(R.string.onboarding_name_placeholder),
            leadingIcon = Icons.Default.Person,
            isError = uiState.errors.isNotEmpty(),
            supportingText = uiState.errors[OnboardingField.NAME]?.let { stringResource(it.toStringRes()) }
        )
    }
}

@Composable
fun ActivityColumn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                shape = ControlShape,
                width = AppDimensions.DividerThickness,
                color = OnSurfaceVariantDark
            )
            .background(color = OnSecondaryLight),
        verticalArrangement = Arrangement.Center
    ) {
        ActivityRowIcon(
            label = stringResource(R.string.onboarding_benefit_tracking_title),
            description = stringResource(R.string.onboarding_benefit_tracking_description),
            icon = Icons.AutoMirrored.Filled.MenuBook
        )

        HorizontalDivider(thickness = AppDimensions.DividerThickness, color = OnSurfaceVariantDark)

        ActivityRowIcon(
            label = stringResource(R.string.onboarding_benefit_insight_title),
            description = stringResource(R.string.onboarding_benefit_insight_description),
            icon = Icons.Filled.CoPresent
        )

        HorizontalDivider(thickness = AppDimensions.DividerThickness, color = OnSurfaceVariantDark)

        ActivityRowIcon(
            label = stringResource(R.string.onboarding_benefit_goal_title),
            description = stringResource(R.string.onboarding_benefit_goal_description),
            icon = Icons.Filled.TrackChanges
        )
    }
}

@Composable
fun ActivityRowIcon(
    label: String,
    description: String,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppDimensions.SpacingMedium),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(AppDimensions.OptionIconContainerSize)
                .clip(CircleShape)
                .background(GreenPrimaryContainerLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(AppDimensions.StandardIconSize),
                tint = GreenPrimaryLight
            )
        }

        Spacer(modifier = Modifier.width(AppDimensions.SpacingSmall))

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                color = OnBackgroundLight,
            )

            Spacer(modifier = Modifier.height(AppDimensions.SpacingExtraSmall))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceVariantLight,
                maxLines = 2
            )
        }
    }
}