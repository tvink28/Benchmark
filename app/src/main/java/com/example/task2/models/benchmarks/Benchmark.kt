package com.example.task2.models.benchmarks

import com.example.task2.models.room.ResultData

interface Benchmark {

    fun getNumberOfColumns(): Int

    fun createItemsList(setRunning: Boolean): List<CellOperation>

    fun measureTime(item: CellOperation, number: Int): Long

    fun setData(action: String, type: String, time: Long, input: Int)

    fun getData(): List<ResultData>
}
