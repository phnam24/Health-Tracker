package com.example.healthtracker.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.calculate.CalculateAgeUseCase
import com.example.healthtracker.domain.usecase.calculate.CalculateBmiUseCase
import com.example.healthtracker.domain.usecase.calculate.CalculateBmrUseCase
import com.example.healthtracker.domain.usecase.calculate.CalculateBurnedCaloriesUseCase
import com.example.healthtracker.domain.usecase.calculate.CalculateTdeeUseCase
import com.example.healthtracker.domain.usecase.onboarding.BuildUserProfileUseCase
import com.example.healthtracker.domain.usecase.onboarding.CompleteOnboardingUseCase
import com.example.healthtracker.domain.usecase.onboarding.GetBmiPreviewUseCase
import com.example.healthtracker.domain.usecase.onboarding.GetTdeePreviewUseCase
import com.example.healthtracker.domain.usecase.onboarding.ValidateOnboardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideClock(): Clock =
        Clock.systemDefaultZone()

    @Provides
    @Singleton
    fun provideCalculateAgeUseCase(): CalculateAgeUseCase =
        CalculateAgeUseCase()

    @Provides
    @Singleton
    fun provideCalculateBmrUseCase(): CalculateBmrUseCase =
        CalculateBmrUseCase()

    @Provides
    @Singleton
    fun provideCalculateBmiUseCase(): CalculateBmiUseCase =
        CalculateBmiUseCase()

    @Provides
    @Singleton
    fun provideCalculateBurnedCaloriesUseCase():
            CalculateBurnedCaloriesUseCase =
        CalculateBurnedCaloriesUseCase()

    @Provides
    @Singleton
    fun provideCalculateTdeeUseCase(
        calculateAgeUseCase: CalculateAgeUseCase,
        calculateBmrUseCase: CalculateBmrUseCase
    ): CalculateTdeeUseCase =
        CalculateTdeeUseCase(
            calculateAge = calculateAgeUseCase,
            calculateBmr = calculateBmrUseCase
        )

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideValidateOnboardingUseCase(
        calculateAgeUseCase: CalculateAgeUseCase,
        clock: Clock
    ): ValidateOnboardingUseCase =
        ValidateOnboardingUseCase(
            calculateAge = calculateAgeUseCase,
            clock = clock
        )

    @Provides
    @Singleton
    fun provideBuildUserProfileUseCase():
            BuildUserProfileUseCase =
        BuildUserProfileUseCase()

    @Provides
    @Singleton
    fun provideGetBmiPreviewUseCase(
        calculateBmiUseCase: CalculateBmiUseCase
    ): GetBmiPreviewUseCase =
        GetBmiPreviewUseCase(
            calculateBmi = calculateBmiUseCase
        )

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideGetTdeePreviewUseCase(
        buildUserProfileUseCase: BuildUserProfileUseCase,
        calculateTdeeUseCase: CalculateTdeeUseCase
    ): GetTdeePreviewUseCase =
        GetTdeePreviewUseCase(
            buildUserProfile = buildUserProfileUseCase,
            calculateTdee = calculateTdeeUseCase
        )

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideCompleteOnboardingUseCase(
        validateOnboardingUseCase: ValidateOnboardingUseCase,
        buildUserProfileUseCase: BuildUserProfileUseCase,
        userRepository: UserRepository
    ): CompleteOnboardingUseCase =
        CompleteOnboardingUseCase(
            validateOnboarding = validateOnboardingUseCase,
            buildUserProfile = buildUserProfileUseCase,
            userRepository = userRepository
        )
}