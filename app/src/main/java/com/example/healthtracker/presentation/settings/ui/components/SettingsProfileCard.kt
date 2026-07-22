package com.example.healthtracker.presentation.settings.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.BmiResult
import com.example.healthtracker.domain.model.UserProfile
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.BmiCategoryBadge
import com.example.healthtracker.presentation.components.BmiScaleBar
import com.example.healthtracker.presentation.components.bmiAccentColor
import com.example.healthtracker.presentation.components.localizedLabel
import com.example.healthtracker.presentation.theme.dimensions
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
    val categoryLabel = bmi.category.localizedLabel()
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
                color = bmi.category.bmiAccentColor(),
            )
            BmiCategoryBadge(category = bmi.category, label = categoryLabel)
        }
        BmiScaleBar(value = bmi.value.toFloat())
    }
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

private fun Double.localizedMeasurement(locale: Locale): String =
    NumberFormat.getNumberInstance(locale).apply {
        minimumFractionDigits = 0
        maximumFractionDigits = 1
    }.format(this)
