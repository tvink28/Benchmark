package com.example.task2.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CollectionResultDao {
    @Insert
    fun insertResult(result: CollectionResult)

    @Query("SELECT * FROM collection_results")
    fun getAllResult(): List<CollectionResult>

    @Query("SELECT * FROM collection_results ORDER BY id DESC LIMIT 21")
    fun getLastResults(): List<CollectionResult>
}