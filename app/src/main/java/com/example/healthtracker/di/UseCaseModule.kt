package com.example.healthtracker.di

import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.BuildUserProfileUseCase
import com.example.healthtracker.domain.usecase.CalculateAgeUseCase
import com.example.healthtracker.domain.usecase.CalculateBmiUseCase
import com.example.healthtracker.domain.usecase.CalculateBmrUseCase
import com.example.healthtracker.domain.usecase.CalculateBurnedCaloriesUseCase
import com.example.healthtracker.domain.usecase.CalculateTdeeUseCase
import com.example.healthtracker.domain.usecase.CompleteOnboardingUseCase
import com.example.healthtracker.domain.usecase.GetBmiPreviewUseCase
import com.example.healthtracker.domain.usecase.GetTdeePreviewUseCase
import com.example.healthtracker.domain.usecase.ValidateOnboardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
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
