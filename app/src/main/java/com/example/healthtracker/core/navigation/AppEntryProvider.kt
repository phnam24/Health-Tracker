package com.example.healthtracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import com.example.healthtracker.presentation.PlaceholderScreen
import com.example.healthtracker.presentation.activity.ui.ActivityRoute
import com.example.healthtracker.presentation.dashboard.ui.DashboardRoute
import com.example.healthtracker.presentation.diary.ui.DiaryRoute
import com.example.healthtracker.presentation.onboarding.ui.OnboardingRoute

@Composable
fun appEntryProvider(
    backStack: NavBackStack<NavKey>
): (NavKey) -> NavEntry<NavKey> {
    return { key ->
        when (key) {
            SplashArt -> NavEntry(key) {
                PlaceholderScreen("Splash Art")
            }

            Onboarding -> NavEntry(key) {
                OnboardingRoute(
                    onCompleted = { backStack.navigate(Dashboard) },
                )
            }

            Dashboard -> NavEntry(key) {
                DashboardRoute(
                    onDiaryNavigate = { backStack.navigate(Diary) },
                    onActivityNavigate = { backStack.navigate(ActivityLog) }
                )
            }

            Diary -> NavEntry(key) {
                DiaryRoute()
            }

            ActivityLog -> NavEntry(key) {
                ActivityRoute()
            }

            Statistics -> NavEntry(key) {
                PlaceholderScreen("Statistics")
            }

            Settings -> NavEntry(key) {
                PlaceholderScreen("Settings")
            }

            EditProfile -> NavEntry(key) {
                PlaceholderScreen("Edit Profile")
            }

            else -> NavEntry(key) {
                PlaceholderScreen("Error Screen")
            }
        }
    }
}
