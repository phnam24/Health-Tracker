package com.example.healthtracker.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.healthtracker.domain.model.AppFontScale

internal fun healthTrackerTypography(fontScale: AppFontScale): Typography {
    val multiplier = fontScale.multiplier

    fun style(
        fontSize: Float,
        lineHeight: Float,
        fontWeight: FontWeight,
    ) = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = fontWeight,
        fontSize = (fontSize * multiplier).sp,
        lineHeight = (lineHeight * multiplier).sp,
    )

    return Typography(
        displayLarge = style(48f, 56f, FontWeight.Bold),
        displayMedium = style(40f, 48f, FontWeight.Bold),
        displaySmall = style(32f, 40f, FontWeight.Bold),
        headlineLarge = style(28f, 36f, FontWeight.SemiBold),
        headlineMedium = style(24f, 32f, FontWeight.SemiBold),
        headlineSmall = style(20f, 28f, FontWeight.SemiBold),
        titleLarge = style(22f, 28f, FontWeight.SemiBold),
        titleMedium = style(17f, 24f, FontWeight.SemiBold),
        titleSmall = style(14f, 20f, FontWeight.SemiBold),
        bodyLarge = style(16f, 24f, FontWeight.Normal),
        bodyMedium = style(14f, 20f, FontWeight.Normal),
        bodySmall = style(11f, 14f, FontWeight.Normal),
        labelLarge = style(14f, 20f, FontWeight.Medium),
        labelMedium = style(12f, 16f, FontWeight.Medium),
        labelSmall = style(11f, 14f, FontWeight.Medium),
    )
}