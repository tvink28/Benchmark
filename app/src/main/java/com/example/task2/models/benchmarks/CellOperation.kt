package com.example.task2.models.benchmarks

data class CellOperation(
    val action: Int,
    val type: Int,
    val time: Long,
    val isRunning: Boolean
) {
    fun withTime(newTime: Long): CellOperation = copy(time = newTime, isRunning = false)

    fun withIsRunning(newIsRunning: Boolean): CellOperation = copy(isRunning = newIsRunning)
}
