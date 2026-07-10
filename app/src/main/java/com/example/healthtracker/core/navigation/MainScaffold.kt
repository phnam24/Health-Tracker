package com.example.healthtracker.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.healthtracker.R

enum class TopLevelTab(
    val key: NavKey,
    val labelRes: Int,
    val icon: ImageVector
) {
    DASHBOARD(Dashboard, R.string.tab_dashboard, Icons.Filled.Home),
    DIARY(Diary, R.string.tab_diary, Icons.Filled.Restaurant),
    ACTIVITY(ActivityLog, R.string.tab_activity, Icons.Filled.FitnessCenter),
    STATISTICS(Statistics, R.string.tab_statistics, Icons.Filled.BarChart),
    SETTINGS(Settings, R.string.tab_settings, Icons.Filled.Settings)
}

val topLevelKeys = TopLevelTab.entries.map { it.key }.toSet()

@Composable
fun MainScaffold(startKey: NavKey) {
    val backStack = rememberNavBackStack(startKey)
    val currentKey = backStack.lastOrNull()
    val showBottomBar = currentKey in topLevelKeys

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    TopLevelTab.entries.forEach { tab ->
                        NavigationBarItem(
                            selected = currentKey == tab.key,
                            onClick = { backStack.switchTab(tab.key) },
                            icon = { Icon(tab.icon, contentDescription = null) },
                            label = { Text(stringResource(tab.labelRes)) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        AppNavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
