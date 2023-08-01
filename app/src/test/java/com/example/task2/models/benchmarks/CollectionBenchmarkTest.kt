package com.example.task2.models.benchmarks

import com.example.task2.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CollectionBenchmarkTest {

    private lateinit var benchmark: CollectionBenchmark

    @Before
    fun setup() {
        benchmark = CollectionBenchmark()
    }

    @Test
    fun testGetNumberOfColumns() {
        Assert.assertEquals(3, benchmark.getNumberOfColumns())
    }

    @Test
    fun testCreateItemsList() {
        val cellOperations = benchmark.createItemsList(true)
        val expectedCellOperations = listOf(
                CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na.toLong(), true),
                CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na.toLong(), true),
                CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, R.string.na.toLong(), true),
                CellOperation(R.string.adding_in_the_middle, R.string.arraylist, R.string.na.toLong(), true),
                CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, R.string.na.toLong(), true),
                CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, R.string.na.toLong(), true),
                CellOperation(R.string.adding_in_the_end, R.string.arraylist, R.string.na.toLong(), true),
                CellOperation(R.string.adding_in_the_end, R.string.linkedlist, R.string.na.toLong(), true),
                CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, R.string.na.toLong(), true),
                CellOperation(R.string.search_by_value, R.string.arraylist, R.string.na.toLong(), true),
                CellOperation(R.string.search_by_value, R.string.linkedlist, R.string.na.toLong(), true),
                CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, R.string.na.toLong(), true),
                CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, R.string.na.toLong(), true),
                CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, R.string.na.toLong(), true),
                CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist, R.string.na.toLong(), true),
                CellOperation(R.string.removing_in_the_middle, R.string.arraylist, R.string.na.toLong(), true),
                CellOperation(R.string.removing_in_the_middle, R.string.linkedlist, R.string.na.toLong(), true),
                CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, R.string.na.toLong(), true),
                CellOperation(R.string.removing_in_the_end, R.string.arraylist, R.string.na.toLong(), true),
                CellOperation(R.string.removing_in_the_end, R.string.linkedlist, R.string.na.toLong(), true),
                CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist, R.string.na.toLong(), true)
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
