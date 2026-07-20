package com.example.healthtracker.presentation.diary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DiaryDay
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun CaloriesSummaryCard(
    day: DiaryDay,
    modifier: Modifier = Modifier,
) {
    val summaryDescription = stringResource(
        R.string.cd_diary_total_summary,
        day.totalCalories,
        day.foodCount,
        day.mealCount,
    )

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = MaterialTheme.dimensions.diaryTotalCardMinHeight)
            .clearAndSetSemantics { contentDescription = summaryDescription },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    modifier = Modifier.size(MaterialTheme.dimensions.diaryHeroIconSize),
                    shape = CircleShape,
                    color = MaterialTheme.healthColors.caloriesConsumedContainer,
                    contentColor = MaterialTheme.healthColors.caloriesConsumed,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        modifier = Modifier.padding(MaterialTheme.dimensions.spacingLarge),
                    )
                }

                Column {
                    Text(
                        text = stringResource(R.string.diary_day_total),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = stringResource(
                            R.string.diary_entry_calories,
                            day.totalCalories,
                        ),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.healthColors.caloriesConsumed,
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            Row(modifier = Modifier.fillMaxWidth()) {
                DiarySummaryMetric(
                    icon = { Icon(Icons.Outlined.Fastfood, contentDescription = null) },
                    value = stringResource(R.string.diary_food_count_value, day.foodCount),
                    modifier = Modifier.weight(1f),
                )
                DiarySummaryMetric(
                    icon = {
                        Icon(Icons.Outlined.FormatListBulleted, contentDescription = null)
                    },
                    value = stringResource(R.string.diary_meal_count_value, day.mealCount),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun DiarySummaryMetric(
    icon: @Composable () -> Unit,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier.size(MaterialTheme.dimensions.diarySummaryIconSize),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Box(
                modifier = Modifier.padding(MaterialTheme.dimensions.spacingSmall),
                contentAlignment = Alignment.Center,
            ) {
                icon()
            }
        }
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
