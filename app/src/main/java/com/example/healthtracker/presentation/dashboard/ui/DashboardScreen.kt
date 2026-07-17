package com.example.healthtracker.presentation.dashboard.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DailyAdvice
import com.example.healthtracker.domain.model.DailySummary
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
import com.example.healthtracker.presentation.theme.dimensions

@RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
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
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMediumLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DashboardHeader(
            userName = uiState.userName,
            dateString = uiState.date?.toLocalizedDateString(datePattern, locale) ?: "",
        )

        if (uiState.summary != null && uiState.advice != null) {
            DashboardCaloriesStatSession(
                dailySummary = uiState.summary,
                dailyAdvice = uiState.advice
            )
        }

        DashboardQuickActionSession(
            onAddMealClick = { onEvent(DashboardEvent.AddMealClicked) },
            onAddActivityClick = { onEvent(DashboardEvent.AddActivityClicked) }
        )
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
