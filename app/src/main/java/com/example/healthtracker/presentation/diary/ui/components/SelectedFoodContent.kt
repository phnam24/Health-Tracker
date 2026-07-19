package com.example.healthtracker.presentation.diary.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.PrimaryButton
import com.example.healthtracker.presentation.diary.message
import com.example.healthtracker.presentation.diary.state.AddFoodSheetUiState
import com.example.healthtracker.presentation.diary.titleRes
import com.example.healthtracker.presentation.diary.viewmodel.DiaryEvent
import com.example.healthtracker.presentation.theme.dimensions

@Composable
fun SelectedFoodContent(
    state: AddFoodSheetUiState,
    onEvent: (DiaryEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val food = state.selectedFood ?: return
    val quantityError = state.quantityError?.message()
    val mealName = stringResource(state.targetMeal.titleRes())

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
    ) {
        AppCard(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
            border = BorderStroke(
                MaterialTheme.dimensions.dividerThickness,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
            ),
            shadowElevation = 0.dp,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    MaterialTheme.dimensions.spacingMedium
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.FormatListBulleted,
                    contentDescription = null,
                    modifier = Modifier.size(MaterialTheme.dimensions.largeIconSize),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = food.name,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = stringResource(
                            R.string.add_food_calories_per_unit,
                            food.caloriesPerUnit,
                            food.unit,
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        QuantitySelector(
            value = state.quantityInput,
            onValueChange = { onEvent(DiaryEvent.QuantityChanged(it)) },
            onDecrease = { onEvent(DiaryEvent.DecreaseQuantityClicked) },
            onIncrease = { onEvent(DiaryEvent.IncreaseQuantityClicked) },
            error = quantityError,
            enabled = !state.isSubmitting,
        )

        state.caloriesPreview?.let { calories ->
            AppCard(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                contentPadding = PaddingValues(MaterialTheme.dimensions.spacingMedium),
                shadowElevation = 0.dp,
            ) {
                Text(
                    text = stringResource(
                        R.string.add_food_calculation,
                        state.quantityInput,
                        food.caloriesPerUnit,
                        calories,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                )
            }
        }

        PrimaryButton(
            text = if (state.isSubmitting) {
                stringResource(R.string.add_food_submitting)
            } else {
                stringResource(R.string.diary_add_food_to_meal, mealName)
            },
            onClick = { onEvent(DiaryEvent.ConfirmAddFoodClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.caloriesPreview != null,
            loading = state.isSubmitting,
        )
    }
}
