package com.example.task2.models.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CollectionResult::class, MapResult::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionResultDao(): CollectionResultDao
    abstract fun mapResultDao(): MapResultDao
}