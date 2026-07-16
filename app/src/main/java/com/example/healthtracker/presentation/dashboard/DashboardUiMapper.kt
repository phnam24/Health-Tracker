package com.example.healthtracker.presentation.dashboard

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DailyAdviceType

@StringRes
fun DailyAdviceType.toMessageRes(): Int = when (this) {
    DailyAdviceType.NEED_MORE ->
        R.string.dashboard_advice_need_more

    DailyAdviceType.ON_TARGET ->
        R.string.dashboard_advice_on_target

    DailyAdviceType.OVER ->
        R.string.dashboard_advice_over
}