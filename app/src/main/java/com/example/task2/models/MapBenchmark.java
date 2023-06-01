package com.example.task2.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapBenchmark implements Benchmark {
    Random random = new Random();

    @Override
    public long addNew(Map<String, String> map) {
        long startTime, endTime;
        startTime = System.nanoTime();
        map.put("key", "value");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    @Override
    public long searchByKey(Map<String, String> map) {
        String specificNumber = "28";
        List<String> keys = new ArrayList<>(map.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        map.put(randomKey, specificNumber);
        long startTime, endTime;
        startTime = System.nanoTime();
        map.containsKey(specificNumber);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    @Override
    public long removing(Map<String, String> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        long startTime, endTime;
        startTime = System.nanoTime();
        map.remove(randomKey);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    @Override
    public long addStart(List<String> list) {
        throw new UnsupportedOperationException("Method not supported for Map benchmarking");
    }

    @Override
    public long addMiddle(List<String> list) {
        throw new UnsupportedOperationException("Method not supported for Map benchmarking");
    }

    @Override
    public long addEnd(List<String> list) {
        throw new UnsupportedOperationException("Method not supported for Map benchmarking");
    }

    @Override
    public long searchByValue(List<String> list) {
        throw new UnsupportedOperationException("Method not supported for Map benchmarking");
    }

    @Override
    public long removeStart(List<String> list) {
        throw new UnsupportedOperationException("Method not supported for Map benchmarking");
    }

    @Override
    public long removeMiddle(List<String> list) {
        throw new UnsupportedOperationException("Method not supported for Map benchmarking");
    }

    @Override
    public long removeEnd(List<String> list) {
        throw new UnsupportedOperationException("Method not supported for Map benchmarking");
    }
}
