package com.example.task2.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MapResultDao {
    @Insert
    fun insertResult(result: MapResult)

    @Query("SELECT * FROM map_results")
    fun getAllResult(): List<MapResult>

    @Query("SELECT * FROM map_results ORDER BY id DESC LIMIT 6")
    fun getLastResults(): List<MapResult>
}