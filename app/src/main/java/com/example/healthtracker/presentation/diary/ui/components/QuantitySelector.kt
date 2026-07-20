package com.example.healthtracker.presentation.diary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.MealQuantityRules
import com.example.healthtracker.presentation.components.AppTextField
import com.example.healthtracker.presentation.theme.dimensions

@Composable
fun QuantitySelector(
    value: String,
    onValueChange: (String) -> Unit,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
    enabled: Boolean = true,
) {
    val parsedQuantity = MealQuantityRules.parse(value)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
    ) {
        Text(
            text = stringResource(R.string.add_food_quantity),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.dimensions.spacingSmall
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FilledTonalIconButton(
                enabled = enabled &&
                        (parsedQuantity ?: MealQuantityRules.DEFAULT_VALUE) >
                        MealQuantityRules.STEPPER_MIN,
                onClick = onDecrease,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Remove,
                    contentDescription = stringResource(R.string.cd_decrease_quantity),
                )
            }

            AppTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                enabled = enabled,
                isError = error != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
            )

            FilledTonalIconButton(
                enabled = enabled &&
                        (parsedQuantity ?: MealQuantityRules.DEFAULT_VALUE) <
                            MealQuantityRules.MAX_VALUE,
                onClick = onIncrease,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(R.string.cd_increase_quantity),
                )
            }
        }

        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}
