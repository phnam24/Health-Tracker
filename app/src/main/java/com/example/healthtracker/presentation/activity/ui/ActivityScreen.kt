package com.example.healthtracker.presentation.activity.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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
import com.example.healthtracker.domain.model.ActivityDay
import com.example.healthtracker.presentation.activity.state.ActivityUiState
import com.example.healthtracker.presentation.activity.ui.components.ActivityEmptyState
import com.example.healthtracker.presentation.activity.ui.components.ActivityEntryList
import com.example.healthtracker.presentation.activity.ui.components.ActivityLoadFailedState
import com.example.healthtracker.presentation.activity.ui.components.ActivityLoadingSkeleton
import com.example.healthtracker.presentation.activity.ui.components.ActivityTotalCard
import com.example.healthtracker.presentation.activity.ui.components.AddActivitySheet
import com.example.healthtracker.presentation.activity.viewmodel.ActivityEffect
import com.example.healthtracker.presentation.activity.viewmodel.ActivityEvent
import com.example.healthtracker.presentation.activity.viewmodel.ActivityViewModel
import com.example.healthtracker.presentation.components.AppTopBar
import com.example.healthtracker.presentation.diary.ui.components.DiaryDateSelector
import com.example.healthtracker.presentation.theme.dimensions
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
fun ActivityRoute(
    viewModel: ActivityViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val deletedMessage = stringResource(R.string.activity_deleted)
    val undoLabel = stringResource(R.string.common_undo)
    val addFailedMessage = stringResource(R.string.activity_add_failed)
    val deleteFailedMessage = stringResource(R.string.activity_delete_failed)
    val restoreFailedMessage = stringResource(R.string.activity_restore_failed)
    val profileRequiredMessage = stringResource(R.string.activity_profile_required)
    val addTodayOnlyMessage = stringResource(R.string.activity_add_today_only)

    LaunchedEffect(viewModel, snackbarHostState) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is ActivityEffect.ShowUndoDelete -> {
                    val result = snackbarHostState.showSnackbar(
                        message = deletedMessage,
                        actionLabel = undoLabel,
                        withDismissAction = true,
                        duration = SnackbarDuration.Long,
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(ActivityEvent.UndoDeleteClicked(effect.entry))
                    }
                }

                ActivityEffect.ShowAddFailed ->
                    snackbarHostState.showSnackbar(addFailedMessage)

                ActivityEffect.ShowDeleteFailed ->
                    snackbarHostState.showSnackbar(deleteFailedMessage)

                ActivityEffect.ShowRestoreFailed ->
                    snackbarHostState.showSnackbar(restoreFailedMessage)

                ActivityEffect.ShowProfileRequired ->
                    snackbarHostState.showSnackbar(profileRequiredMessage)

                ActivityEffect.ShowAddTodayOnly ->
                    snackbarHostState.showSnackbar(addTodayOnlyMessage)
            }
        }
    }

    ActivityScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    uiState: ActivityUiState,
    snackbarHostState: SnackbarHostState,
    onEvent: (ActivityEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val day = uiState.day
    val today = uiState.today
    val showFab = uiState.isToday &&
        day?.entries?.isNotEmpty() == true &&
        !uiState.isLoading &&
        !uiState.loadFailed &&
        !uiState.isMutating

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppTopBar(
                title = stringResource(R.string.activity_log_title),
                windowInsets = WindowInsets(0),
            )

            ActivityDateSelector(
                selectedDate = uiState.selectedDate,
                today = today,
                onEvent = onEvent,
            )

            when {
                uiState.isLoading && day == null -> {
                    ActivityLoadingSkeleton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = MaterialTheme.dimensions.screenPadding),
                    )
                }

                uiState.loadFailed && day == null -> {
                    ActivityLoadFailedState(
                        onRetry = { onEvent(ActivityEvent.RetryClicked) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = MaterialTheme.dimensions.screenPadding),
                    )
                }

                day != null -> {
                    ActivityContent(
                        day = day,
                        today = today ?: day.date,
                        isRefreshing = uiState.isLoading,
                        isMutating = uiState.isMutating,
                        onEvent = onEvent,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        if (showFab) {
            FloatingActionButton(
                onClick = { onEvent(ActivityEvent.AddActivityClicked) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(MaterialTheme.dimensions.screenPadding),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(R.string.cd_add_activity),
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }

    uiState.addSheet?.let { sheet ->
        AddActivitySheet(
            state = sheet,
            onEvent = onEvent,
            onDismiss = { onEvent(ActivityEvent.AddSheetDismissed) },
        )
    }
}

@Composable
private fun ActivityDateSelector(
    selectedDate: LocalDate?,
    today: LocalDate?,
    onEvent: (ActivityEvent) -> Unit,
) {
    if (selectedDate == null || today == null) return

    DiaryDateSelector(
        selectedDate = selectedDate,
        today = today,
        onPreviousClick = { onEvent(ActivityEvent.PreviousDayClicked) },
        onNextClick = { onEvent(ActivityEvent.NextDayClicked) },
        onDateSelected = { onEvent(ActivityEvent.DateSelected(it)) },
        modifier = Modifier.padding(
            horizontal = MaterialTheme.dimensions.screenPadding,
            vertical = MaterialTheme.dimensions.spacingExtraSmall,
        ),
    )
}

@Composable
private fun ActivityContent(
    day: ActivityDay,
    today: LocalDate,
    isRefreshing: Boolean,
    isMutating: Boolean,
    onEvent: (ActivityEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isToday = day.date == today

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.cardSpacing),
    ) {
        if (isRefreshing) {
            item(key = "refresh-progress") {
                LinearProgressIndicator(modifier = Modifier.fillParentMaxWidth())
            }
        }

        item(key = "activity-total") {
            ActivityTotalCard(day = day)
        }

        if (day.entries.isEmpty()) {
            item(key = "empty") {
                ActivityEmptyState(
                    isToday = isToday,
                    onAdd = { onEvent(ActivityEvent.AddActivityClicked) },
                )
            }
        } else {
            item(key = "section-title") {
                Text(
                    text = stringResource(
                        if (isToday) R.string.activity_section_today
                        else R.string.activity_section_day
                    ),
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            item(key = "activity-list") {
                ActivityEntryList(
                    entries = day.entries,
                    metByActivityTypeId = day.metByActivityTypeId,
                    canDelete = isToday && !isMutating,
                    onDelete = { onEvent(ActivityEvent.DeleteEntryClicked(it)) },
                )
            }
        }

        item(key = "bottom-space") {
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingExtraLarge))
        }
    }
}
