package com.example.task2.models.benchmarks;

import com.example.task2.R;

import java.util.Arrays;
import java.util.List;

public class FakeMapBenchmark implements Benchmark {

    @Override
    public int getNumberOfColumns() {
        return 2;
    }

    @Override
    public List<CellOperation> createItemsList(boolean setRunning) {
        return Arrays.asList(
                new CellOperation(R.string.adding_new, R.string.treemap, R.string.na, setRunning),
                new CellOperation(R.string.adding_new, R.string.hashmap, R.string.na, setRunning),
                new CellOperation(R.string.search_by_key, R.string.treemap, R.string.na, setRunning),
                new CellOperation(R.string.search_by_key, R.string.hashmap, R.string.na, setRunning),
                new CellOperation(R.string.removing, R.string.treemap, R.string.na, setRunning),
                new CellOperation(R.string.removing, R.string.hashmap, R.string.na, setRunning)
        );
    }

    @Override
    public long measureTime(CellOperation item, int number) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 99;
    }
}
