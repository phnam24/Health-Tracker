package com.example.healthtracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay

@Composable
fun AppNavDisplay(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.goBack()
        },
        entryProvider = appEntryProvider(backStack),
        modifier = modifier
    )
}