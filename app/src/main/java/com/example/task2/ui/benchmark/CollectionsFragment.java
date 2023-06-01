package com.example.task2.ui.benchmark;

import com.example.task2.R;
import com.example.task2.models.Benchmark;
import com.example.task2.models.CellOperation;
import com.example.task2.models.CollectionBenchmark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionsFragment extends BenchmarksFragment {

    @Override
    protected int getNumberOfColumns() {
        return 3;
    }

    protected List<CellOperation> createItemsList(boolean setRunning) {
        List<CellOperation> operations = Arrays.asList(
                new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, false),
                new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na, false),
                new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, R.string.na, false),
                new CellOperation(R.string.adding_in_the_middle, R.string.arraylist, R.string.na, false),
                new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, R.string.na, false),
                new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, R.string.na, false),
                new CellOperation(R.string.adding_in_the_end, R.string.arraylist, R.string.na, false),
                new CellOperation(R.string.adding_in_the_end, R.string.linkedlist, R.string.na, false),
                new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, R.string.na, false),
                new CellOperation(R.string.search_by_value, R.string.arraylist, R.string.na, false),
                new CellOperation(R.string.search_by_value, R.string.linkedlist, R.string.na, false),
                new CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, R.string.na, false),
                new CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, R.string.na, false),
                new CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, R.string.na, false),
                new CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist, R.string.na, false),
                new CellOperation(R.string.removing_in_the_middle, R.string.arraylist, R.string.na, false),
                new CellOperation(R.string.removing_in_the_middle, R.string.linkedlist, R.string.na, false),
                new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, R.string.na, false),
                new CellOperation(R.string.removing_in_the_end, R.string.arraylist, R.string.na, false),
                new CellOperation(R.string.removing_in_the_end, R.string.linkedlist, R.string.na, false),
                new CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist, R.string.na, false)
        );

        if (setRunning) {
            List<CellOperation> updatedOperations = new ArrayList<>();
            for (CellOperation operation : operations) {
                updatedOperations.add(operation.withIsRunning(true));
            }
            return updatedOperations;
        } else {
            return operations;
        }
    }

    protected long measureTime(CellOperation item, int number) {
        Benchmark benchmark;

        List<String> list;
        if (item.type == R.string.arraylist) {
            list = new ArrayList<>(Collections.nCopies(number, "test"));
            benchmark = new CollectionBenchmark();
        } else if (item.type == R.string.linkedlist) {
            list = new LinkedList<>(Collections.nCopies(number, "test"));
            benchmark = new CollectionBenchmark();
        } else if (item.type == R.string.copyonwritearraylist) {
            list = new CopyOnWriteArrayList<>(Collections.nCopies(number, "test"));
            benchmark = new CollectionBenchmark();
        } else {
            throw new RuntimeException("Unsupported collection type");
        }

        long result;
        if (item.action == R.string.adding_in_the_beginning) {
            result = benchmark.addStart(list);
        } else if (item.action == R.string.adding_in_the_middle) {
            result = benchmark.addMiddle(list);
        } else if (item.action == R.string.adding_in_the_end) {
            result = benchmark.addEnd(list);
        } else if (item.action == R.string.search_by_value) {
            result = benchmark.searchByValue(list);
        } else if (item.action == R.string.removing_in_the_beginning) {
            result = benchmark.removeStart(list);
        } else if (item.action == R.string.removing_in_the_middle) {
            result = benchmark.removeMiddle(list);
        } else if (item.action == R.string.removing_in_the_end) {
            result = benchmark.removeEnd(list);
        } else {
            throw new RuntimeException("Unsupported action type");
        }
        return result;
    }
}
