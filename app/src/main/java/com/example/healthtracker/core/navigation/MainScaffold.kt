package com.example.healthtracker.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.healthtracker.R
import kotlinx.coroutines.launch

enum class TopLevelTab(
    val key: NavKey,
    val labelRes: Int,
    val icon: ImageVector,
) {
    DASHBOARD(Dashboard, R.string.tab_dashboard, Icons.Filled.Home),
    DIARY(Diary, R.string.tab_diary, Icons.Filled.RoomService),
    ACTIVITY(ActivityLog, R.string.tab_activity, Icons.Filled.SportsMartialArts),
    STATISTICS(Statistics, R.string.tab_statistics, Icons.Filled.BarChart),
    SETTINGS(Settings, R.string.tab_settings, Icons.Filled.Settings),
}

val topLevelKeys = TopLevelTab.entries.map { it.key }.toSet()

@Composable
fun MainScaffold(
    startKey: NavKey,
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(startKey)
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val currentKey = backStack.lastOrNull()
    val showBottomBar = currentKey in topLevelKeys

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    TopLevelTab.entries.forEach { tab ->
                        NavigationBarItem(
                            selected = currentKey == tab.key,
                            onClick = { backStack.switchTab(tab.key) },
                            icon = { Icon(tab.icon, contentDescription = null) },
                            label = { Text(stringResource(tab.labelRes)) },
                        )
                    }
                }
            }
        },
    ) { paddingValues ->
        AppNavDisplay(
            backStack = backStack,
            onShowMessage = { message ->
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            },
            modifier = Modifier.padding(paddingValues),
        )
    }
}
