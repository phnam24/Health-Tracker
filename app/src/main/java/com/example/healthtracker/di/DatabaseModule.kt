package com.example.healthtracker.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.healthtracker.data.local.HealthDatabase
import com.example.healthtracker.data.local.seed.ActivitySeedData
import com.example.healthtracker.data.local.seed.FoodSeedData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HealthDatabase {
        lateinit var database: HealthDatabase

        database = Room.databaseBuilder(
            context,
            HealthDatabase::class.java,
            "health_tracker.db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                    database.foodDao().insertAll(FoodSeedData.items)
                    database.activityTypeDao().insertAll(ActivitySeedData.items)
                }
            }
        }).build()

        return database
    }

    @Provides
    fun provideUserProfileDao(db: HealthDatabase) = db.userProfileDao()

    @Provides
    fun provideFoodDao(db: HealthDatabase) = db.foodDao()

    @Provides
    fun provideActivityTypeDao(db: HealthDatabase) = db.activityTypeDao()

    @Provides
    fun provideMealEntryDao(db: HealthDatabase) = db.mealEntryDao()

    @Provides
    fun provideActivityEntryDao(db: HealthDatabase) = db.activityEntryDao()
}