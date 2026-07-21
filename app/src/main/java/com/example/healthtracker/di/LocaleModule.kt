package com.example.healthtracker.di

import com.example.healthtracker.core.locale.AppCompatLocaleController
import com.example.healthtracker.core.locale.LocaleController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocaleModule {
    @Binds
    abstract fun bindLocaleController(impl: AppCompatLocaleController): LocaleController
}
