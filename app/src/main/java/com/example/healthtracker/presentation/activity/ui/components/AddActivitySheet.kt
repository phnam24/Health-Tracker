package com.example.healthtracker.presentation.activity.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.model.AddActivityError
import com.example.healthtracker.presentation.activity.localizedName
import com.example.healthtracker.presentation.activity.state.AddActivitySheetUiState
import com.example.healthtracker.presentation.activity.viewmodel.ActivityEvent
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.AppTextField
import com.example.healthtracker.presentation.components.PrimaryButton
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivitySheet(
    state: AddActivitySheetUiState,
    onEvent: (ActivityEvent) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    ModalBottomSheet(
        onDismissRequest = { if (!state.isSubmitting) onDismiss() },
        sheetState = sheetState,
        sheetGesturesEnabled = false,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(
                    start = MaterialTheme.dimensions.screenPadding,
                    end = MaterialTheme.dimensions.screenPadding,
                    bottom = MaterialTheme.dimensions.screenPadding,
                ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
        ) {
            AddActivitySheetHeader(
                enabled = !state.isSubmitting,
                onClose = onDismiss,
            )

            if (state.selectedType == null) {
                ActivityTypeSearchContent(
                    state = state,
                    onEvent = onEvent,
                    modifier = Modifier.weight(1f, fill = false),
                )
            } else {
                ActivityDurationEditor(
                    state = state,
                    onEvent = onEvent,
                    modifier = Modifier.weight(1f, fill = false),
                )
            }
        }
    }
}

@Composable
private fun AddActivitySheetHeader(
    enabled: Boolean,
    onClose: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.add_activity_title),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
        )
        IconButton(enabled = enabled, onClick = onClose) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(R.string.common_close),
            )
        }
    }
}

