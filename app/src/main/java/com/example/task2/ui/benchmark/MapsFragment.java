package com.example.task2.ui.benchmark;

import com.example.task2.R;
import com.example.task2.models.BenchmarkManager;
import com.example.task2.models.CellOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapsFragment extends BenchmarksFragment {


    protected List<CellOperation> createItemsList() {
        return Arrays.asList(
                new CellOperation(R.string.adding_new, R.string.treemap, R.string.na),
                new CellOperation(R.string.adding_new, R.string.hashmap, R.string.na),
                new CellOperation(R.string.search_by_key, R.string.treemap, R.string.na),
                new CellOperation(R.string.search_by_key, R.string.hashmap, R.string.na),
                new CellOperation(R.string.removing, R.string.treemap, R.string.na),
                new CellOperation(R.string.removing, R.string.hashmap, R.string.na)
        );
    }

    protected CellOperation measureTime(CellOperation item, int number) {
        BenchmarkManager bm = new BenchmarkManager();

        // вернусь сюда позже

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
            result = bm.addStart(list);
        } else if (item.action == R.string.adding_in_the_middle) {
            result = bm.addMiddle(list);
        } else if (item.action == R.string.adding_in_the_end) {
            result = bm.addEnd(list);
        } else {
            throw new RuntimeException("Unsupported action type");
        }
        return item.withTime(result);
    }
}
