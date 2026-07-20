package com.example.healthtracker.presentation.diary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.AppTextField
import com.example.healthtracker.presentation.diary.state.AddFoodSheetUiState
import com.example.healthtracker.presentation.diary.viewmodel.DiaryEvent
import com.example.healthtracker.presentation.theme.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryAddFoodSheet(
    state: AddFoodSheetUiState,
    onEvent: (DiaryEvent) -> Unit,
) {
    val maxSheetHeight = LocalConfiguration.current.screenHeightDp.dp * 0.9f
    val showingCustomForm = state.customForm != null
    val showingSelectedFood = state.selectedFood != null && !showingCustomForm
    val canGoBack = showingCustomForm || showingSelectedFood

    ModalBottomSheet(
        modifier = Modifier.imePadding(),
        onDismissRequest = {
            if (!state.isSubmitting) onEvent(DiaryEvent.AddFoodDismissed)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxSheetHeight)
                .padding(
                    start = MaterialTheme.dimensions.screenPadding,
                    end = MaterialTheme.dimensions.screenPadding,
                    bottom = MaterialTheme.dimensions.screenPadding,
                ),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.dimensions.spacingMedium
            ),
        ) {
            AddFoodSheetHeader(
                title = stringResource(
                    if (showingCustomForm) {
                        R.string.custom_food_title
                    } else {
                        R.string.add_food_title
                    }
                ),
                canGoBack = canGoBack,
                enabled = !state.isSubmitting,
                onBack = {
                    onEvent(
                        if (showingCustomForm) {
                            DiaryEvent.CloseCustomFoodClicked
                        } else {
                            DiaryEvent.BackToFoodSearchClicked
                        }
                    )
                },
                onClose = { onEvent(DiaryEvent.AddFoodDismissed) },
            )

            when {
                showingCustomForm -> CustomFoodFormContent(
                    state = state,
                    onEvent = onEvent,
                    modifier = Modifier.weight(1f, fill = false),
                )

                showingSelectedFood -> SelectedFoodContent(
                    state = state,
                    onEvent = onEvent,
                    modifier = Modifier.weight(1f, fill = false),
                )

                else -> FoodSearchContent(
                    state = state,
                    onEvent = onEvent,
                    modifier = Modifier.weight(1f, fill = false),
                )
            }
        }
    }
}

@Composable
private fun AddFoodSheetHeader(
    title: String,
    canGoBack: Boolean,
    enabled: Boolean,
    onBack: () -> Unit,
    onClose: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (canGoBack) {
            IconButton(enabled = enabled, onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.content_description_back),
                )
            }
        }

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            textAlign = if (canGoBack) TextAlign.Center else TextAlign.Start,
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
private fun FoodSearchContent(
    state: AddFoodSheetUiState,
    onEvent: (DiaryEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
    ) {
        AppTextField(
            leadingIcon = Icons.Outlined.Search,
            value = state.query,
            onValueChange = { onEvent(DiaryEvent.SearchQueryChanged(it)) },
            enabled = !state.isSubmitting,
            singleLine = true,
            label = stringResource(R.string.add_food_search_hint),
        )

        Text(
            text = stringResource(
                if (state.query.isBlank()) {
                    R.string.add_food_popular
                } else {
                    R.string.add_food_results
                }
            ),
            style = MaterialTheme.typography.titleMedium,
        )

        when {
            state.isSearching -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimensions.spacingExtraLarge),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(MaterialTheme.dimensions.largeIconSize)
                )
            }

            state.searchFailed -> Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.add_food_search_failed),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                )
                TextButton(onClick = { onEvent(DiaryEvent.RetrySearchClicked) }) {
                    Text(stringResource(R.string.common_retry))
                }
            }

            state.results.isEmpty() -> Text(
                text = stringResource(R.string.add_food_search_empty),
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
                verticalArrangement = Arrangement.spacedBy(
                    MaterialTheme.dimensions.spacingExtraSmall
                ),
            ) {
                items(
                    items = state.results,
                    key = { it.id },
                ) { food ->
                    FoodSearchResultItem(
                        food = food,
                        enabled = !state.isSubmitting,
                        onClick = {
                            focusManager.clearFocus()
                            onEvent(DiaryEvent.FoodSelected(food))
                        },
                    )
                }
            }
        }

        TextButton(
            enabled = !state.isSubmitting,
            onClick = {
                focusManager.clearFocus()
                onEvent(DiaryEvent.OpenCustomFoodClicked)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
            )
            Text(
                text = stringResource(R.string.custom_food_open),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
