package com.example.task2.models;

import java.util.List;
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

    public long searchByValue(List<String> list, String value) {
        String specificNumber = "28";
        long startTime, endTime;
        startTime = System.nanoTime();
        Random random = new Random();
        int randomIndex = random.nextInt(list.size() + 1);
        list.add(randomIndex, specificNumber);
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
}
