package com.example.task2.models.benchmarks

import com.example.task2.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MapBenchmarkTest {

    private lateinit var benchmark: MapBenchmark

    @Before
    fun setup() {
        benchmark = MapBenchmark()
    }

    @Test
    fun testGetNumberOfColumns() {
        Assert.assertEquals(2, benchmark.getNumberOfColumns())
    }

    @Test
    fun testCreateItemsList() {
        val cellOperations = benchmark.createItemsList(true)
        val expectedCellOperations = listOf(
                CellOperation(R.string.adding_new, R.string.treemap, R.string.na.toLong(), true),
                CellOperation(R.string.adding_new, R.string.hashmap, R.string.na.toLong(), true),
                CellOperation(R.string.search_by_key, R.string.treemap, R.string.na.toLong(), true),
                CellOperation(R.string.search_by_key, R.string.hashmap, R.string.na.toLong(), true),
                CellOperation(R.string.removing, R.string.treemap, R.string.na.toLong(), true),
                CellOperation(R.string.removing, R.string.hashmap, R.string.na.toLong(), true)
        )
        Assert.assertEquals(cellOperations.size, expectedCellOperations.size)
        for (i in cellOperations.indices) {
            Assert.assertEquals(cellOperations[i], expectedCellOperations[i])
        }
    }

    @Test
    fun testMeasureTime() {
        val cellOperations = benchmark.createItemsList(true)
        for (cellOperation in cellOperations) {
            Assert.assertTrue(benchmark.measureTime(cellOperation, 1000) > 0)
        }
    }

    @Test(expected = RuntimeException::class)
    fun testMeasureTimeException() {
        val cellOperation = CellOperation(2, R.string.arraylist, R.string.na.toLong(), true)
        benchmark.measureTime(cellOperation, 1000)
    }
}