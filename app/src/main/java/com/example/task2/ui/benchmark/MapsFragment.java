package com.example.task2.ui.benchmark;

import com.example.task2.R;
import com.example.task2.models.CellOperation;

import java.util.Arrays;
import java.util.List;

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


}
