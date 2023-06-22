package com.example.task2.models.benchmarks;

import java.util.List;

public interface Benchmark {

    int getNumberOfColumns();

    List<CellOperation> createItemsList(boolean setRunning);

    long measureTime(CellOperation item, int number);
}












