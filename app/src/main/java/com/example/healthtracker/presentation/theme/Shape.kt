package com.example.healthtracker.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

internal fun healthTrackerShapes(dimensions: HealthTrackerDimensions): Shapes {
    val controlShape = RoundedCornerShape(dimensions.ControlCornerRadius)
    val cardShape = RoundedCornerShape(dimensions.CardCornerRadius)
    val bottomSheetShape = RoundedCornerShape(
        topStart = dimensions.BottomSheetCornerRadius,
        topEnd = dimensions.BottomSheetCornerRadius,
    )

    return Shapes(
        extraSmall = controlShape,
        small = controlShape,
        medium = controlShape,
        large = cardShape,
        extraLarge = bottomSheetShape,
    )
}
