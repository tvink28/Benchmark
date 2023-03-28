package com.example.task2.models;

import java.util.List;

public class BenchmarkManager {

    public BenchmarkManager() {
        ;
    }


    public long addStart(List<String> list, int size) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add(0, "test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public long addMiddle(List<String> list, int size) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add(list.size() / 2, "test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public long addEnd(List<String> list, int size) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add("test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }


//    public CompletableFuture<Long> addStart(List<String> list, int size) {
//        return CompletableFuture.supplyAsync(() -> {
//            for (int i = 0; i < size; i++) {
//                list.add("test" + i);
//            }
//            long startTime, endTime;
//            startTime = System.nanoTime();
//            list.add(0, "test");
//            endTime = System.nanoTime();
//            return (endTime - startTime);
//        }, executorService);
//    }
//
//    public CompletableFuture<Long> addMiddle(List<String> list, int size) {
//        return CompletableFuture.supplyAsync(() -> {
//            for (int i = 0; i < size; i++) {
//                list.add("test" + i);
//            }
//            long startTime, endTime;
//            startTime = System.nanoTime();
//            list.add(list.size() / 2, "test");
//            endTime = System.nanoTime();
//            return (endTime - startTime);
//        }, executorService);
//    }
//
//    public CompletableFuture<Long> addEnd(List<String> list, int size) {
//        return CompletableFuture.supplyAsync(() -> {
//            for (int i = 0; i < size; i++) {
//                list.add("test" + i);
//            }
//            long startTime, endTime;
//            startTime = System.nanoTime();
//            list.add("test");
//            endTime = System.nanoTime();
//            return (endTime - startTime);
//        }, executorService);
//    }
}
