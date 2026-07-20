package com.example.healthtracker.presentation.diary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.helper.toLocalizedDateString
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.AppDatePickerDialog
import com.example.healthtracker.presentation.theme.dimensions
import java.time.LocalDate

@Composable
fun DiaryDateSelector(
    selectedDate: LocalDate,
    today: LocalDate,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    minDate: LocalDate? = null,
    enabled: Boolean = true
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val locale = LocalConfiguration.current.locales[0]
    val datePattern = stringResource(R.string.date_format_short)
    val weekdayPattern = stringResource(R.string.diary_weekday_format)
    val isToday = selectedDate == today
    val previousEnabled = enabled &&
            (minDate == null || selectedDate.isAfter(minDate))
    val nextEnabled = enabled && selectedDate.isBefore(today)
    val openDatePickerDescription = stringResource(R.string.cd_open_date_picker)

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = MaterialTheme.dimensions.textFieldHeight),
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        contentPadding = PaddingValues(0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = MaterialTheme.dimensions.textFieldHeight)
                .padding(horizontal = MaterialTheme.dimensions.spacingExtraSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                enabled = previousEnabled,
                onClick = onPreviousClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronLeft,
                    contentDescription = stringResource(R.string.cd_previous_day),
                    tint = if (previousEnabled) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
            }

            TextButton(
                enabled = enabled,
                onClick = { showDatePicker = true },
                modifier = Modifier.semantics {
                    contentDescription = openDatePickerDescription
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        MaterialTheme.dimensions.spacingSmall
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            MaterialTheme.dimensions.spacingExtraSmall
                        ),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (!isToday) {
                                Text(
                                    text = "${
                                        selectedDate.toLocalizedDateString(
                                            weekdayPattern,
                                            locale
                                        )
                                    }, ",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = if (isToday) {
                                    stringResource(R.string.common_today)
                                } else {
                                    selectedDate.toLocalizedDateString(datePattern, locale)
                                },
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        if (!isToday) {
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            ) {
                                Text(
                                    text = stringResource(R.string.diary_read_only),
                                    modifier = Modifier.padding(
                                        horizontal = MaterialTheme.dimensions.spacingSmall,
                                        vertical = MaterialTheme.dimensions.spacingExtraSmall,
                                    ),
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }
                        }
                    }
                }
            }

            IconButton(
                enabled = nextEnabled,
                onClick = onNextClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = stringResource(R.string.cd_next_day)
                )
            }
        }
    }

    AppDatePickerDialog(
        visible = showDatePicker,
        selectedDate = selectedDate,
        minDate = minDate,
        maxDate = today,
        onDateSelected = onDateSelected,
        onDismissRequest = { showDatePicker = false }
    )
}
