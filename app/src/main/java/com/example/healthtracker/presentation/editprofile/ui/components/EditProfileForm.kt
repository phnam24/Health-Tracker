package com.example.healthtracker.presentation.editprofile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.domain.model.OnboardingValidationError
import com.example.healthtracker.domain.model.OnboardingPolicy
import com.example.healthtracker.presentation.components.AppDatePickerField
import com.example.healthtracker.presentation.components.AppTextField
import com.example.healthtracker.presentation.editprofile.state.EditProfileUiState
import com.example.healthtracker.presentation.editprofile.viewmodel.EditProfileEvent
import com.example.healthtracker.presentation.mapper.toOptionUiData
import com.example.healthtracker.presentation.mapper.toStringRes
import com.example.healthtracker.presentation.theme.dimensions
import java.time.LocalDate

@Composable
fun EditProfileContent(
    uiState: EditProfileUiState,
    onEvent: (EditProfileEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val controlsEnabled = !uiState.isSaving

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        contentPadding = PaddingValues(MaterialTheme.dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
    ) {
        item(key = "name") {
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall)) {
                EditProfileFieldLabel(text = stringResource(R.string.onboarding_name_label))
                AppTextField(
                    value = uiState.name,
                    onValueChange = { onEvent(EditProfileEvent.NameChanged(it)) },
                    placeholder = stringResource(R.string.onboarding_name_placeholder),
                    supportingText = uiState.errorText(OnboardingField.NAME),
                    isError = OnboardingField.NAME in uiState.errors,
                    enabled = controlsEnabled,
                )
            }
        }

        item(key = "birth-date") {
            val today = LocalDate.now()
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall)) {
                EditProfileFieldLabel(text = stringResource(R.string.onboarding_birth_date_label))
                AppDatePickerField(
                    value = uiState.birthDate,
                    errorText = uiState.errorText(OnboardingField.BIRTH_DATE),
                    onDateSelected = { onEvent(EditProfileEvent.BirthDateSelected(it)) },
                    label = null,
                    enabled = controlsEnabled,
                    minDate = today
                        .minusYears((OnboardingPolicy.AGE_MAX + 1).toLong())
                        .plusDays(1),
                    maxDate = today.minusYears(OnboardingPolicy.AGE_MIN.toLong()),
                )
            }
        }

        item(key = "gender") {
            GenderSelector(
                selected = uiState.gender,
                errorText = uiState.errorText(OnboardingField.GENDER),
                enabled = controlsEnabled,
                onSelected = { onEvent(EditProfileEvent.GenderSelected(it)) },
            )
        }

        item(key = "body-metrics") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
                verticalAlignment = Alignment.Top,
            ) {
                MetricField(
                    label = stringResource(R.string.onboarding_weight_label),
                    value = uiState.weightInput,
                    unit = stringResource(R.string.unit_kilogram),
                    errorText = uiState.errorText(OnboardingField.WEIGHT),
                    enabled = controlsEnabled,
                    onValueChange = { onEvent(EditProfileEvent.WeightChanged(it)) },
                    modifier = Modifier.weight(1f),
                )
                MetricField(
                    label = stringResource(R.string.onboarding_height_label),
                    value = uiState.heightInput,
                    unit = stringResource(R.string.unit_centimeter),
                    errorText = uiState.errorText(OnboardingField.HEIGHT),
                    enabled = controlsEnabled,
                    onValueChange = { onEvent(EditProfileEvent.HeightChanged(it)) },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        item(key = "activity-level") {
            ActivityLevelDropdown(
                selected = uiState.activityLevel,
                errorText = uiState.errorText(OnboardingField.ACTIVITY_LEVEL),
                enabled = controlsEnabled,
                onSelected = { onEvent(EditProfileEvent.ActivityLevelSelected(it)) },
            )
        }

        item(key = "goal") {
            GoalSelector(
                selected = uiState.goal,
                errorText = uiState.errorText(OnboardingField.GOAL),
                enabled = controlsEnabled,
                onSelected = { onEvent(EditProfileEvent.GoalSelected(it)) },
            )
        }

        item(key = "preview-heading") {
            Text(
                text = stringResource(R.string.edit_profile_preview_section).uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.dimensions.spacingSmall),
            )
        }

        item(key = "preview") {
            EditProfilePreviewCard(uiState = uiState)
        }
    }
}

