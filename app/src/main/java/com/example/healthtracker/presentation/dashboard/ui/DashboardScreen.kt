package com.example.healthtracker.presentation.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.PersonOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DailyAdvice
import com.example.healthtracker.domain.model.DailySummary
import com.example.healthtracker.domain.model.ThemeMode
import com.example.healthtracker.helper.toLocalizedDateString
import com.example.healthtracker.presentation.dashboard.state.DashboardUiState
import com.example.healthtracker.presentation.dashboard.ui.components.CaloriesProgressCircle
import com.example.healthtracker.presentation.dashboard.ui.components.CaloriesStatCard
import com.example.healthtracker.presentation.dashboard.ui.components.CaloriesSummaryRow
import com.example.healthtracker.presentation.dashboard.ui.components.DashboardHeader
import com.example.healthtracker.presentation.dashboard.ui.components.DashboardQuickActionSession
import com.example.healthtracker.presentation.dashboard.viewmodel.DashboardEffect
import com.example.healthtracker.presentation.dashboard.viewmodel.DashboardEvent
import com.example.healthtracker.presentation.dashboard.viewmodel.DashboardViewModel
import com.example.healthtracker.presentation.theme.HealthTrackerTheme
import com.example.healthtracker.presentation.theme.dimensions

@Composable
fun DashboardRoute(
    onDiaryNavigate: () -> Unit,
    onActivityNavigate: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                DashboardEffect.NavigateToDiary -> onDiaryNavigate()
                DashboardEffect.NavigateToActivity -> onActivityNavigate()
            }
        }
    }

    DashboardScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onEvent: (DashboardEvent) -> Unit
) {
    val summary = uiState.summary
    val advice = uiState.advice

    when {
        uiState.isLoading -> DashboardLoadingState()
        uiState.loadFailed -> DashboardLoadFailedState(
            onRetryClick = { onEvent(DashboardEvent.RetryClicked) }
        )

        summary == null || advice == null -> DashboardProfileMissingState()
        else -> DashboardContent(
            userName = uiState.userName,
            summary = summary,
            advice = advice,
            onEvent = onEvent
        )
    }
}

@Composable
private fun DashboardContent(
    userName: String,
    summary: DailySummary,
    advice: DailyAdvice,
    onEvent: (DashboardEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val locale = LocalConfiguration.current.locales[0]
    val datePattern = stringResource(R.string.dashboard_date_format)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(MaterialTheme.dimensions.spacingLarge),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DashboardHeader(
            userName = userName,
            dateString = summary.date.toLocalizedDateString(datePattern, locale),
        )

        DashboardCaloriesStatSession(
            dailySummary = summary,
            dailyAdvice = advice
        )

        DashboardQuickActionSession(
            onAddMealClick = { onEvent(DashboardEvent.AddMealClicked) },
            onAddActivityClick = { onEvent(DashboardEvent.AddActivityClicked) }
        )
    }
}

@Composable
private fun DashboardLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium)
        ) {
            CircularProgressIndicator()

            Text(
                text = stringResource(R.string.common_loading),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun DashboardLoadFailedState(
    onRetryClick: () -> Unit
) {
    DashboardMessageState(
        icon = Icons.Outlined.ErrorOutline,
        title = stringResource(R.string.dashboard_load_failed),
        actionLabel = stringResource(R.string.common_retry),
        onActionClick = onRetryClick
    )
}

@Composable
private fun DashboardProfileMissingState() {
    DashboardMessageState(
        icon = Icons.Outlined.PersonOff,
        title = stringResource(R.string.dashboard_profile_missing_title),
        message = stringResource(R.string.dashboard_profile_missing_message)
    )
}

@Composable
private fun DashboardMessageState(
    icon: ImageVector,
    title: String,
    message: String? = null,
    actionLabel: String? = null,
    onActionClick: () -> Unit = { },
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimensions.screenPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(MaterialTheme.dimensions.emptyStateIconSize),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            if (message != null) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            if (actionLabel != null) {
                TextButton(onClick = onActionClick) {
                    Text(text = actionLabel)
                }
            }
        }
    }
}

@Composable
fun DashboardCaloriesStatSession(
    dailySummary: DailySummary,
    dailyAdvice: DailyAdvice
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CaloriesProgressCircle(
            dailySummary = dailySummary
        )

        CaloriesSummaryRow(
            dailySummary = dailySummary
        )

        CaloriesStatCard(
            dailySummary = dailySummary,
            dailyAdvice = dailyAdvice
        )
    }
}

@Preview(name = "Dashboard - Loading", showBackground = true)
@Composable
private fun DashboardLoadingPreview() {
    HealthTrackerTheme(themeMode = ThemeMode.LIGHT) {
        Surface(modifier = Modifier.fillMaxSize()) {
            DashboardScreen(
                uiState = DashboardUiState(isLoading = true),
                onEvent = { }
            )
        }
    }
}

@Preview(name = "Dashboard - Load failed", showBackground = true)
@Composable
private fun DashboardLoadFailedPreview() {
    HealthTrackerTheme(themeMode = ThemeMode.LIGHT) {
        Surface(modifier = Modifier.fillMaxSize()) {
            DashboardScreen(
                uiState = DashboardUiState(
                    isLoading = false,
                    loadFailed = true,
                ),
                onEvent = { }
            )
        }
    }
}
