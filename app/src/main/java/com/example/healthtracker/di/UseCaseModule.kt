package com.example.healthtracker.di

import com.example.healthtracker.domain.usecase.CalculateAgeUseCase
import com.example.healthtracker.domain.usecase.CalculateBmiUseCase
import com.example.healthtracker.domain.usecase.CalculateBmrUseCase
import com.example.healthtracker.domain.usecase.CalculateBurnedCaloriesUseCase
import com.example.healthtracker.domain.usecase.CalculateTdeeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

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
    fun provideCalculateBurnedCaloriesUseCase(): CalculateBurnedCaloriesUseCase =
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
}