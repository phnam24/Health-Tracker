package com.example.healthtracker.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.BmiCategory
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun BmiCategoryBadge(
    category: BmiCategory,
    label: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = category.bmiContainerColor(),
        contentColor = category.bmiOnContainerColor(),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimensions.spacingMedium,
                vertical = MaterialTheme.dimensions.spacingSmall,
            ),
        )
    }
}

@Composable
fun BmiScaleBar(
    value: Float?,
    modifier: Modifier = Modifier,
) {
    val colors = listOf(
        MaterialTheme.healthColors.bmiUnderweight,
        MaterialTheme.healthColors.bmiNormal,
        MaterialTheme.healthColors.bmiOverweight,
        MaterialTheme.healthColors.bmiObese,
    )
    val progress = value?.let {
        ((it - BMI_SCALE_MIN) / (BMI_SCALE_MAX - BMI_SCALE_MIN)).coerceIn(0f, 1f)
    }
    val markerColor = MaterialTheme.colorScheme.onSurface

    Column(modifier = modifier.fillMaxWidth()) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimensions.spacingMedium),
        ) {
            if (progress != null) {
                val markerWidth = MaterialTheme.dimensions.spacingMedium
                Canvas(
                    modifier = Modifier
                        .size(markerWidth, MaterialTheme.dimensions.spacingSmall)
                        .offset(x = (maxWidth - markerWidth) * progress),
                ) {
                    val marker = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(size.width, 0f)
                        lineTo(size.width / 2f, size.height)
                        close()
                    }
                    drawPath(marker, markerColor, style = Fill)
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimensions.bmiIndicatorHeight)
                .clip(CircleShape)
                .background(Brush.horizontalGradient(colors)),
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            BmiThresholdLabel("18.5", Modifier.weight(1f))
            BmiThresholdLabel("25", Modifier.weight(1f))
            BmiThresholdLabel("30", Modifier.weight(1f))
        }
    }
}

@Composable
fun BmiCategory.localizedLabel(): String = stringResource(
    when (this) {
        BmiCategory.UNDERWEIGHT -> R.string.bmi_category_underweight
        BmiCategory.NORMAL -> R.string.bmi_category_normal
        BmiCategory.OVERWEIGHT -> R.string.bmi_category_overweight
        BmiCategory.OBESE -> R.string.bmi_category_obese
    },
)

@Composable
fun BmiCategory.bmiAccentColor(): Color = when (this) {
    BmiCategory.UNDERWEIGHT -> MaterialTheme.healthColors.bmiUnderweight
    BmiCategory.NORMAL -> MaterialTheme.healthColors.bmiNormal
    BmiCategory.OVERWEIGHT -> MaterialTheme.healthColors.bmiOverweight
    BmiCategory.OBESE -> MaterialTheme.healthColors.bmiObese
}

@Composable
private fun BmiThresholdLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun BmiCategory.bmiContainerColor(): Color = when (this) {
    BmiCategory.UNDERWEIGHT -> MaterialTheme.healthColors.caloriesBurnedContainer
    BmiCategory.NORMAL -> MaterialTheme.healthColors.successContainer
    BmiCategory.OVERWEIGHT -> MaterialTheme.healthColors.warningContainer
    BmiCategory.OBESE -> MaterialTheme.colorScheme.errorContainer
}

@Composable
private fun BmiCategory.bmiOnContainerColor(): Color = when (this) {
    BmiCategory.UNDERWEIGHT -> MaterialTheme.healthColors.onCaloriesBurnedContainer
    BmiCategory.NORMAL -> MaterialTheme.healthColors.onSuccessContainer
    BmiCategory.OVERWEIGHT -> MaterialTheme.healthColors.onWarningContainer
    BmiCategory.OBESE -> MaterialTheme.colorScheme.onErrorContainer
}

private const val BMI_SCALE_MIN = 14f
private const val BMI_SCALE_MAX = 40f
