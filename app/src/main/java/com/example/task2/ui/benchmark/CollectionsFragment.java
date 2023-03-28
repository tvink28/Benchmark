package com.example.task2.ui.benchmark;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.example.task2.R;
import com.example.task2.models.BenchmarkManager;
import com.example.task2.models.CellOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CollectionsFragment extends BenchmarkFragment {


    Handler handler = new Handler(Looper.getMainLooper());


    String arrayList = "1";
    String linkedList = "2";
    String copyOnWriteArrayList = "3";

    ExecutorService executorService = Executors.newFixedThreadPool(3);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.submitList((List<CellOperation>) createItemsList());
//        adapter.setItems(createItemsList());
    }


    private Collection<CellOperation> createItemsList() {
        return Arrays.asList(
                new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, NA),
                new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, NA),
                new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, NA),
                new CellOperation(R.string.adding_in_the_middle, R.string.arraylist, NA),
                new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, NA),
                new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, NA),
                new CellOperation(R.string.adding_in_the_end, R.string.arraylist, NA),
                new CellOperation(R.string.adding_in_the_end, R.string.linkedlist, NA),
                new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, NA)
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


    @Override
    protected void runBenchmark(int number) {
        BenchmarkManager bm = new BenchmarkManager();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int collectionIndex = i;
                final int operationIndex = j;
                final int position = i * 3 + j;
                executorService.submit(() -> {
                    List<String> list;
                    if (collectionIndex == 0) {
                        list = new ArrayList<>(Collections.nCopies(number, "test"));
                    } else if (collectionIndex == 1) {
                        list = new LinkedList<>(Collections.nCopies(number, "test"));
                    } else {
                        list = new CopyOnWriteArrayList<>(Collections.nCopies(number, "test"));
                    }

                    long result;
                    if (operationIndex == 0) {
                        result = bm.addStart(list, list.size());
                    } else if (operationIndex == 1) {
                        result = bm.addMiddle(list, list.size());
                    } else {
                        result = bm.addEnd(list, list.size());
                    }

                    String time = Long.toString(result);
                    handler.post(() -> updateCell(position, time));
                });
            }
        }

    }

    private void updateCell(int position, String result) {
        List<CellOperation> currentList = new ArrayList<>(adapter.getCurrentList());
        CellOperation cellOperation = currentList.get(position);
        cellOperation.setTime(result);
        adapter.notifyItemChanged(position);
//        adapter.submitList(currentList);
    }


}
