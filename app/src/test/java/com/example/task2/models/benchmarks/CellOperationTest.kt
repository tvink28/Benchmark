package com.example.task2.models.benchmarks

import com.example.task2.R
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CellOperationTest {

    private lateinit var cellOperation: CellOperation

    @Before
    fun setup() {
        cellOperation = CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na.toLong(), true)
    }

    @Test
    fun testWithTime() {
        val updatedCellOperation = cellOperation.withTime(5)
        assertEquals(R.string.adding_in_the_beginning, updatedCellOperation.action)
        assertEquals(R.string.arraylist, updatedCellOperation.type)
        assertEquals(5, updatedCellOperation.time)
        assertEquals(false, updatedCellOperation.isRunning)
    }

    @Test
    fun testWithIsRunning() {
        val updatedCellOperation = cellOperation.withIsRunning(false)
        assertEquals(R.string.adding_in_the_beginning, updatedCellOperation.action)
        assertEquals(R.string.arraylist, updatedCellOperation.type)
        assertEquals(R.string.na.toLong(), updatedCellOperation.time)
        assertEquals(false, updatedCellOperation.isRunning)
    }

    @Test
    fun testEquals() {
        val updatedCellOperationIsRunning = CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na.toLong(), false)
        val updatedCellOperationAction = CellOperation(R.string.removing_in_the_middle, R.string.arraylist, R.string.na.toLong(), true)
        val updatedCellOperationType = CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, R.string.na.toLong(), true)
        val updatedCellOperationTime = CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, 14, true)
        val updatedCellOperation = CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na.toLong(), true)

        Assert.assertNotEquals(cellOperation, updatedCellOperationIsRunning)
        Assert.assertNotEquals(cellOperation, updatedCellOperationAction)
        Assert.assertNotEquals(cellOperation, updatedCellOperationType)
        Assert.assertNotEquals(cellOperation, updatedCellOperationTime)
        Assert.assertEquals(cellOperation, updatedCellOperation)

        Assert.assertEquals(cellOperation.hashCode().toLong(), updatedCellOperationIsRunning.hashCode().toLong())
        Assert.assertEquals(cellOperation.hashCode().toLong(), updatedCellOperationTime.hashCode().toLong())
        Assert.assertEquals(cellOperation.hashCode().toLong(), updatedCellOperation.hashCode().toLong())
        Assert.assertNotEquals(cellOperation.hashCode().toLong(), updatedCellOperationAction.hashCode().toLong())
        Assert.assertNotEquals(cellOperation.hashCode().toLong(), updatedCellOperationType.hashCode().toLong())
    }
}
