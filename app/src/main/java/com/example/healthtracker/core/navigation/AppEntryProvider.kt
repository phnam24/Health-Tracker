package com.example.healthtracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import com.example.healthtracker.presentation.PlaceholderScreen
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
                PlaceholderScreen("Dashboard")
            }

            Diary -> NavEntry(key) {
                PlaceholderScreen("Diary")
            }

            ActivityLog -> NavEntry(key) {
                PlaceholderScreen("Activity Log")
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