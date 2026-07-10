package com.example.healthtracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val ControlShape = RoundedCornerShape(12.dp)
val CardShape = RoundedCornerShape(16.dp)
val BottomSheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
val PillShape = RoundedCornerShape(percent = 50)

val HealthTrackerShapes = Shapes(
    extraSmall = ControlShape,
    small = ControlShape,
    medium = ControlShape,
    large = CardShape,
    extraLarge = BottomSheetShape,
)
