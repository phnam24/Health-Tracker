package com.example.healthtracker.presentation.activity.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.presentation.activity.state.ActivityUiState
import com.example.healthtracker.presentation.activity.state.AddActivitySheetUiState
import com.example.healthtracker.presentation.activity.viewmodel.ActivityEvent
import com.example.healthtracker.presentation.activity.viewmodel.ActivityViewModel
import com.example.healthtracker.presentation.components.AppTopBar

@Composable
fun ActivityRoute(
    viewModel: ActivityViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            // map effect -> localized snackbar; Undo dispatch event
        }
    }

    ActivityScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    state: ActivityUiState,
    snackbarHostState: SnackbarHostState,
    onEvent: (ActivityEvent) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.activity_log_title),
                windowInsets = WindowInsets(0)
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (state.isToday && !state.isLoading && !state.loadFailed) {
                FloatingActionButton(
                    onClick = { onEvent(ActivityEvent.AddActivityClicked) }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = stringResource(R.string.cd_add_activity)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) { }
    }

    state.addSheet?.let { sheet ->
        AddActivitySheet(
            state = sheet,
            onEvent = onEvent,
            onDismiss = { onEvent(ActivityEvent.AddSheetDismissed) }
        )
    }
}

@Composable
fun AddActivitySheet(
    state: AddActivitySheetUiState,
    onEvent: (ActivityEvent) -> Unit,
    onDismiss: () -> Unit
) {

}