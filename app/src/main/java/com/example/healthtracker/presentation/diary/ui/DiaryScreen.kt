package com.example.healthtracker.presentation.diary.ui

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
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DiaryDay
import com.example.healthtracker.presentation.components.AppDaySelector
import com.example.healthtracker.presentation.components.AppTopBar
import com.example.healthtracker.presentation.components.ScreenLoadingState
import com.example.healthtracker.presentation.components.ScreenMessageState
import com.example.healthtracker.presentation.diary.state.DiaryUiState
import com.example.healthtracker.presentation.diary.ui.components.CaloriesSummaryCard
import com.example.healthtracker.presentation.diary.ui.components.DiaryAddFoodSheet
import com.example.healthtracker.presentation.diary.ui.components.DiaryMealSection
import com.example.healthtracker.presentation.diary.viewmodel.DiaryEffect
import com.example.healthtracker.presentation.diary.viewmodel.DiaryEvent
import com.example.healthtracker.presentation.diary.viewmodel.DiaryViewModel
import com.example.healthtracker.presentation.theme.dimensions
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
fun DiaryRoute(
    viewModel: DiaryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val deletedMessage by rememberUpdatedState(stringResource(R.string.diary_entry_deleted))
    val undoLabel by rememberUpdatedState(stringResource(R.string.common_undo))
    val addFailedMessage by rememberUpdatedState(stringResource(R.string.add_food_failed))
    val customFoodFailedMessage by rememberUpdatedState(
        stringResource(R.string.custom_food_save_failed),
    )
    val deleteFailedMessage by rememberUpdatedState(stringResource(R.string.diary_delete_failed))
    val restoreFailedMessage by rememberUpdatedState(stringResource(R.string.diary_restore_failed))
    val addTodayOnlyMessage by rememberUpdatedState(
        stringResource(R.string.diary_add_today_only),
    )

    LaunchedEffect(viewModel, snackbarHostState) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is DiaryEffect.ShowUndoDelete -> {
                    val result = snackbarHostState.showSnackbar(
                        message = deletedMessage,
                        actionLabel = undoLabel,
                        withDismissAction = true,
                        duration = SnackbarDuration.Long,
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(DiaryEvent.UndoDeleteClicked(effect.entry))
                    }
                }

                DiaryEffect.ShowAddFailed ->
                    snackbarHostState.showSnackbar(addFailedMessage)

                DiaryEffect.ShowCustomFoodSaveFailed ->
                    snackbarHostState.showSnackbar(customFoodFailedMessage)

                DiaryEffect.ShowDeleteFailed ->
                    snackbarHostState.showSnackbar(deleteFailedMessage)

                DiaryEffect.ShowRestoreFailed ->
                    snackbarHostState.showSnackbar(restoreFailedMessage)

                DiaryEffect.ShowAddTodayOnly ->
                    snackbarHostState.showSnackbar(addTodayOnlyMessage)
            }
        }
    }

    DiaryScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(
    uiState: DiaryUiState,
    snackbarHostState: SnackbarHostState,
    onEvent: (DiaryEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val day = uiState.day

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppTopBar(
                title = stringResource(R.string.diary_title),
                windowInsets = WindowInsets(0),
            )

            when {
                uiState.isLoading && day == null -> {
                    ScreenLoadingState(modifier = Modifier.weight(1f))
                }

                uiState.loadFailed && day == null -> {
                    ScreenMessageState(
                        icon = Icons.Outlined.ErrorOutline,
                        title = stringResource(R.string.diary_load_failed),
                        modifier = Modifier
                            .weight(1f)
                            .padding(MaterialTheme.dimensions.screenPadding),
                        action = {
                            TextButton(
                                onClick = { onEvent(DiaryEvent.RetryClicked) },
                            ) {
                                Text(stringResource(R.string.common_retry))
                            }
                        },
                    )
                }

                day != null -> {
                    DiaryContent(
                        day = day,
                        today = uiState.today ?: day.date,
                        isMutating = uiState.isMutating,
                        onEvent = onEvent,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }

    uiState.addFoodSheet?.let { sheet ->
        DiaryAddFoodSheet(
            state = sheet,
            onEvent = onEvent,
        )
    }
}

@Composable
private fun DiaryContent(
    day: DiaryDay,
    today: LocalDate,
    isMutating: Boolean,
    onEvent: (DiaryEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.cardSpacing),
    ) {
        item(key = "date-selector") {
            AppDaySelector(
                selectedDate = day.date,
                today = today,
                onPreviousClick = { onEvent(DiaryEvent.PreviousDayClicked) },
                onNextClick = { onEvent(DiaryEvent.NextDayClicked) },
                onDateSelected = { onEvent(DiaryEvent.DateSelected(it)) },
                modifier = Modifier.padding(
                    top = MaterialTheme.dimensions.spacingExtraSmall,
                ),
            )
        }

        item(key = "day-total") {
            CaloriesSummaryCard(day = day)
        }

        day.sections.forEach { section ->
            item(key = "meal-${section.type}") {
                DiaryMealSection(
                    section = section,
                    mutationEnabled = !isMutating,
                    canAddFood = day.date == today,
                    onAddFood = {
                        onEvent(DiaryEvent.AddFoodClicked(section.type))
                    },
                    onDelete = {
                        onEvent(DiaryEvent.DeleteEntryClicked(it))
                    },
                )
            }
        }

        item(key = "bottom-space") {
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingExtraSmall))
        }
    }
}
