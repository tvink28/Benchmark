package com.example.task2.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BenchmarkManager {

    public BenchmarkManager() {
    }

    public long addStart(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add(0, "test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public long addMiddle(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add(list.size() / 2, "test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public long addEnd(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add("test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

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

    public long removeStart(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.remove(0);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public long removeMiddle(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.remove(list.size() / 2);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public long removeEnd(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.remove(list.size() - 1);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public long addNew(Map<String, String> map) {
        long startTime, endTime;
        startTime = System.nanoTime();
        map.put("key", "value");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public long searchByKey(Map<String, String> map) {
        String specificNumber = "28";
        Random random = new Random();
        List<String> keys = new ArrayList<>(map.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        map.put(randomKey, specificNumber);
        long startTime, endTime;
        startTime = System.nanoTime();
        map.containsKey(specificNumber);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public long removing(Map<String, String> map) {
        Random random = new Random();
        List<String> keys = new ArrayList<>(map.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        long startTime, endTime;
        startTime = System.nanoTime();
        map.remove(randomKey);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }
}
