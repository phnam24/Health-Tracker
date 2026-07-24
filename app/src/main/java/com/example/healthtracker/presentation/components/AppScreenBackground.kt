package com.example.healthtracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.healthtracker.presentation.theme.rememberScreenBackgroundBrush

@Composable
fun AppScreenBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.background(rememberScreenBackgroundBrush()),
    ) {
        content()
    }
}