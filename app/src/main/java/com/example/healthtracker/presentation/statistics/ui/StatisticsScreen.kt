package com.example.healthtracker.presentation.statistics.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.StatisticsSnapshot
import com.example.healthtracker.presentation.components.AppTopBar
import com.example.healthtracker.presentation.statistics.state.StatisticsUiState
import com.example.healthtracker.presentation.statistics.ui.components.StatisticsFullEmptyState
import com.example.healthtracker.presentation.statistics.ui.components.StatisticsLoadFailedState
import com.example.healthtracker.presentation.statistics.ui.components.StatisticsLoadingSkeleton
import com.example.healthtracker.presentation.statistics.ui.components.StatisticsRecentIntakeCard
import com.example.healthtracker.presentation.statistics.ui.components.StatisticsWeekEmptyCard
import com.example.healthtracker.presentation.statistics.ui.components.StatisticsWeekSelector
import com.example.healthtracker.presentation.statistics.ui.components.StatisticsWeeklySummary
import com.example.healthtracker.presentation.statistics.ui.components.WeeklyTrendCard
import com.example.healthtracker.presentation.statistics.viewmodel.StatisticsEvent
import com.example.healthtracker.presentation.statistics.viewmodel.StatisticsViewModel
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.HealthTrackerTheme
import com.example.healthtracker.presentation.theme.ThemeMode
import java.time.LocalDate

@Composable
fun StatisticsRoute(
    viewModel: StatisticsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatisticsScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    uiState: StatisticsUiState,
    onEvent: (StatisticsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(R.string.statistics_title),
            subtitle = stringResource(R.string.statistics_last_seven_days),
            windowInsets = WindowInsets(0),
        )

        when {
            uiState.isLoading && uiState.snapshot == null -> {
                StatisticsLoadingSkeleton(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MaterialTheme.dimensions.screenPadding),
                )
            }

            uiState.loadFailed && uiState.snapshot == null -> {
                StatisticsFailedContent(
                    uiState = uiState,
                    onEvent = onEvent,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            uiState.snapshot != null -> {
                StatisticsContent(
                    snapshot = uiState.snapshot,
                    uiState = uiState,
                    onEvent = onEvent,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            else -> {
                StatisticsNoSnapshotContent(
                    uiState = uiState,
                    onEvent = onEvent,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun StatisticsContent(
    snapshot: StatisticsSnapshot,
    uiState: StatisticsUiState,
    onEvent: (StatisticsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = MaterialTheme.dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.cardSpacing),
    ) {
        item(key = "recent-intake") {
            StatisticsRecentIntakeCard(
                stats = snapshot.recentIntake,
                isEmpty = uiState.isRecentIntakeEmpty,
            )
        }

        item(key = "week-selector") {
            StatisticsWeekSelector(
                weekStart = snapshot.selectedWeek.startDate,
                weekEnd = snapshot.selectedWeek.endDate,
                isCurrentWeek = snapshot.selectedWeek.startDate == uiState.currentWeekStart,
                canGoNext = uiState.canGoNext,
                onPrevious = { onEvent(StatisticsEvent.PreviousWeekClicked) },
                onNext = { onEvent(StatisticsEvent.NextWeekClicked) },
                onCurrentWeek = { onEvent(StatisticsEvent.CurrentWeekClicked) },
            )
        }

        item(key = "week-heading") {
            Text(
                text = stringResource(R.string.statistics_week_overview).uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        if (uiState.isSelectedWeekEmpty) {
            item(key = "week-empty") {
                StatisticsWeekEmptyCard(
                    isCurrentWeek = snapshot.selectedWeek.startDate == uiState.currentWeekStart,
                )
            }
        } else {
            item(key = "week-summary") {
                StatisticsWeeklySummary(stats = snapshot.selectedWeek)
            }

            item(key = "week-trend") {
                WeeklyTrendCard(stats = snapshot.selectedWeek)
            }
        }

        item(key = "bottom-space") {
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingExtraLarge))
        }
    }
}

@Composable
private fun StatisticsFailedContent(
    uiState: StatisticsUiState,
    onEvent: (StatisticsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = MaterialTheme.dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.cardSpacing),
    ) {
        uiState.selectedWeekStart?.let { weekStart ->
            item(key = "disabled-week-selector") {
                StatisticsWeekSelector(
                    weekStart = weekStart,
                    weekEnd = weekStart.plusDays(6),
                    isCurrentWeek = weekStart == uiState.currentWeekStart,
                    canGoNext = false,
                    enabled = false,
                    onPrevious = {},
                    onNext = {},
                    onCurrentWeek = {},
                )
            }
        }

        item(key = "load-failed") {
            StatisticsLoadFailedState(
                onRetry = { onEvent(StatisticsEvent.RetryClicked) },
            )
        }
    }
}

@Composable
private fun StatisticsNoSnapshotContent(
    uiState: StatisticsUiState,
    onEvent: (StatisticsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val today = uiState.today ?: LocalDate.now()
    val weekStart = uiState.selectedWeekStart ?: today.minusDays(
        (today.dayOfWeek.value - 1).toLong(),
    )

    LazyColumn(
        modifier = modifier.padding(horizontal = MaterialTheme.dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.cardSpacing),
    ) {
        item(key = "recent-empty") {
            StatisticsRecentIntakeCard(
                startDate = today.minusDays(6),
                endDate = today,
            )
        }

        item(key = "week-selector") {
            StatisticsWeekSelector(
                weekStart = weekStart,
                weekEnd = weekStart.plusDays(6),
                isCurrentWeek = weekStart == uiState.currentWeekStart,
                canGoNext = uiState.canGoNext,
                onPrevious = { onEvent(StatisticsEvent.PreviousWeekClicked) },
                onNext = { onEvent(StatisticsEvent.NextWeekClicked) },
                onCurrentWeek = { onEvent(StatisticsEvent.CurrentWeekClicked) },
            )
        }

        item(key = "week-heading") {
            Text(
                text = stringResource(R.string.statistics_week_overview).uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        item(key = "full-empty") {
            StatisticsFullEmptyState()
        }
    }
}