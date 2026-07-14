package com.example.healthtracker.presentation.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.presentation.theme.AppDimensions
import com.example.healthtracker.presentation.theme.BackgroundLight
import com.example.healthtracker.presentation.theme.GreenPrimaryContainerLight
import com.example.healthtracker.presentation.theme.HealthTrackerTypography
import com.example.healthtracker.presentation.theme.OnBackgroundLight
import com.example.healthtracker.presentation.theme.OnSurfaceVariantLight

@Composable
fun SplashScreen() {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1500
        ),
        label = "fade_in"
    )

    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(GreenPrimaryContainerLight, BackgroundLight)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alphaAnimation)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_healthtracker_logo),
                contentDescription = "Splash Art Logo",
                modifier = Modifier.size(AppDimensions.SplashLogoSize)
            )

            Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge))

            Text(
                text = "Health Tracker",
                style = HealthTrackerTypography.titleLarge,
                color = OnBackgroundLight
            )

            Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge))

            Text(
                text = stringResource(R.string.splash_tagline),
                style = HealthTrackerTypography.bodyLarge,
                color = OnSurfaceVariantLight
            )
        }

        Text(
            text = stringResource(R.string.splash_version),
            style = HealthTrackerTypography.bodyMedium,
            color = OnSurfaceVariantLight.copy(alpha = 0.75f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = AppDimensions.SplashVersionBottomPadding)
                .alpha(alphaAnimation)
        )
    }
}