package com.example.task2.models;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class CollectionBenchmark implements Benchmark {
    @Override
    public long addStart(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add(0, "test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    @Override
    public long addMiddle(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add(list.size() / 2, "test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    @Override
    public long addEnd(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add("test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    @Override
    public long searchByValue(List<String> list) {
        String specificNumber = "28";
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        list.set(randomIndex, specificNumber);
        long startTime, endTime;
        startTime = System.nanoTime();
        list.contains(specificNumber);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    @Override
    public long removeStart(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.remove(0);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    @Override
    public long removeMiddle(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.remove(list.size() / 2);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    @Override
    public long removeEnd(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.remove(list.size() - 1);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    @Override
    public long addNew(Map<String, String> map) {
        throw new UnsupportedOperationException("Method not supported for Collection benchmarking");
    }

    @Override
    public long searchByKey(Map<String, String> map) {
        throw new UnsupportedOperationException("Method not supported for Collection benchmarking");
    }

    @Override
    public long removing(Map<String, String> map) {
        throw new UnsupportedOperationException("Method not supported for Collection benchmarking");
    }
}
