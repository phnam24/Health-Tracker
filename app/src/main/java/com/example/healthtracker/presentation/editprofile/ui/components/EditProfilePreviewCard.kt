package com.example.healthtracker.presentation.editprofile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.TrackChanges
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
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.components.BmiCategoryBadge
import com.example.healthtracker.presentation.components.BmiScaleBar
import com.example.healthtracker.presentation.components.bmiAccentColor
import com.example.healthtracker.presentation.components.localizedLabel
import com.example.healthtracker.presentation.editprofile.state.EditProfileUiState
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors
import java.text.NumberFormat

@Composable
fun EditProfilePreviewCard(
    uiState: EditProfileUiState,
    modifier: Modifier = Modifier,
) {
    val locale = LocalConfiguration.current.locales[0]
    val target = uiState.tdeePreview?.target
    val formattedTarget = target?.let {
        NumberFormat.getIntegerInstance(locale).format(it)
    }
    val bmi = uiState.bmiPreview
    val categoryLabel = bmi?.category?.localizedLabel()
    val previewDescription = if (
        bmi != null && categoryLabel != null && formattedTarget != null
    ) {
        stringResource(
            R.string.cd_edit_profile_preview_values,
            bmi.value,
            categoryLabel,
            formattedTarget,
        )
    } else {
        stringResource(R.string.cd_edit_profile_preview)
    }

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {
                contentDescription = previewDescription
            },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
        ) {
            BmiPreview(uiState = uiState)

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ) {
                    Box(
                        modifier = Modifier.size(MaterialTheme.dimensions.optionIconContainerSize),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.TrackChanges,
                            contentDescription = null,
                            modifier = Modifier.size(MaterialTheme.dimensions.standardIconSize),
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.edit_profile_new_target),
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = formattedTarget?.let {
                        stringResource(R.string.edit_profile_target_value, it)
                    } ?: stringResource(R.string.edit_profile_preview_unavailable),
                    style = if (formattedTarget != null) {
                        MaterialTheme.typography.titleLarge
                    } else {
                        MaterialTheme.typography.bodyMedium
                    },
                    color = if (formattedTarget != null) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    fontWeight = if (formattedTarget != null) FontWeight.SemiBold else null,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun BmiPreview(uiState: EditProfileUiState) {
    val bmi = uiState.bmiPreview

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
        ) {
            Text(
                text = stringResource(R.string.settings_bmi_label),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            if (bmi != null) {
                Text(
                    text = stringResource(R.string.bmi_value, bmi.value),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = bmi.category.bmiAccentColor(),
                )
                BmiCategoryBadge(
                    category = bmi.category,
                    label = bmi.category.localizedLabel(),
                )
            } else {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.healthColors.subtleContainer,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ) {
                    Text(
                        text = stringResource(R.string.edit_profile_preview_unavailable),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.dimensions.spacingMedium,
                            vertical = MaterialTheme.dimensions.spacingSmall,
                        ),
                    )
                }
            }
        }

        BmiScaleBar(value = bmi?.value?.toFloat())
    }
}
