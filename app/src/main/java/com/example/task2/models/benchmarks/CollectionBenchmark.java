package com.example.task2.models.benchmarks;

import com.example.task2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionBenchmark implements Benchmark {
    private static final String SPECIFIC_NUMBER = "28";
    private final Random random = new Random();

    @Override
    public int getNumberOfColumns() {
        return 3;
    }

    @Override
    public List<CellOperation> createItemsList(boolean setRunning) {
        return Arrays.asList(
                new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_middle, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_end, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_end, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.search_by_value, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.search_by_value, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_middle, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_middle, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_end, R.string.arraylist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_end, R.string.linkedlist, R.string.na, setRunning),
                new CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist, R.string.na, setRunning)
        );
    }

    @Override
    public long measureTime(CellOperation item, int number) {
        List<String> list;
        if (item.type == R.string.arraylist) {
            list = new ArrayList<>(Collections.nCopies(number, "test"));
        } else if (item.type == R.string.linkedlist) {
            list = new LinkedList<>(Collections.nCopies(number, "test"));
        } else if (item.type == R.string.copyonwritearraylist) {
            list = new CopyOnWriteArrayList<>(Collections.nCopies(number, "test"));
        } else {
            throw new RuntimeException("Unsupported collection type");
        }

        long result;
        if (item.action == R.string.adding_in_the_beginning) {
            result = addStart(list);
        } else if (item.action == R.string.adding_in_the_middle) {
            result = addMiddle(list);
        } else if (item.action == R.string.adding_in_the_end) {
            result = addEnd(list);
        } else if (item.action == R.string.search_by_value) {
            result = searchByValue(list);
        } else if (item.action == R.string.removing_in_the_beginning) {
            result = removeStart(list);
        } else if (item.action == R.string.removing_in_the_middle) {
            result = removeMiddle(list);
        } else if (item.action == R.string.removing_in_the_end) {
            result = removeEnd(list);
        } else {
            throw new RuntimeException("Unsupported action type");
        }
        return result;
    }

    private long addStart(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add(0, "test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private long addMiddle(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add(list.size() / 2, "test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private long addEnd(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.add("test");
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private long searchByValue(List<String> list) {
        int randomIndex = random.nextInt(list.size());
        list.set(randomIndex, SPECIFIC_NUMBER);
        long startTime, endTime;
        startTime = System.nanoTime();
        list.contains(SPECIFIC_NUMBER);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private long removeStart(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.remove(0);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private long removeMiddle(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.remove(list.size() / 2);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private long removeEnd(List<String> list) {
        long startTime, endTime;
        startTime = System.nanoTime();
        list.remove(list.size() - 1);
        endTime = System.nanoTime();
        return (endTime - startTime);
    }
}
