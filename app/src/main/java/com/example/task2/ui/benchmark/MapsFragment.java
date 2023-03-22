package com.example.task2.ui.benchmark;

import android.os.Bundle;

import com.example.task2.R;
import com.example.task2.models.CellOperation;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MapsFragment extends BenchmarkFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.submitList((List<CellOperation>) createItemsList());
//        adapter.setItems(createItemsList());
    }

    private Collection<CellOperation> createItemsList() {
        return Arrays.asList(
                new CellOperation(R.string.adding_new, R.string.treemap, NA),
                new CellOperation(R.string.adding_new, R.string.hashmap, NA),
                new CellOperation(R.string.search_by_key, R.string.treemap, NA),
                new CellOperation(R.string.search_by_key, R.string.hashmap, NA),
                new CellOperation(R.string.removing, R.string.treemap, NA),
                new CellOperation(R.string.removing, R.string.hashmap, NA)
        );
    }


    @Override
    protected void runBenchmark(int number) {

    }
}
