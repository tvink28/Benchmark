package com.example.task2.models.benchmarks;

import com.example.task2.R;

import java.util.Arrays;
import java.util.List;

public class FakeCollectionBenchmark implements Benchmark {

    @Override
    public int getNumberOfColumns() {
        return 3;
    }

    @Override
    public List<CellOperation> createItemsList(boolean setRunning) {
        return Arrays.asList(
                new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_middle, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_end, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_end, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.search_by_value, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.search_by_value, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_middle, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_middle, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_end, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_end, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist, R.string.na, setRunning)
        );
    }

    @Override
    public long measureTime(CellOperation item, int number) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100;
    }
}
