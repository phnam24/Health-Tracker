package com.example.healthtracker.presentation.diary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.CustomFoodField
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.AppTextField
import com.example.healthtracker.presentation.components.PrimaryButton
import com.example.healthtracker.presentation.diary.message
import com.example.healthtracker.presentation.diary.state.AddFoodSheetUiState
import com.example.healthtracker.presentation.diary.viewmodel.DiaryEvent
import com.example.healthtracker.presentation.theme.dimensions

@Composable
fun CustomFoodFormContent(
    state: AddFoodSheetUiState,
    onEvent: (DiaryEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val form = state.customForm ?: return
    val nameError = form.errors[CustomFoodField.NAME]?.message(CustomFoodField.NAME)
    val caloriesError = form.errors[CustomFoodField.CALORIES]?.message(
        CustomFoodField.CALORIES
    )
    val unitError = form.errors[CustomFoodField.UNIT]?.message(CustomFoodField.UNIT)
    val quantityError = form.errors[CustomFoodField.QUANTITY]?.message(
        CustomFoodField.QUANTITY
    )
    val caloriesPerUnit = form.caloriesInput.trim().toIntOrNull()

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
    ) {
        AppTextField(
            value = form.nameViInput,
            onValueChange = { onEvent(DiaryEvent.CustomFoodNameViChanged(it)) },
            label = stringResource(R.string.custom_food_name_vi),
            placeholder = stringResource(R.string.custom_food_name_vi_hint),
            supportingText = nameError,
            isError = nameError != null,
            enabled = !state.isSubmitting,
        )

        AppTextField(
            value = form.nameEnInput,
            onValueChange = { onEvent(DiaryEvent.CustomFoodNameEnChanged(it)) },
            label = stringResource(R.string.custom_food_name_en),
            placeholder = stringResource(R.string.custom_food_name_en_hint),
            enabled = !state.isSubmitting,
        )

        AppTextField(
            value = form.caloriesInput,
            onValueChange = { onEvent(DiaryEvent.CustomFoodCaloriesChanged(it)) },
            label = stringResource(R.string.custom_food_calories),
            placeholder = stringResource(R.string.custom_food_calories_hint),
            supportingText = caloriesError,
            isError = caloriesError != null,
            enabled = !state.isSubmitting,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingContent = {
                Text(
                    text = stringResource(R.string.common_kcal),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
        )

        AppTextField(
            value = form.unitInput,
            onValueChange = { onEvent(DiaryEvent.CustomFoodUnitChanged(it)) },
            label = stringResource(R.string.custom_food_unit),
            placeholder = stringResource(R.string.custom_food_unit_hint),
            supportingText = unitError,
            isError = unitError != null,
            enabled = !state.isSubmitting,
        )

        QuantitySelector(
            value = form.quantityInput,
            onValueChange = { onEvent(DiaryEvent.CustomFoodQuantityChanged(it)) },
            onDecrease = { onEvent(DiaryEvent.DecreaseCustomFoodQuantityClicked) },
            onIncrease = { onEvent(DiaryEvent.IncreaseCustomFoodQuantityClicked) },
            error = quantityError,
            enabled = !state.isSubmitting,
        )

        if (form.caloriesPreview != null && caloriesPerUnit != null) {
            AppCard(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                contentPadding = PaddingValues(MaterialTheme.dimensions.spacingMedium),
                shadowElevation = 0.dp,
            ) {
                Text(
                    text = stringResource(
                        R.string.add_food_calculation,
                        form.quantityInput,
                        caloriesPerUnit,
                        form.caloriesPreview,
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
                stringResource(R.string.custom_food_save_and_add)
            },
            onClick = { onEvent(DiaryEvent.ConfirmCustomFoodClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isSubmitting &&
                    form.nameViInput.isNotBlank() &&
                    form.unitInput.isNotBlank() &&
                    form.caloriesPreview != null,
            loading = state.isSubmitting,
        )
    }
}
