package com.example.task2.ui.benchmark;

import com.example.task2.R;
import com.example.task2.models.CellOperation;

import java.util.Arrays;
import java.util.List;

public class CollectionsFragment extends BenchmarksFragment {

    protected List<CellOperation> createItemsList() {
        return Arrays.asList(
                new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na),
                new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na),
                new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, R.string.na),
                new CellOperation(R.string.adding_in_the_middle, R.string.arraylist, R.string.na),
                new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, R.string.na),
                new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, R.string.na),
                new CellOperation(R.string.adding_in_the_end, R.string.arraylist, R.string.na),
                new CellOperation(R.string.adding_in_the_end, R.string.linkedlist, R.string.na),
                new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, R.string.na)
//                new CellOperation(R.string.search_by_value, R.string.arraylist, NA),
//                new CellOperation(R.string.search_by_value, R.string.linkedlist, NA),
//                new CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, NA),
//                new CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, NA),
//                new CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, NA),
//                new CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist, NA),
//                new CellOperation(R.string.removing_in_the_middle, R.string.arraylist, NA),
//                new CellOperation(R.string.removing_in_the_middle, R.string.linkedlist, NA),
//                new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, NA),
//                new CellOperation(R.string.removing_in_the_end, R.string.arraylist, NA),
//                new CellOperation(R.string.removing_in_the_end, R.string.linkedlist, NA),
//                new CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist, NA)
        );
    }
}
