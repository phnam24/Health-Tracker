package com.example.healthtracker.presentation.onboarding.ui.step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.presentation.components.FeatureHeader
import com.example.healthtracker.presentation.components.OptionCard
import com.example.healthtracker.presentation.onboarding.getUiData
import com.example.healthtracker.presentation.onboarding.state.OnboardingUiState
import com.example.healthtracker.presentation.onboarding.toStringRes
import com.example.healthtracker.presentation.onboarding.viewmodel.OnboardingEvent
import com.example.healthtracker.presentation.theme.dimensions

@Composable
fun ActivityLevelStep(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        FeatureHeader(
            title = stringResource(R.string.onboarding_activity_title),
            description = stringResource(R.string.onboarding_activity_description)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingMediumLarge))

        ActivityLevelSelectSession(
            uiState = uiState,
            onEvent = { selectedLevel ->
                onEvent(OnboardingEvent.ActivityLevelSelected(selectedLevel))
            }
        )

        uiState.errors[OnboardingField.ACTIVITY_LEVEL]?.let {
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingMediumLarge))
            Text(
                text = stringResource(it.toStringRes()),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun ActivityLevelSelectSession(
    uiState: OnboardingUiState,
    onEvent: (ActivityLevel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall)
    ) {
        ActivityLevel.entries.forEach { level ->
            val uiData = level.getUiData()

            OptionCard(
                title = stringResource(id = uiData.titleRes),
                description = stringResource(id = uiData.descriptionRes),
                selected = uiState.activityLevel == level,
                onClick = { onEvent(level) },
                icon = uiData.icon,
            )
        }
    }
}


