package com.example.task2.models;

import com.example.task2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapBenchmark implements Benchmark {

    @Override
    public int getNumberOfColumns() {
        return 2;
    }

    @Override
    public List<CellOperation> createItemsList(boolean setRunning) {
        List<CellOperation> operations = Arrays.asList(
                new CellOperation(R.string.adding_new, R.string.treemap, R.string.na, false),
                new CellOperation(R.string.adding_new, R.string.hashmap, R.string.na, false),
                new CellOperation(R.string.search_by_key, R.string.treemap, R.string.na, false),
                new CellOperation(R.string.search_by_key, R.string.hashmap, R.string.na, false),
                new CellOperation(R.string.removing, R.string.treemap, R.string.na, false),
                new CellOperation(R.string.removing, R.string.hashmap, R.string.na, false)
        );

        if (setRunning) {
            List<CellOperation> updatedOperations = new ArrayList<>();
            for (CellOperation operation : operations) {
                updatedOperations.add(operation.withIsRunning(true));
            }
            return updatedOperations;
        } else {
            return operations;
        }
    }

    @Override
    public long measureTime(CellOperation item, int number) {
        Map<String, String> map;
        if (item.type == R.string.treemap) {
            map = new TreeMap<>();
        } else if (item.type == R.string.hashmap) {
            map = new HashMap<>();
        } else {
            throw new RuntimeException("Unsupported map type");
        }

        for (int i = 0; i < number; i++) {
            map.put("key" + i, "value" + i);
        }

        long result;
        if (item.action == R.string.adding_new) {
            result = addNew(map);
        } else if (item.action == R.string.search_by_key) {
            result = searchByKey(map);
        } else if (item.action == R.string.removing) {
            result = removing(map);
        } else {
            throw new RuntimeException("Unsupported action type");
        }
        return result;
    }

    public long addNew(Map<String, String> map) {
        long startTime, endTime;
        startTime = System.nanoTime();
        map.put("key", "value");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public long searchByKey(Map<String, String> map) {
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
        List<String> keys = new ArrayList<>(map.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        long startTime, endTime;
        startTime = System.nanoTime();
        map.remove(randomKey);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }
}
