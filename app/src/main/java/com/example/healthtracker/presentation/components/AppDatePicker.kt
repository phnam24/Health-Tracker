package com.example.healthtracker.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.helper.toLocalDateFromPicker
import com.example.healthtracker.helper.toLocalizedDateString
import com.example.healthtracker.helper.toPickerMillis
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerField(
    errorText: String?,
    value: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    val locale = LocalConfiguration.current.locales[0]
    val datePattern = stringResource(R.string.date_format_short)
    val displayValue =
        value?.toLocalizedDateString(datePattern, locale)
            ?: stringResource(R.string.onboarding_birth_date_dialog_title)

    Box(modifier = modifier) {
        AppTextField(
            value = displayValue,
            onValueChange = { },
            label = label,
            readOnly = true,
            leadingIcon = Icons.Default.DateRange,
            trailingContent = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.cd_open_date_picker)
                )
            },
            isError = errorText?.isNotBlank() == true,
            supportingText = errorText,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    showDialog = true
                }
        )
    }

    AppDatePickerDialog(
        visible = showDialog,
        selectedDate = value,
        onDateSelected = onDateSelected,
        onDismissRequest = { showDialog = false }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    visible: Boolean,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null
) {
    if (!visible) return

    val selectableDates = remember(minDate, maxDate) {
        object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val date = utcTimeMillis.toLocalDateFromPicker()
                return (minDate == null || !date.isBefore(minDate)) &&
                        (maxDate == null || !date.isAfter(maxDate))
            }

            override fun isSelectableYear(year: Int): Boolean =
                (minDate == null || year >= minDate.year) &&
                        (maxDate == null || year <= maxDate.year)
        }
    }
    val initialDateMillis = selectedDate?.toPickerMillis()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis,
        initialDisplayedMonthMillis = initialDateMillis,
        selectableDates = selectableDates
    )
    val selectedValue = datePickerState.selectedDateMillis
        ?.toLocalDateFromPicker()
    val canConfirm = selectedValue != null &&
            (minDate == null || !selectedValue.isBefore(minDate)) &&
            (maxDate == null || !selectedValue.isAfter(maxDate))

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                enabled = canConfirm,
                onClick = {
                    selectedValue?.let(onDateSelected)
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.common_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.common_cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            modifier = modifier
        )
    }
}
