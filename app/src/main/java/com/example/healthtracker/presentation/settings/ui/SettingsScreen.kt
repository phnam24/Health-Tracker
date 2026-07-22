package com.example.healthtracker.presentation.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.example.healthtracker.presentation.components.AppTopBar
import com.example.healthtracker.presentation.settings.state.SettingsUiState
import com.example.healthtracker.presentation.settings.ui.components.SettingsLoadFailedState
import com.example.healthtracker.presentation.settings.ui.components.SettingsLoadingSkeleton
import com.example.healthtracker.presentation.settings.ui.components.SettingsAppearanceCard
import com.example.healthtracker.presentation.settings.ui.components.SettingsProfileCard
import com.example.healthtracker.presentation.settings.viewmodel.SettingsEffect
import com.example.healthtracker.presentation.settings.viewmodel.SettingsEvent
import com.example.healthtracker.presentation.settings.viewmodel.SettingsViewModel
import com.example.healthtracker.presentation.theme.dimensions
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsRoute(
    onEditProfileNavigate: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val updateFailedMessage = stringResource(R.string.settings_preference_update_failed)

    LaunchedEffect(viewModel, snackbarHostState) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                SettingsEffect.NavigateToEditProfile -> onEditProfileNavigate()
                SettingsEffect.PreferenceUpdateFailed -> {
                    snackbarHostState.showSnackbar(updateFailedMessage)
                }
            }
        }
    }

    SettingsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    snackbarHostState: SnackbarHostState,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppTopBar(
                title = stringResource(R.string.settings_title),
                windowInsets = WindowInsets(0),
            )

            when {
                uiState.isLoading -> {
                    SettingsLoadingSkeleton(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = MaterialTheme.dimensions.screenPadding),
                    )
                }

                uiState.loadFailed ||
                    uiState.profile == null ||
                    uiState.age == null ||
                    uiState.bmi == null -> {
                    SettingsLoadFailedState(
                        onRetry = { onEvent(SettingsEvent.RetryClicked) },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = MaterialTheme.dimensions.screenPadding),
                    )
                }

                else -> {
                    SettingsContent(
                        uiState = uiState,
                        onEvent = onEvent,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun SettingsContent(
    uiState: SettingsUiState,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val profile = requireNotNull(uiState.profile)
    val bmi = requireNotNull(uiState.bmi)

    LazyColumn(
        modifier = modifier.padding(horizontal = MaterialTheme.dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.cardSpacing),
    ) {
        item(key = "profile-heading") {
            Text(
                text = stringResource(R.string.settings_profile_section).uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        item(key = "profile-card") {
            SettingsProfileCard(
                profile = profile,
                age = requireNotNull(uiState.age),
                bmi = bmi,
                onEditProfile = { onEvent(SettingsEvent.EditProfileClicked) },
            )
        }

        item(key = "appearance-heading") {
            Text(
                text = stringResource(R.string.settings_appearance_section).uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.dimensions.spacingMedium),
            )
        }

        item(key = "appearance-card") {
            SettingsAppearanceCard(
                settings = uiState.settings,
                updateInProgress = uiState.preferenceUpdateInProgress,
                onThemeModeChange = { onEvent(SettingsEvent.ThemeModeChanged(it)) },
                onPaletteChange = { onEvent(SettingsEvent.PaletteChanged(it)) },
                onFontScaleChange = { onEvent(SettingsEvent.FontScaleChanged(it)) },
            )
        }

        item(key = "bottom-space") {
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingExtraLarge))
        }
    }
}
