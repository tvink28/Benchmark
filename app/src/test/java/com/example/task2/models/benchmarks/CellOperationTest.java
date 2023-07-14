package com.example.task2.models.benchmarks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import com.example.task2.R;

import org.junit.Before;
import org.junit.Test;

public class CellOperationTest {
    private CellOperation cellOperation;

    @Before
    public void setup() {
        cellOperation = new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, true);
    }

    @Test
    public void testWithTime() {
        final CellOperation updatedCellOperation = cellOperation.withTime(5);
        assertEquals(R.string.adding_in_the_beginning, updatedCellOperation.action);
        assertEquals(R.string.arraylist, updatedCellOperation.type);
        assertEquals(5, updatedCellOperation.time);
        assertFalse(updatedCellOperation.isRunning);
    }

    @Test
    public void testWithIsRunning() {
        final CellOperation updatedCellOperation = cellOperation.withIsRunning(false);
        assertEquals(R.string.adding_in_the_beginning, updatedCellOperation.action);
        assertEquals(R.string.arraylist, updatedCellOperation.type);
        assertEquals(R.string.na, updatedCellOperation.time);
        assertFalse(updatedCellOperation.isRunning);
    }

    @Test
    public void testEquals() {
        final CellOperation updatedCellOperationIsRunning = new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, false);
        final CellOperation updatedCellOperationAction = new CellOperation(R.string.removing_in_the_middle, R.string.arraylist, R.string.na, true);
        final CellOperation updatedCellOperationType = new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, R.string.na, true);
        final CellOperation updatedCellOperationTime = new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, 14, true);
        final CellOperation updatedCellOperation = new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, true);

        assertNotEquals(cellOperation, updatedCellOperationIsRunning);
        assertNotEquals(cellOperation, updatedCellOperationAction);
        assertNotEquals(cellOperation, updatedCellOperationType);
        assertNotEquals(cellOperation, updatedCellOperationTime);
        assertEquals(cellOperation, updatedCellOperation);
    }
}
