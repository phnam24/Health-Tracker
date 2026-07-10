package com.example.healthtracker.core.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.setRoot(route: NavKey) {
    clear()
    add(route)
}

fun NavBackStack<NavKey>.navigate(route: NavKey) {
    add(route)
}

fun NavBackStack<NavKey>.switchTab(route: NavKey) {
    if (lastOrNull() == route) return
    clear()
    add(route)
}

fun NavBackStack<NavKey>.goBack() {
    if (size > 1) {
        removeLastOrNull()
    }
}