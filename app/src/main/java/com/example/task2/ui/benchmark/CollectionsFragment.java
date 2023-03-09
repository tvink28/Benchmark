package com.example.task2.ui.benchmark;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.task2.R;
import com.example.task2.models.BenchmarkManager;
import com.example.task2.models.CellOperation;
import com.example.task2.models.MyApplication;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class CollectionsFragment extends BaseFragment {

    @Inject
    BenchmarkManager benchmarkManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.setItems(createItemsList());
    }

    private Collection<CellOperation> createItemsList() {
        return Arrays.asList(
                new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist),
                new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist),
                new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist),
                new CellOperation(R.string.adding_in_the_middle, R.string.arraylist),
                new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist),
                new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist),
                new CellOperation(R.string.adding_in_the_end, R.string.arraylist),
                new CellOperation(R.string.adding_in_the_end, R.string.linkedlist),
                new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist),
                new CellOperation(R.string.search_by_value, R.string.arraylist),
                new CellOperation(R.string.search_by_value, R.string.linkedlist),
                new CellOperation(R.string.search_by_value, R.string.copyonwritearraylist),
                new CellOperation(R.string.removing_in_the_beginning, R.string.arraylist),
                new CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist),
                new CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist),
                new CellOperation(R.string.removing_in_the_middle, R.string.arraylist),
                new CellOperation(R.string.removing_in_the_middle, R.string.linkedlist),
                new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist),
                new CellOperation(R.string.removing_in_the_end, R.string.arraylist),
                new CellOperation(R.string.removing_in_the_end, R.string.linkedlist),
                new CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist)
        );
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MyApplication) context.getApplicationContext()).getAppComponent().inject(this);
    }


    @Override
    protected int getValidationMin() {
        return 1;
    }

    @Override
    protected int getValidationMax() {
        return 3;
    }
}