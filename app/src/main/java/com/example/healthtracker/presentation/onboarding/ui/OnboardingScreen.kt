package com.example.healthtracker.presentation.onboarding.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.PrimaryButton
import com.example.healthtracker.presentation.components.StepProgressBar
import com.example.healthtracker.presentation.onboarding.state.OnboardingUiState
import com.example.healthtracker.presentation.onboarding.ui.step.ActivityLevelStep
import com.example.healthtracker.presentation.onboarding.ui.step.BodyMetricsStep
import com.example.healthtracker.presentation.onboarding.ui.step.GoalStep
import com.example.healthtracker.presentation.onboarding.ui.step.NameStep
import com.example.healthtracker.presentation.onboarding.ui.step.PersonalInfoStep
import com.example.healthtracker.presentation.onboarding.viewmodel.OnboardingEffect
import com.example.healthtracker.presentation.onboarding.viewmodel.OnboardingEvent
import com.example.healthtracker.presentation.onboarding.viewmodel.OnboardingViewModel
import com.example.healthtracker.presentation.theme.AppDimensions
import com.example.healthtracker.presentation.theme.OnSurfaceVariantLight

@Composable
fun OnboardingRoute(
    onCompleted: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val saveFailedMessage = stringResource(R.string.error_save_profile)

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                OnboardingEffect.NavigateToDashboard -> onCompleted()
                OnboardingEffect.SaveFailed ->
                    snackbarHostState.showSnackbar(saveFailedMessage)
            }
        }
    }

    OnboardingScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun OnboardingScreen(
    uiState: OnboardingUiState,
    snackbarHostState: SnackbarHostState,
    onEvent: (OnboardingEvent) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
        ) {
            OnboardingHeader(
                current = uiState.currentStep.position,
                total = OnboardingStep.entries.size,
                showBack = true,
                onBack = { onEvent(OnboardingEvent.BackClicked) }
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = AppDimensions.SpacingMediumLarge)
            ) {
                when (uiState.currentStep) {
                    OnboardingStep.NAME -> NameStep(uiState, onEvent)
                    OnboardingStep.PERSONAL_INFO -> PersonalInfoStep(uiState, onEvent)
                    OnboardingStep.BODY_METRICS -> BodyMetricsStep(uiState, onEvent)
                    OnboardingStep.ACTIVITY_LEVEL -> ActivityLevelStep(uiState, onEvent)
                    OnboardingStep.GOAL -> GoalStep(uiState, onEvent)
                }
            }

            PrimaryButton(
                text = stringResource(
                    if (uiState.currentStep.isLast) R.string.action_start
                    else R.string.action_continue
                ),
                loading = uiState.isSaving,
                enabled = !uiState.isSaving,
                onClick = {
                    onEvent(
                        if (uiState.currentStep.isLast) OnboardingEvent.FinishClicked
                        else OnboardingEvent.NextClicked
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.SpacingMediumLarge)
            )
        }
    }
}

@Composable
fun OnboardingHeader(
    current: Int,
    total: Int,
    showBack: Boolean = true,
    onBack: (OnboardingEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppDimensions.SpacingMediumLarge),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBack) {
                IconButton(
                    onClick = { onBack }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back icon",
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.onboarding_step, current, total),
                style = MaterialTheme.typography.labelLarge,
                color = OnSurfaceVariantLight
            )
        }

        Spacer(modifier = Modifier.height(AppDimensions.SpacingMedium))

        StepProgressBar(
            currentStep = current,
            totalSteps = total,
        )
    }
}

