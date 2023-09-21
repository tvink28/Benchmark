package com.example.task2.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "collection_results")
data class CollectionResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    override val input: Int,
    override val action: String,
    override val type: String,
    override val time: Long
) : ResultData
