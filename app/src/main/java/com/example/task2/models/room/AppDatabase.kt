package com.example.task2.models.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CollectionResult::class, MapResult::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionResultDao(): CollectionResultDao
    abstract fun mapResultDao(): MapResultDao

    companion object {
        private var instance: AppDatabase? = null

        fun init(context: Context) {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_database"
                    ).build()
                }
            }
        }

        fun getInstance(): AppDatabase {
            return instance ?: throw IllegalStateException("AppDatabase must be initialized")
        }
    }
}