@Composable
private fun ActivityTypeSearchContent(
    state: AddActivitySheetUiState,
    onEvent: (ActivityEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
    ) {
        AppTextField(
            value = state.query,
            onValueChange = { onEvent(ActivityEvent.SearchQueryChanged(it)) },
            label = stringResource(R.string.add_activity_search_hint),
            leadingIcon = Icons.Outlined.Search,
            enabled = !state.isSubmitting,
        )

        Text(
            text = stringResource(
                if (state.query.isBlank()) R.string.add_activity_suggestions
                else R.string.add_activity_results
            ),
            style = MaterialTheme.typography.titleMedium,
        )

        when {
            state.searchFailed -> SearchFailure(
                onRetry = { onEvent(ActivityEvent.RetrySearchClicked) },
            )

            state.isSearching && state.types.isEmpty() -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimensions.spacingExtraLarge),
                contentAlignment = Alignment.Center,
            ) { CircularProgressIndicator() }

            state.types.isEmpty() -> Text(
                text = stringResource(R.string.add_activity_search_empty),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimensions.spacingExtraLarge),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false),
            ) {
                if (state.isSearching) {
                    item(key = "search-progress") {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
                items(state.types, key = { it.id }) { type ->
                    ActivityTypeRow(
                        type = type,
                        onClick = { onEvent(ActivityEvent.ActivityTypeSelected(type)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityTypeRow(
    type: ActivityType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val displayName = type.localizedName(
        LocalConfiguration.current.locales[0].language,
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = MaterialTheme.dimensions.activityTypeRowMinHeight)
            .clickable(onClick = onClick)
            .padding(vertical = MaterialTheme.dimensions.spacingSmall),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier.size(MaterialTheme.dimensions.optionIconContainerSize),
            shape = CircleShape,
            color = MaterialTheme.healthColors.caloriesBurnedContainer,
            contentColor = MaterialTheme.healthColors.onCaloriesBurnedContainer,
        ) {
            Icon(
                imageVector = Icons.Outlined.DirectionsRun,
                contentDescription = null,
                modifier = Modifier.padding(MaterialTheme.dimensions.spacingSmall),
            )
        }
        Text(
            text = displayName,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
        )
        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.healthColors.neutralIconContainer,
            contentColor = MaterialTheme.healthColors.onNeutralIconContainer,
        ) {
            Text(
                text = stringResource(R.string.add_activity_met_value, type.met),
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.dimensions.activityMetBadgeHorizontalPadding,
                    vertical = MaterialTheme.dimensions.activityMetBadgeVerticalPadding,
                ),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
private fun ActivityDurationEditor(
    state: AddActivitySheetUiState,
    onEvent: (ActivityEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val type = requireNotNull(state.selectedType)
    val displayName = type.localizedName(
        LocalConfiguration.current.locales[0].language,
    )
    val errorText = activityDurationErrorText(state.durationError)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
    ) {
        AppCard(
            containerColor = MaterialTheme.healthColors.caloriesBurnedContainer,
            contentColor = MaterialTheme.healthColors.onCaloriesBurnedContainer,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
            ) {
                Surface(
                    modifier = Modifier.size(MaterialTheme.dimensions.optionIconContainerSize),
                    shape = CircleShape,
                    color = MaterialTheme.healthColors.caloriesBurned,
                    contentColor = MaterialTheme.healthColors.onCaloriesBurned,
                ) {
                    Icon(
                        Icons.Outlined.DirectionsRun,
                        contentDescription = null,
                        modifier = Modifier.padding(MaterialTheme.dimensions.spacingSmall),
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(displayName, style = MaterialTheme.typography.titleMedium)
                    Text(
                        stringResource(R.string.add_activity_met_value, type.met),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.healthColors.onCaloriesBurnedContainer,
                    )
                }
                TextButton(
                    enabled = !state.isSubmitting,
                    onClick = { onEvent(ActivityEvent.ChangeActivityTypeClicked) },
                ) { Text(stringResource(R.string.add_activity_change)) }
            }
        }

        AppTextField(
            value = state.durationText,
            onValueChange = { onEvent(ActivityEvent.DurationChanged(it)) },
            label = stringResource(R.string.add_activity_duration_label),
            placeholder = stringResource(R.string.add_activity_duration_hint),
            trailingContent = { Text(stringResource(R.string.unit_minutes)) },
            supportingText = errorText,
            isError = state.durationError != null,
            enabled = !state.isSubmitting,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
        ) {
            listOf(15, 30, 45, 60).forEach { minutes ->
                FilterChip(
                    selected = state.durationText == minutes.toString(),
                    onClick = { onEvent(ActivityEvent.QuickDurationSelected(minutes)) },
                    label = { Text(minutes.toString()) },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isSubmitting,
                    leadingIcon = if (state.durationText == minutes.toString()) {
                        { Icon(Icons.Outlined.Check, contentDescription = null) }
                    } else null,
                )
            }
        }

        AppCard(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.healthColors.caloriesBurnedContainer,
            contentColor = MaterialTheme.healthColors.onCaloriesBurnedContainer,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    modifier = Modifier.size(MaterialTheme.dimensions.activityPreviewIconSize),
                    shape = CircleShape,
                    color = MaterialTheme.healthColors.neutralIconContainer,
                    contentColor = MaterialTheme.healthColors.onNeutralIconContainer,
                ) {
                    Icon(
                        Icons.Outlined.DirectionsRun,
                        contentDescription = null,
                        modifier = Modifier.padding(MaterialTheme.dimensions.spacingMedium),
                    )
                }
                Column {
                    Text(
                        stringResource(R.string.add_activity_estimated_calories),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = state.estimatedCalories?.let {
                            stringResource(R.string.add_activity_estimated_value, it)
                        } ?: "—",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.healthColors.caloriesBurned,
                    )
                    Text(
                        stringResource(R.string.add_activity_estimated_formula, type.met),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.healthColors.onCaloriesBurnedContainer,
                    )
                }
            }
        }

        PrimaryButton(
            text = stringResource(
                if (state.isSubmitting) R.string.add_activity_submitting
                else R.string.add_activity_confirm
            ),
            onClick = { onEvent(ActivityEvent.ConfirmAddClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.estimatedCalories != null && state.durationError == null,
            loading = state.isSubmitting,
        )
    }
}

@Composable
private fun SearchFailure(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimensions.spacingExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.add_activity_search_failed),
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
        )
        TextButton(onClick = onRetry) {
            Text(stringResource(R.string.common_retry))
        }
    }
}

@Composable
private fun activityDurationErrorText(error: AddActivityError?): String? = when (error) {
    AddActivityError.DURATION_REQUIRED -> stringResource(R.string.error_duration_required)
    AddActivityError.DURATION_INVALID -> stringResource(R.string.error_duration_invalid)
    AddActivityError.DURATION_OUT_OF_RANGE -> stringResource(R.string.error_duration_out_of_range)
    else -> null
}
