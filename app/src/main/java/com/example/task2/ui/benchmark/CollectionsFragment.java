package com.example.task2.ui.benchmark;

import android.os.Bundle;
import android.util.Log;

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


    private final List<CellOperation> operations = (List<CellOperation>) createItemsList();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.submitList(operations);
//        adapter.setItems(createItemsList());
    }


    private Collection<CellOperation> createItemsList() {
        return Arrays.asList(new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na), new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na), new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, R.string.na), new CellOperation(R.string.adding_in_the_middle, R.string.arraylist, R.string.na), new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, R.string.na), new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, R.string.na), new CellOperation(R.string.adding_in_the_end, R.string.arraylist, R.string.na), new CellOperation(R.string.adding_in_the_end, R.string.linkedlist, R.string.na), new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, R.string.na)
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

        ExecutorService executorService = Executors.newFixedThreadPool(3);


        for (int position = 0; position < operations.size(); position++) {
            final int pos = position;
            executorService.submit(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CellOperation operation = operations.get(pos);
                List<String> list;

                if (operation.type == R.string.arraylist) {
                    list = new ArrayList<>(Collections.nCopies(number, "test"));
                } else if (operation.type == R.string.linkedlist) {
                    list = new LinkedList<>(Collections.nCopies(number, "test"));
                } else {
                    list = new CopyOnWriteArrayList<>(Collections.nCopies(number, "test"));
                }

                long result;
                if (operation.action == R.string.adding_in_the_beginning) {
                    result = bm.addStart(list, list.size());
                } else if (operation.action == R.string.adding_in_the_middle) {
                    result = bm.addMiddle(list, list.size());
                } else {
                    result = bm.addEnd(list, list.size());
                }

                int time = (int) result;
                updateCell(pos, time);

                Log.d("CollectionsFragment", "Thread ID: " + Thread.currentThread().getId() + ", Position: " + pos);
            });
        }
        executorService.shutdown();
    }

    private void updateCell(int position, int result) {
        CellOperation cellOperation = operations.get(position);
        CellOperation updatedCellOperation = cellOperation.withNewTime(result);
        operations.set(position, updatedCellOperation);
        handler.post(() -> adapter.notifyItemChanged(position));
    }


//    private void updateCell(int position, int result) {
//        List<CellOperation> currentList = new ArrayList<>(adapter.getCurrentList());
//        CellOperation cellOperation = currentList.get(position);
//        cellOperation.time = result;
//        adapter.notifyItemChanged(position);
////        adapter.submitList(currentList);
//    }


}
