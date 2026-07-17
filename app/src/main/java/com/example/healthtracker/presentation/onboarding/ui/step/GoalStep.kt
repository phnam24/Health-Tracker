package com.example.healthtracker.presentation.onboarding.ui.step

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.presentation.components.DescriptionText
import com.example.healthtracker.presentation.components.OptionCard
import com.example.healthtracker.presentation.onboarding.getUiData
import com.example.healthtracker.presentation.onboarding.resolveColor
import com.example.healthtracker.presentation.onboarding.state.OnboardingUiState
import com.example.healthtracker.presentation.onboarding.toStringRes
import com.example.healthtracker.presentation.onboarding.viewmodel.OnboardingEvent
import com.example.healthtracker.presentation.theme.AppDimensions
import com.example.healthtracker.presentation.theme.ControlShape

@Composable
fun GoalStep(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DescriptionText(
            label = stringResource(R.string.onboarding_goal_title),
            description = stringResource(R.string.onboarding_goal_description)
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingMediumLarge))

        GoalSelectSession(
            uiState = uiState,
            onEvent = { selectedGoal ->
                onEvent(OnboardingEvent.GoalSelected(selectedGoal))
            }
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingMediumLarge))

        uiState.errors[OnboardingField.GOAL]?.let {
            Spacer(modifier = Modifier.height(AppDimensions.SpacingMediumLarge))
            Text(
                text = stringResource(it.toStringRes()),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }

        uiState.tdeePreview?.let {
            TdeeResultSession(
                uiState = uiState
            )

            Spacer(modifier = Modifier.height(AppDimensions.SpacingMediumLarge))

            Text(
                text = stringResource(R.string.tdee_disclaimer),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(AppDimensions.SpacingMedium)
            )
        }
    }
}

@Composable
fun GoalSelectSession(
    uiState: OnboardingUiState,
    onEvent: (Goal) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall)
    ) {
        Goal.entries.forEach { goal ->
            val uiData = goal.getUiData()
            val tintColor = uiData.colorRole.resolveColor()

            OptionCard(
                title = stringResource(id = uiData.titleRes),
                description = stringResource(id = uiData.descriptionRes),
                selected = uiState.goal == goal,
                onClick = { onEvent(goal) },
                icon = uiData.icon,
                iconTintColor = tintColor,
                iconContainerColor = tintColor.copy(alpha = 0.15f),
            )
        }
    }
}

@Composable
fun TdeeResultSession(
    uiState: OnboardingUiState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = AppDimensions.FocusedBorderThickness,
                shape = ControlShape,
                color = MaterialTheme.colorScheme.outline
            )
            .padding(AppDimensions.SpacingMedium),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
    ) {
        Text(
            text = stringResource(R.string.tdee_preview_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.tdeePreview?.tdee.toString(),
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.width(AppDimensions.SpacingSmall))

            Text(
                text = stringResource(R.string.calories_per_day_unit),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        HorizontalDivider()

        CaloriesInfoRow(
            label = stringResource(R.string.tdee_bmr_label),
            value = stringResource(R.string.calories_value, uiState.tdeePreview?.bmr ?: 0)
        )

        CaloriesInfoRow(
            label = stringResource(R.string.tdee_maintenance_label),
            value = stringResource(R.string.calories_value, uiState.tdeePreview?.tdee ?: 0)
        )

        CaloriesInfoRow(
            label = stringResource(R.string.tdee_goal_adjustment_label),
            value = when (uiState.goal) {
                Goal.LOSE -> stringResource(
                    R.string.calories_adjustment_negative,
                    uiState.tdeePreview?.goalAdjustment ?: 0
                )

                Goal.MAINTAIN -> stringResource(R.string.calories_adjustment_none)
                Goal.GAIN -> stringResource(
                    R.string.calories_adjustment_positive,
                    uiState.tdeePreview?.goalAdjustment ?: 0
                )

                else -> stringResource(R.string.calories_adjustment_none)
            }
        )
    }
}

@Composable
fun CaloriesInfoRow(
    label: String,
    value: String
) {
    Row {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}