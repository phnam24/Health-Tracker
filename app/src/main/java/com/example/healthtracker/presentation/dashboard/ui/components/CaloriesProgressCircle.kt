package com.example.healthtracker.presentation.dashboard.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DailySummary
import com.example.healthtracker.presentation.theme.AppDimensions
import com.example.healthtracker.presentation.theme.GreenPrimaryLight
import com.example.healthtracker.presentation.theme.OnSurfaceDark
import com.example.healthtracker.presentation.theme.OnSurfaceVariantLight
import java.time.LocalDate

@Composable
fun CaloriesProgressCircle(
    dailySummary: DailySummary
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(AppDimensions.CalorieRingSize),
                color = OnSurfaceDark,
                strokeWidth = AppDimensions.CalorieRingStrokeWidth,
            )

            CircularProgressIndicator(
                progress = {
                    dailySummary.balanceCalories.toFloat() / dailySummary.goalCalories
                },
                modifier = Modifier.size(AppDimensions.CalorieRingSize),
                color = GreenPrimaryLight,
                strokeWidth = AppDimensions.CalorieRingStrokeWidth,
                trackColor = Color.Transparent,
                strokeCap = StrokeCap.Round
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingExtraSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dailySummary.remainingCalories.toString(),
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = stringResource(R.string.dashboard_remaining),
                    style = MaterialTheme.typography.bodyLarge,
                    color = OnSurfaceVariantLight
                )

                Text(
                    text = stringResource(R.string.common_kcal),
                    style = MaterialTheme.typography.bodyLarge,
                    color = OnSurfaceVariantLight
                )
            }
        }
    }
}