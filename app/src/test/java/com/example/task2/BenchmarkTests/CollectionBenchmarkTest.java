package com.example.task2.BenchmarkTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.task2.R;
import com.example.task2.models.benchmarks.CellOperation;
import com.example.task2.models.benchmarks.CollectionBenchmark;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CollectionBenchmarkTest {
    private CollectionBenchmark benchmark;

    @Before
    public void setup() {
        benchmark = new CollectionBenchmark();
    }

    @Test
    public void testGetNumberOfColumns() {
        assertEquals(3, benchmark.getNumberOfColumns());
    }

    @Test
    public void testCreateItemsList() {
        final List<CellOperation> cellOperations = benchmark.createItemsList(true);
        final List<CellOperation> expectedCellOperations = Arrays.asList(
                new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, true),
                new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na, true),
                new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, R.string.na, true),
                new CellOperation(R.string.adding_in_the_middle, R.string.arraylist, R.string.na, true),
                new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, R.string.na, true),
                new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, R.string.na, true),
                new CellOperation(R.string.adding_in_the_end, R.string.arraylist, R.string.na, true),
                new CellOperation(R.string.adding_in_the_end, R.string.linkedlist, R.string.na, true),
                new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, R.string.na, true),
                new CellOperation(R.string.search_by_value, R.string.arraylist, R.string.na, true),
                new CellOperation(R.string.search_by_value, R.string.linkedlist, R.string.na, true),
                new CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, R.string.na, true),
                new CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, R.string.na, true),
                new CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, R.string.na, true),
                new CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist, R.string.na, true),
                new CellOperation(R.string.removing_in_the_middle, R.string.arraylist, R.string.na, true),
                new CellOperation(R.string.removing_in_the_middle, R.string.linkedlist, R.string.na, true),
                new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, R.string.na, true),
                new CellOperation(R.string.removing_in_the_end, R.string.arraylist, R.string.na, true),
                new CellOperation(R.string.removing_in_the_end, R.string.linkedlist, R.string.na, true),
                new CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist, R.string.na, true)
        );
        assertEquals(cellOperations.size(), expectedCellOperations.size());
        for (int i = 0; i < cellOperations.size(); i++) {
            assertEquals(cellOperations.get(i), expectedCellOperations.get(i));
        }
    }

    @Test
    public void testMeasureTime() {
        final List<CellOperation> cellOperations = benchmark.createItemsList(true);
        for (CellOperation cellOperation : cellOperations) {
            assertTrue(benchmark.measureTime(cellOperation, 1000) > 0);
        }
    }

    @Test(expected = RuntimeException.class)
    public void testMeasureTimeException() {
        final CellOperation cellOperation = new CellOperation(2, R.string.arraylist, R.string.na, true);
        benchmark.measureTime(cellOperation, 1000);
    }
}
