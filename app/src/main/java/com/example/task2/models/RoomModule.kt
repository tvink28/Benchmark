package com.example.task2.models

import android.content.Context
import androidx.room.Room
import com.example.task2.models.room.AppDatabase
import com.example.task2.models.room.CollectionResultDao
import com.example.task2.models.room.MapResultDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
        ).build()
    }

    @Provides
    fun provideCollectionResultDao(database: AppDatabase): CollectionResultDao {
        return database.collectionResultDao()
    }

    @Provides
    fun provideMapResultDao(database: AppDatabase): MapResultDao {
        return database.mapResultDao()
    }
}