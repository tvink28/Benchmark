package com.example.task2.ui.benchmark;

import android.os.Bundle;

import com.example.task2.R;
import com.example.task2.models.CellOperation;

import java.util.Arrays;
import java.util.Collection;

public class MapsFragment extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.setItems(createItemsList());
    }


    private Collection<CellOperation> createItemsList() {
        return Arrays.asList(
                new CellOperation(R.string.adding_new, R.string.treemap),
                new CellOperation(R.string.adding_new, R.string.hashmap),
                new CellOperation(R.string.search_by_key, R.string.treemap),
                new CellOperation(R.string.search_by_key, R.string.hashmap),
                new CellOperation(R.string.removing, R.string.treemap),
                new CellOperation(R.string.removing, R.string.hashmap)
        );
    }
}