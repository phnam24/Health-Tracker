package com.example.healthtracker.presentation.editprofile.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.AppTopBar
import com.example.healthtracker.presentation.components.AppTopBarNavigation
import com.example.healthtracker.presentation.components.AppTopBarTextAction
import com.example.healthtracker.presentation.editprofile.state.EditProfileUiState
import com.example.healthtracker.presentation.editprofile.ui.components.EditProfileContent
import com.example.healthtracker.presentation.editprofile.ui.components.EditProfileLoadFailedState
import com.example.healthtracker.presentation.editprofile.ui.components.EditProfileLoadingState
import com.example.healthtracker.presentation.editprofile.viewmodel.EditProfileEffect
import com.example.healthtracker.presentation.editprofile.viewmodel.EditProfileEvent
import com.example.healthtracker.presentation.editprofile.viewmodel.EditProfileViewModel
import com.example.healthtracker.presentation.theme.dimensions
import kotlinx.coroutines.flow.collectLatest
import java.text.NumberFormat

@Composable
fun EditProfileRoute(
    onBack: () -> Unit,
    onShowMessage: (String) -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDiscardDialog by rememberSaveable { mutableStateOf(false) }

    val currentOnBack by rememberUpdatedState(onBack)
    val currentOnShowMessage by rememberUpdatedState(onShowMessage)
    val saveFailedMessage by rememberUpdatedState(
        stringResource(R.string.edit_profile_save_failed),
    )
    val context by rememberUpdatedState(LocalContext.current)
    val locale by rememberUpdatedState(LocalConfiguration.current.locales[0])

    LaunchedEffect(viewModel, snackbarHostState) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                EditProfileEffect.ConfirmDiscard -> showDiscardDialog = true
                EditProfileEffect.NavigateBack -> currentOnBack()
                EditProfileEffect.SaveFailed -> {
                    snackbarHostState.showSnackbar(saveFailedMessage)
                }

                is EditProfileEffect.Saved -> {
                    val formattedTarget = NumberFormat
                        .getIntegerInstance(locale)
                        .format(effect.newTargetCalories)
                    currentOnShowMessage(
                        context.getString(R.string.edit_profile_updated, formattedTarget),
                    )
                    currentOnBack()
                }
            }
        }
    }

    BackHandler {
        viewModel.onEvent(EditProfileEvent.BackClicked)
    }

    EditProfileScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        showDiscardDialog = showDiscardDialog,
        onKeepEditing = { showDiscardDialog = false },
        onDiscard = {
            showDiscardDialog = false
            viewModel.onEvent(EditProfileEvent.DiscardConfirmed)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    uiState: EditProfileUiState,
    snackbarHostState: SnackbarHostState,
    onEvent: (EditProfileEvent) -> Unit,
    showDiscardDialog: Boolean,
    onKeepEditing: () -> Unit,
    onDiscard: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val savingDescription = stringResource(R.string.edit_profile_saving)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.edit_profile_title),
                navigation = AppTopBarNavigation(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_edit_profile_back),
                    onClick = { onEvent(EditProfileEvent.BackClicked) },
                    enabled = !uiState.isSaving,
                ),
                windowInsets = WindowInsets(0),
                actions = {
                    if (uiState.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(end = MaterialTheme.dimensions.spacingLarge)
                                .size(MaterialTheme.dimensions.standardIconSize)
                                .semantics {
                                    contentDescription = savingDescription
                                },
                            strokeWidth = MaterialTheme.dimensions.focusedBorderThickness,
                        )
                    } else {
                        AppTopBarTextAction(
                            text = stringResource(R.string.edit_profile_save),
                            onClick = { onEvent(EditProfileEvent.SaveClicked) },
                            enabled = uiState.canSave,
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            when {
                uiState.loadFailed -> {
                    EditProfileLoadFailedState(
                        onRetry = { onEvent(EditProfileEvent.RetryClicked) },
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                uiState.isLoading -> {
                    EditProfileLoadingState(modifier = Modifier.fillMaxSize())
                }

                else -> {
                    EditProfileContent(
                        uiState = uiState,
                        onEvent = onEvent,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }

    if (showDiscardDialog) {
        EditProfileDiscardDialog(
            onKeepEditing = onKeepEditing,
            onDiscard = onDiscard,
        )
    }
}

@Composable
private fun EditProfileDiscardDialog(
    onKeepEditing: () -> Unit,
    onDiscard: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onKeepEditing,
        title = { Text(stringResource(R.string.edit_profile_discard_title)) },
        text = { Text(stringResource(R.string.edit_profile_discard_message)) },
        dismissButton = {
            TextButton(onClick = onKeepEditing) {
                Text(stringResource(R.string.edit_profile_keep_editing))
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDiscard,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                ),
            ) {
                Text(stringResource(R.string.edit_profile_discard))
            }
        },
    )
}
