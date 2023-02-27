package com.example.task2.ui.benchmark;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.task2.R;
import com.example.task2.models.CellOperation;

import java.util.Arrays;
import java.util.Collection;

public class CollectionsFragment extends BaseFragment {

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


}