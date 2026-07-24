package com.example.healthtracker.presentation.settings.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.AppFontScale
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.domain.model.ThemeMode
import com.example.healthtracker.domain.model.ThemePalette
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.theme.colorSchemeFor
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors

@Composable
fun SettingsAppearanceCard(
    settings: AppSettings,
    updateInProgress: Boolean,
    onThemeModeChange: (ThemeMode) -> Unit,
    onPaletteChange: (ThemePalette) -> Unit,
    onFontScaleChange: (AppFontScale) -> Unit,
    modifier: Modifier = Modifier,
) {
    val controlsEnabled = !updateInProgress
    val previewDarkTheme = when (settings.themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    AppCard(modifier = modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLarge),
        ) {
            AppearanceSettingLabel(text = stringResource(R.string.settings_theme_mode_label))
            ThemeModeSelector(
                selected = settings.themeMode,
                enabled = controlsEnabled,
                onSelected = onThemeModeChange,
            )

            SettingsDivider()

            AppearanceSettingLabel(text = stringResource(R.string.settings_palette_label))
            PaletteSelector(
                selected = settings.palette,
                enabled = controlsEnabled,
                darkTheme = previewDarkTheme,
                onSelected = onPaletteChange,
            )

            SettingsDivider()

            AppearanceSettingLabel(text = stringResource(R.string.settings_font_size_label))
            FontScaleSelector(
                selected = settings.fontScale,
                enabled = controlsEnabled,
                onSelected = onFontScaleChange,
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.healthColors.subtleContainer,
                contentColor = MaterialTheme.healthColors.onSubtleContainer,
            ) {
                Column(
                    modifier = Modifier.padding(MaterialTheme.dimensions.cardPadding),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
                ) {
                    Text(
                        text = stringResource(R.string.settings_font_preview_title),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = stringResource(R.string.settings_font_preview_text),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeModeSelector(
    selected: ThemeMode,
    enabled: Boolean,
    onSelected: (ThemeMode) -> Unit,
) {
    val options = ThemeMode.entries

    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        options.forEachIndexed { index, option ->
            SegmentedButton(
                selected = selected == option,
                onClick = {
                    if (option != selected) onSelected(option)
                },
                enabled = enabled,
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                label = {
                    Text(
                        text = option.label(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        }
    }
}

@Composable
private fun PaletteSelector(
    selected: ThemePalette,
    enabled: Boolean,
    darkTheme: Boolean,
    onSelected: (ThemePalette) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
                .alpha(if (enabled) 1f else 0.6f),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ThemePalette.entries.forEach { palette ->
                val isSelected = palette == selected
                val previewColors = colorSchemeFor(
                    palette = palette,
                    darkTheme = darkTheme,
                )
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.dimensions.minimumTouchTarget)
                        .selectable(
                            selected = isSelected,
                            enabled = enabled,
                            role = Role.RadioButton,
                            onClick = {
                                if (palette != selected) onSelected(palette)
                            },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    PalettePreview(
                        primaryColor = previewColors.primary,
                        onPrimaryColor = previewColors.onPrimary,
                        selected = isSelected,
                    )
                }
            }
        }
    }
}

@Composable
private fun PalettePreview(
    primaryColor: Color,
    onPrimaryColor: Color,
    selected: Boolean,
) {
    Surface(
        modifier = Modifier.size(MaterialTheme.dimensions.optionIconContainerSize),
        shape = CircleShape,
        color = primaryColor,
        contentColor = onPrimaryColor,
        border = BorderStroke(
            width = if (selected) {
                MaterialTheme.dimensions.focusedBorderThickness
            } else {
                MaterialTheme.dimensions.dividerThickness
            },
            color = if (selected) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.outlineVariant
            },
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    modifier = Modifier.size(MaterialTheme.dimensions.smallIconSize),
                )
            }
        }
    }
}

@Composable
private fun FontScaleSelector(
    selected: AppFontScale,
    enabled: Boolean,
    onSelected: (AppFontScale) -> Unit,
) {
    val options = AppFontScale.entries

    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        options.forEachIndexed { index, option ->
            SegmentedButton(
                selected = selected == option,
                onClick = {
                    if (option != selected) onSelected(option)
                },
                enabled = enabled,
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                label = {
                    Text(
                        text = option.label(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        }
    }
}

@Composable
private fun AppearanceSettingLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
}

@Composable
private fun ThemeMode.label(): String = stringResource(
    when (this) {
        ThemeMode.LIGHT -> R.string.settings_theme_light
        ThemeMode.DARK -> R.string.settings_theme_dark
        ThemeMode.SYSTEM -> R.string.settings_theme_system
    },
)


@Composable
private fun AppFontScale.label(): String = stringResource(
    when (this) {
        AppFontScale.SMALL -> R.string.settings_font_small
        AppFontScale.MEDIUM -> R.string.settings_font_medium
        AppFontScale.LARGE -> R.string.settings_font_large
    },
)
