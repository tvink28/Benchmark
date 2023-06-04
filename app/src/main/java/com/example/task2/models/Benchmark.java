package com.example.task2.models;

import java.util.List;
import java.util.Random;

public interface Benchmark {
    String specificNumber = "28";
    Random random = new Random();

    int getNumberOfColumns();

    List<CellOperation> createItemsList(boolean setRunning);

    long measureTime(CellOperation item, int number);
}












