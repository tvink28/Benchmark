package com.example.task2.models.benchmarks

import java.util.Objects

data class CellOperation(
        val action: Int,
        val type: Int,
        val time: Long,
        val isRunning: Boolean
) {
    fun withTime(newTime: Long): CellOperation = copy(time = newTime, isRunning = false)

    fun withIsRunning(newIsRunning: Boolean): CellOperation = copy(isRunning = newIsRunning)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other == null || this::class != other::class) {
            return false
        }

        other as CellOperation

        return action == other.action &&
                type == other.type &&
                time == other.time &&
                isRunning == other.isRunning
    }

    override fun hashCode(): Int = Objects.hash(action, type)

}