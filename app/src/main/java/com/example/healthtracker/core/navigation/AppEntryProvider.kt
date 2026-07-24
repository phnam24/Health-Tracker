package com.example.healthtracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import com.example.healthtracker.presentation.PlaceholderScreen
import com.example.healthtracker.presentation.activity.ui.ActivityRoute
import com.example.healthtracker.presentation.dashboard.ui.DashboardRoute
import com.example.healthtracker.presentation.diary.ui.DiaryRoute
import com.example.healthtracker.presentation.editprofile.ui.EditProfileRoute
import com.example.healthtracker.presentation.onboarding.ui.OnboardingRoute
import com.example.healthtracker.presentation.settings.ui.SettingsRoute
import com.example.healthtracker.presentation.statistics.ui.StatisticsRoute

@Composable
fun appEntryProvider(
    backStack: NavBackStack<NavKey>,
    onShowMessage: (String) -> Unit,
): (NavKey) -> NavEntry<NavKey> {
    return { key ->
        when (key) {
            Onboarding -> NavEntry(key) {
                OnboardingRoute(
                    onCompleted = { backStack.setRoot(Dashboard) },
                )
            }

            Dashboard -> NavEntry(key) {
                DashboardRoute(
                    onDiaryNavigate = { backStack.switchTab(Diary) },
                    onActivityNavigate = { backStack.switchTab(ActivityLog) }
                )
            }

            Diary -> NavEntry(key) {
                DiaryRoute()
            }

            ActivityLog -> NavEntry(key) {
                ActivityRoute()
            }

            Statistics -> NavEntry(key) {
                StatisticsRoute()
            }

            Settings -> NavEntry(key) {
                SettingsRoute(
                    onEditProfileNavigate = { backStack.navigate(EditProfile) },
                )
            }

            EditProfile -> NavEntry(key) {
                EditProfileRoute(
                    onBack = { backStack.goBack() },
                    onShowMessage = onShowMessage,
                )
            }

            else -> NavEntry(key) {
                PlaceholderScreen("Error Screen")
            }
        }
    }
}
