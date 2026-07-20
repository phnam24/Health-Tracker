package com.example.healthtracker.presentation.diary.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.MealEntry
import com.example.healthtracker.domain.model.MealSectionData
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.diary.titleRes
import com.example.healthtracker.presentation.diary.localizedName
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors
import java.text.NumberFormat

@Composable
fun DiaryMealSection(
    section: MealSectionData,
    mutationEnabled: Boolean,
    canAddFood: Boolean,
    onAddFood: () -> Unit,
    onDelete: (MealEntry) -> Unit,
) {
    val locale = LocalConfiguration.current.locales[0]

    AppCard(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = MaterialTheme.dimensions.minimumTouchTarget)
                    .padding(MaterialTheme.dimensions.cardPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        MaterialTheme.dimensions.spacingMedium
                    ),
                ) {
                    MealTypeIcon(mealType = section.type)
                    Text(
                        text = stringResource(section.type.titleRes()),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Text(
                    text = stringResource(
                        R.string.diary_entry_calories,
                        section.totalCalories,
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.healthColors.caloriesConsumed,
                    fontWeight = FontWeight.Medium,
                )
            }

            if (section.entries.isEmpty()) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimensions.spacingLarge),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                    )
                    Text(
                        text = stringResource(R.string.diary_empty_meal),
                        modifier = Modifier.padding(
                            start = MaterialTheme.dimensions.spacingSmall
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            } else {
                section.entries.forEach { entry ->
                    val displayName = entry.localizedName(locale.language)
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = MaterialTheme.dimensions.cardPadding,
                                top = MaterialTheme.dimensions.spacingSmall,
                                bottom = MaterialTheme.dimensions.spacingSmall,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = displayName,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = stringResource(
                                    R.string.diary_entry_quantity_value,
                                    NumberFormat.getNumberInstance(locale).format(entry.quantity),
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Text(
                            text = stringResource(
                                R.string.diary_entry_calories,
                                entry.calories,
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.healthColors.caloriesConsumed,
                        )
                        IconButton(
                            enabled = mutationEnabled,
                            onClick = { onDelete(entry) },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = stringResource(
                                    R.string.cd_delete_meal_entry,
                                    displayName,
                                ),
                            )
                        }
                    }
                }
            }

            if (canAddFood) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                TextButton(
                    onClick = onAddFood,
                    enabled = mutationEnabled,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(Icons.Outlined.Add, contentDescription = null)
                    Text(
                        text = stringResource(R.string.diary_add_food),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }
}

@Composable
fun MealTypeIcon(
    mealType: MealType,
    modifier: Modifier = Modifier,
) {
    val imageVector = when (mealType) {
        MealType.BREAKFAST -> Icons.Outlined.WbTwilight
        MealType.LUNCH -> Icons.Outlined.LightMode
        MealType.DINNER -> Icons.Outlined.DarkMode
        MealType.SNACK -> Icons.Outlined.Eco
    }

    Box(
        modifier = modifier
            .size(MaterialTheme.dimensions.optionIconContainerSize)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape,
            )
            .clearAndSetSemantics { },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(MaterialTheme.dimensions.standardIconSize),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}
