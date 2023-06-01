package com.example.task2.ui.benchmark;

import com.example.task2.R;
import com.example.task2.models.BenchmarkManager;
import com.example.task2.models.CellOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapsFragment extends BenchmarksFragment {

    @Override
    protected int getNumberOfColumns() {
        return 2;
    }

    protected List<CellOperation> createItemsList(boolean setRunning) {
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

    protected long measureTime(CellOperation item, int number) {
        BenchmarkManager bm = new BenchmarkManager();

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
            result = bm.addNew(map);
        } else if (item.action == R.string.search_by_key) {
            result = bm.searchByKey(map);
        } else if (item.action == R.string.removing) {
            result = bm.removing(map);
        } else {
            throw new RuntimeException("Unsupported action type");
        }
        return result;
    }
}
