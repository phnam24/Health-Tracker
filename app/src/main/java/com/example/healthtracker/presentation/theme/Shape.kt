package com.example.healthtracker.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

internal fun healthTrackerShapes(dimensions: HealthTrackerDimensions): Shapes {
    val controlShape = RoundedCornerShape(dimensions.controlCornerRadius)
    val cardShape = RoundedCornerShape(dimensions.cardCornerRadius)
    val bottomSheetShape = RoundedCornerShape(
        topStart = dimensions.bottomSheetCornerRadius,
        topEnd = dimensions.bottomSheetCornerRadius,
    )

    return Shapes(
        extraSmall = controlShape,
        small = controlShape,
        medium = controlShape,
        large = cardShape,
        extraLarge = bottomSheetShape,
    )
}
