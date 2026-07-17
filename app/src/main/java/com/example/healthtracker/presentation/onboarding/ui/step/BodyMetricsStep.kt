package com.example.healthtracker.presentation.onboarding.ui.step

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.BmiCategory
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.presentation.components.AppTextField
import com.example.healthtracker.presentation.components.DescriptionText
import com.example.healthtracker.presentation.onboarding.state.OnboardingUiState
import com.example.healthtracker.presentation.onboarding.toStringRes
import com.example.healthtracker.presentation.onboarding.viewmodel.OnboardingEvent
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun BodyMetricsStep(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        DescriptionText(
            label = stringResource(R.string.onboarding_body_metrics_title),
            description = stringResource(R.string.onboarding_body_metrics_description)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingMediumLarge))

        WeightInputSession(
            uiState = uiState,
            onEvent = onEvent
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingMediumLarge))

        HeightInputSession(
            uiState = uiState,
            onEvent = onEvent
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingMediumLarge))

        BmiResultSession(
            uiState = uiState
        )
    }
}

@Composable
fun WeightInputSession(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit
) {
    BodyMetricsInput(
        value = uiState.weightInput,
        errorText = uiState.errors[OnboardingField.WEIGHT]?.let { stringResource(it.toStringRes()) },
        onValueChange = { newValue ->
            onEvent(OnboardingEvent.WeightChanged(newValue))
        },
        label = stringResource(R.string.onboarding_weight_label),
        unit = stringResource(R.string.unit_kilogram),
        leadingIcon = Icons.Default.MonitorWeight,
        suggestions = listOf("50", "55", "60", "65", "70")
    )
}

@Composable
fun HeightInputSession(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit
) {
    BodyMetricsInput(
        value = uiState.heightInput,
        errorText = uiState.errors[OnboardingField.HEIGHT]?.let { stringResource(it.toStringRes()) },
        onValueChange = { newValue ->
            onEvent(OnboardingEvent.HeightChanged(newValue))
        },
        label = stringResource(R.string.onboarding_height_label),
        unit = stringResource(R.string.unit_centimeter),
        leadingIcon = Icons.Default.Straighten,
        suggestions = listOf("150", "160", "165", "170", "175")
    )
}

@Composable
fun BmiResultSession(
    uiState: OnboardingUiState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = MaterialTheme.dimensions.focusedBorderThickness,
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.outline
            )
            .padding(MaterialTheme.dimensions.spacingMedium),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.bmi_preview_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingSmall))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.bmi_value, uiState.bmiPreview?.value ?: 0.0),
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.width(MaterialTheme.dimensions.spacingMedium))

            Text(
                text = when (uiState.bmiPreview?.category) {
                    BmiCategory.UNDERWEIGHT -> stringResource(R.string.bmi_category_underweight)
                    BmiCategory.NORMAL -> stringResource(R.string.bmi_category_normal)
                    BmiCategory.OVERWEIGHT -> stringResource(R.string.bmi_category_overweight)
                    BmiCategory.OBESE -> stringResource(R.string.bmi_category_obese)
                    else -> ""
                },
                style = MaterialTheme.typography.titleMedium,
                color = when (uiState.bmiPreview?.category) {
                    BmiCategory.UNDERWEIGHT -> MaterialTheme.healthColors.bmiUnderweight
                    BmiCategory.NORMAL -> MaterialTheme.healthColors.bmiNormal
                    BmiCategory.OVERWEIGHT -> MaterialTheme.healthColors.bmiOverweight
                    BmiCategory.OBESE -> MaterialTheme.healthColors.bmiObese
                    null -> MaterialTheme.colorScheme.onSurface
                }
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingSmall))

        Text(
            text = when (uiState.bmiPreview?.category) {
                BmiCategory.UNDERWEIGHT -> stringResource(R.string.bmi_underweight_description)
                BmiCategory.NORMAL -> stringResource(R.string.bmi_normal_description)
                BmiCategory.OVERWEIGHT -> stringResource(R.string.bmi_overweight_description)
                BmiCategory.OBESE -> stringResource(R.string.bmi_obese_description)
                else -> ""
            },
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingSmall))

        BmiScaleBar(
            bmi = uiState.bmiPreview?.value?.toFloat() ?: 10f
        )
    }
}

@Composable
fun BodyMetricsInput(
    value: String,
    errorText: String?,
    onValueChange: (String) -> Unit,
    label: String,
    unit: String,
    leadingIcon: ImageVector,
    suggestions: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        AppTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            leadingIcon = leadingIcon,
            trailingContent = {
                Text(
                    text = unit,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            isError = !errorText.isNullOrBlank(),
            supportingText = errorText,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacingSmall))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium)
        ) {
            suggestions.forEach { suggestion ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(MaterialTheme.dimensions.optionIconContainerSize)
                        .clip(MaterialTheme.shapes.medium)
                        .border(
                            width = MaterialTheme.dimensions.dividerThickness,
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.outline
                        )
                        .clickable {
                            onValueChange(suggestion)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = suggestion)
                }
            }
        }
    }
}

@Composable
fun BmiScaleBar(
    bmi: Float,
    modifier: Modifier = Modifier
) {
    val gradientColors = listOf(
        MaterialTheme.healthColors.bmiUnderweight,
        MaterialTheme.healthColors.bmiNormal,
        MaterialTheme.healthColors.bmiOverweight,
        MaterialTheme.healthColors.bmiObese,
    )

    val minBmi = 16f
    val maxBmi = 34f

    val percentage = ((bmi - minBmi) / (maxBmi - minBmi)).coerceIn(0f, 1f)

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val indicatorOffset = (maxWidth * percentage) - MaterialTheme.dimensions.spacingMedium

            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Indicator",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(MaterialTheme.dimensions.spacingDoubleExtraLarge)
                    .offset(x = indicatorOffset)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimensions.spacingSmall)
                .clip(CircleShape)
                .background(Brush.horizontalGradient(gradientColors))
        )
    }
}