@Composable
private fun GenderSelector(
    selected: Gender?,
    errorText: String?,
    enabled: Boolean,
    onSelected: (Gender) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall)) {
        EditProfileFieldLabel(text = stringResource(R.string.onboarding_gender_label))
        val options = Gender.entries
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            options.forEachIndexed { index, gender ->
                SegmentedButton(
                    selected = selected == gender,
                    onClick = { onSelected(gender) },
                    enabled = enabled,
                    shape = SegmentedButtonDefaults.itemShape(index, options.size),
                    label = {
                        Text(
                            text = stringResource(
                                if (gender == Gender.MALE) {
                                    R.string.gender_male
                                } else {
                                    R.string.gender_female
                                },
                            ),
                        )
                    },
                )
            }
        }
        FieldErrorText(errorText)
    }
}

@Composable
private fun MetricField(
    label: String,
    value: String,
    unit: String,
    errorText: String?,
    enabled: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
    ) {
        EditProfileFieldLabel(text = label)
        AppTextField(
            value = value,
            onValueChange = onValueChange,
            trailingContent = {
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            supportingText = errorText,
            isError = errorText != null,
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
    }
}

@Composable
private fun ActivityLevelDropdown(
    selected: ActivityLevel?,
    errorText: String?,
    enabled: Boolean,
    onSelected: (ActivityLevel) -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val selectedData = selected?.toOptionUiData()

    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall)) {
        EditProfileFieldLabel(text = stringResource(R.string.edit_profile_activity_level))
        Box(modifier = Modifier.fillMaxWidth()) {
            AppTextField(
                value = selectedData?.let { stringResource(it.titleRes) }.orEmpty(),
                onValueChange = {},
                placeholder = stringResource(R.string.edit_profile_select_activity),
                readOnly = true,
                enabled = enabled,
                isError = errorText != null,
                supportingText = errorText ?: selectedData?.let {
                    stringResource(it.descriptionRes)
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                    )
                },
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(
                        enabled = enabled,
                        interactionSource = interactionSource,
                        indication = null,
                    ) { expanded = true },
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(),
            ) {
                ActivityLevel.entries.forEach { level ->
                    val uiData = level.toOptionUiData()
                    DropdownMenuItem(
                        text = {
                            Column {
                                Text(
                                    text = stringResource(uiData.titleRes),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                Text(
                                    text = stringResource(uiData.descriptionRes),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        },
                        onClick = {
                            expanded = false
                            onSelected(level)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun GoalSelector(
    selected: Goal?,
    errorText: String?,
    enabled: Boolean,
    onSelected: (Goal) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall)) {
        EditProfileFieldLabel(text = stringResource(R.string.edit_profile_goal))
        val options = Goal.entries
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            options.forEachIndexed { index, goal ->
                SegmentedButton(
                    selected = selected == goal,
                    onClick = { onSelected(goal) },
                    enabled = enabled,
                    shape = SegmentedButtonDefaults.itemShape(index, options.size),
                    label = {
                        Text(
                            text = goal.shortLabel(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                        )
                    },
                )
            }
        }
        FieldErrorText(errorText)
    }
}

@Composable
private fun EditProfileFieldLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
private fun FieldErrorText(errorText: String?) {
    if (errorText == null) return
    Text(
        text = errorText,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error,
    )
}

@Composable
private fun Goal.shortLabel(): String = stringResource(
    when (this) {
        Goal.LOSE -> R.string.edit_profile_goal_lose
        Goal.MAINTAIN -> R.string.edit_profile_goal_maintain
        Goal.GAIN -> R.string.edit_profile_goal_gain
    },
)

@Composable
private fun EditProfileUiState.errorText(field: OnboardingField): String? {
    val error = errors[field] ?: return null
    val resource = if (error == OnboardingValidationError.REQUIRED) {
        when (field) {
            OnboardingField.NAME -> R.string.error_name_required
            OnboardingField.BIRTH_DATE -> R.string.error_birth_date_required
            OnboardingField.GENDER -> R.string.error_gender_required
            OnboardingField.WEIGHT -> R.string.error_weight_required
            OnboardingField.HEIGHT -> R.string.error_height_required
            OnboardingField.ACTIVITY_LEVEL -> R.string.error_activity_required
            OnboardingField.GOAL -> R.string.error_goal_required
        }
    } else {
        error.toStringRes()
    }
    return stringResource(resource)
}
