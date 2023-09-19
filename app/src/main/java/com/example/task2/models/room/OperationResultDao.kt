package com.example.task2.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OperationResultDao {
	@Insert
	fun insertResult(result: OperationResult)

	@Query("SELECT * FROM operation_results")
	fun getAllResult(): List<OperationResult>

	@Query("SELECT * FROM operation_results ORDER BY id DESC LIMIT 21")
	fun getLast21Results(): List<OperationResult>
}