package com.example.task2.models.benchmarks;

import static com.example.task2.Constants.RESULT_MAP;
import static com.example.task2.Constants.SLEEP_TIME_FOR_MODELS;

public class FakeMapBenchmark extends MapBenchmark implements Benchmark {

    @Override
    public long measureTime(CellOperation item, int number) {
        try {
            Thread.sleep(SLEEP_TIME_FOR_MODELS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return RESULT_MAP;
    }
}
