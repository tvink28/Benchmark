package com.example.task2.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BenchmarkManager {
    private final CollectionProvider collectionProvider;
    private final ExecutorService executorService;

    public BenchmarkManager(CollectionProvider collectionProvider) {
        this.collectionProvider = collectionProvider;
        this.executorService = Executors.newFixedThreadPool(20);
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public Future<Long> addStart(List<String> list, int size) {
        return executorService.submit(() -> {
            for (int i = 0; i < size; i++) {
                list.add("test" + i);
            }
            long startTime, endTime;
            startTime = System.nanoTime();
            list.add(0, "test");
            endTime = System.nanoTime();
            return (endTime - startTime);
        });
    }

    public Future<Long> addMiddle(List<String> list, int size) {
        return executorService.submit(() -> {
            for (int i = 0; i < size; i++) {
                list.add("test" + i);
            }
            long startTime, endTime;
            startTime = System.nanoTime();
            list.add(list.size() / 2, "test");
            endTime = System.nanoTime();
            return (endTime - startTime);
        });
    }

    public Future<Long> addEnd(List<String> list, int size) {
        return executorService.submit(() -> {
            for (int i = 0; i < size; i++) {
                list.add("test" + i);
            }
            long startTime, endTime;
            startTime = System.nanoTime();
            list.add("test");
            endTime = System.nanoTime();
            return (endTime - startTime);
        });
    }

    public Future<Long> searchValue(List<String> list, int size) {
        return executorService.submit(() -> {
            for (int i = 0; i < size; i++) {
                list.add("test" + i);
            }
            long startTime, endTime;
            startTime = System.nanoTime();
            list.indexOf("test0");
            endTime = System.nanoTime();
            return (endTime - startTime);
        });
    }

    public Future<Long> removeStart(List<String> list, int size) {
        return executorService.submit(() -> {
            for (int i = 0; i < size; i++) {
                list.add("test" + i);
            }
            long startTime, endTime;
            startTime = System.nanoTime();
            list.remove(0);
            endTime = System.nanoTime();
            return (endTime - startTime);
        });
    }

    public Future<Long> removeMiddle(List<String> list, int size) {
        return executorService.submit(() -> {
            for (int i = 0; i < size; i++) {
                list.add("test" + i);
            }
            long startTime, endTime;
            startTime = System.nanoTime();
            list.remove(list.size() / 2);
            endTime = System.nanoTime();
            return (endTime - startTime);
        });
    }

    public Future<Long> removeEnd(List<String> list, int size) {
        return executorService.submit(() -> {
            for (int i = 0; i < size; i++) {
                list.add("test" + i);
            }
            long startTime, endTime;
            startTime = System.nanoTime();
            list.remove(list.size() - 1);
            endTime = System.nanoTime();
            return (endTime - startTime);
        });
    }


    public static class CollectionProvider {
        private final List<String> arrayList = new ArrayList<>();
        private final List<String> linkedList = new LinkedList<>();
        private final List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        public List<String> getArrayList() {
            return arrayList;
        }

        public List<String> getLinkedList() {
            return linkedList;
        }

        public List<String> getCopyOnWriteArrayList() {
            return copyOnWriteArrayList;
        }
    }

    public CollectionProvider getCollectionProvider() {
        return collectionProvider;
    }
}
