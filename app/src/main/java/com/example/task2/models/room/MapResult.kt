package com.example.task2.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "map_results")
data class MapResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    override val input: Int,
    override val action: String,
    override val type: String,
    override val time: Long
) : ResultData
