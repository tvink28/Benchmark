package com.example.task2.models.benchmarks

interface Benchmark {

    fun getNumberOfColumns(): Int

    fun createItemsList(setRunning: Boolean): List<CellOperation>

    fun measureTime(item: CellOperation, number: Int): Long
}