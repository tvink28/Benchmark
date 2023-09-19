package com.example.task2.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "operation_results")
data class OperationResult(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val input: Int,
	val action: String,
	val type: String,
	val time: Long
)
