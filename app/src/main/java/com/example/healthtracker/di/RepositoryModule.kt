package com.example.healthtracker.di

import com.example.healthtracker.data.repository.ActivityRepositoryImpl
import com.example.healthtracker.data.repository.DiaryRepositoryImpl
import com.example.healthtracker.data.repository.FoodRepositoryImpl
import com.example.healthtracker.data.repository.UserRepositoryImpl
import com.example.healthtracker.domain.repository.ActivityRepository
import com.example.healthtracker.domain.repository.DiaryRepository
import com.example.healthtracker.domain.repository.FoodRepository
import com.example.healthtracker.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindFoodRepository(impl: FoodRepositoryImpl): FoodRepository
    @Binds
    abstract fun bindDiaryRepository(impl: DiaryRepositoryImpl): DiaryRepository
    @Binds
    abstract fun bindActivityRepository(impl: ActivityRepositoryImpl): ActivityRepository
}