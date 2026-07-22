package com.example.healthtracker.presentation.settings.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.BmiCategory
import com.example.healthtracker.domain.model.BmiResult
import com.example.healthtracker.domain.model.UserProfile
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SettingsProfileCard(
    profile: UserProfile,
    age: Int,
    bmi: BmiResult,
    onEditProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
    ) {
        Column {
            ProfileSummary(
                profile = profile,
                age = age,
                modifier = Modifier.padding(MaterialTheme.dimensions.cardPadding),
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimensions.cardPadding),
                color = MaterialTheme.colorScheme.outlineVariant,
            )
            BmiSummary(
                bmi = bmi,
                modifier = Modifier.padding(MaterialTheme.dimensions.cardPadding),
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimensions.cardPadding),
                color = MaterialTheme.colorScheme.outlineVariant,
            )
            EditProfileRow(onClick = onEditProfile)
        }
    }
}

@Composable
private fun ProfileSummary(
    profile: UserProfile,
    age: Int,
    modifier: Modifier = Modifier,
) {
    val initial = profile.name.trim().firstOrNull()?.uppercaseChar()?.toString() ?: "?"
    val locale = LocalConfiguration.current.locales[0]
    val metadata = stringResource(
        R.string.settings_profile_metadata,
        age,
        profile.weightKg.localizedMeasurement(locale),
        profile.heightCm.localizedMeasurement(locale),
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
    ) {
        Surface(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = initial,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingExtraSmall),
        ) {
            Text(
                text = profile.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = metadata,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun BmiSummary(
    bmi: BmiResult,
    modifier: Modifier = Modifier,
) {
    val categoryLabel = bmi.category.label()
    val bmiDescription = stringResource(
        R.string.cd_settings_bmi,
        bmi.value,
        categoryLabel,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {
                contentDescription = bmiDescription
            },
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
    ) {
        Text(
            text = stringResource(R.string.settings_bmi_label),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
        ) {
            Text(
                text = stringResource(R.string.bmi_value, bmi.value),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = bmi.category.accentColor(),
            )
            BmiCategoryBadge(category = bmi.category, label = categoryLabel)
        }
        BmiScale(value = bmi.value.toFloat())
    }
}

@Composable
private fun BmiCategoryBadge(
    category: BmiCategory,
    label: String,
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = category.containerColor(),
        contentColor = category.onContainerColor(),
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
private fun BmiScale(
    value: Float,
    modifier: Modifier = Modifier,
) {
    val colors = listOf(
        MaterialTheme.healthColors.bmiUnderweight,
        MaterialTheme.healthColors.bmiNormal,
        MaterialTheme.healthColors.bmiOverweight,
        MaterialTheme.healthColors.bmiObese,
    )
    val progress = ((value - 14f) / (40f - 14f)).coerceIn(0f, 1f)
    val markerColor = MaterialTheme.colorScheme.onSurface

    Column(modifier = modifier.fillMaxWidth()) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimensions.spacingMedium),
        ) {
            val markerWidth = MaterialTheme.dimensions.spacingMedium
            androidx.compose.foundation.Canvas(
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
private fun EditProfileRow(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = MaterialTheme.dimensions.cardPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Outlined.Edit,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.width(MaterialTheme.dimensions.spacingMedium))
        Text(
            text = stringResource(R.string.settings_edit_profile),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun BmiCategory.label(): String = stringResource(
    when (this) {
        BmiCategory.UNDERWEIGHT -> R.string.bmi_category_underweight
        BmiCategory.NORMAL -> R.string.bmi_category_normal
        BmiCategory.OVERWEIGHT -> R.string.bmi_category_overweight
        BmiCategory.OBESE -> R.string.bmi_category_obese
    }
)

@Composable
private fun BmiCategory.accentColor(): Color = when (this) {
    BmiCategory.UNDERWEIGHT -> MaterialTheme.healthColors.bmiUnderweight
    BmiCategory.NORMAL -> MaterialTheme.healthColors.bmiNormal
    BmiCategory.OVERWEIGHT -> MaterialTheme.healthColors.bmiOverweight
    BmiCategory.OBESE -> MaterialTheme.healthColors.bmiObese
}

@Composable
private fun BmiCategory.containerColor(): Color = when (this) {
    BmiCategory.UNDERWEIGHT -> MaterialTheme.healthColors.caloriesBurnedContainer
    BmiCategory.NORMAL -> MaterialTheme.healthColors.successContainer
    BmiCategory.OVERWEIGHT -> MaterialTheme.healthColors.warningContainer
    BmiCategory.OBESE -> MaterialTheme.colorScheme.errorContainer
}

@Composable
private fun BmiCategory.onContainerColor(): Color = when (this) {
    BmiCategory.UNDERWEIGHT -> MaterialTheme.healthColors.onCaloriesBurnedContainer
    BmiCategory.NORMAL -> MaterialTheme.healthColors.onSuccessContainer
    BmiCategory.OVERWEIGHT -> MaterialTheme.healthColors.onWarningContainer
    BmiCategory.OBESE -> MaterialTheme.colorScheme.onErrorContainer
}

private fun Double.localizedMeasurement(locale: Locale): String =
    NumberFormat.getNumberInstance(locale).apply {
        minimumFractionDigits = 0
        maximumFractionDigits = 1
    }.format(this)
