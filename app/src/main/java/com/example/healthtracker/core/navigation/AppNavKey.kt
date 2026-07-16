package com.example.healthtracker.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object SplashArt : NavKey

@Serializable
data object Onboarding : NavKey

@Serializable
data object Dashboard : NavKey

@Serializable
data object Diary : NavKey

@Serializable
data object ActivityLog : NavKey

@Serializable
data object Statistics : NavKey

@Serializable
data object Settings : NavKey

@Serializable
data object EditProfile : NavKey