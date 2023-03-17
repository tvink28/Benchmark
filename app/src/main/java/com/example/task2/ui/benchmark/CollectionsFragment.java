package com.example.task2.ui.benchmark;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.example.task2.R;
import com.example.task2.models.CellOperation;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CollectionsFragment extends BenchmarkFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.setItems(createItemsList());
    }

    private Collection<CellOperation> createItemsList() {
        return Arrays.asList(
                new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, "N/A"),
                new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, "N/A"),
                new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, "N/A"),
                new CellOperation(R.string.adding_in_the_middle, R.string.arraylist, "N/A"),
                new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, "N/A"),
                new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, "N/A"),
                new CellOperation(R.string.adding_in_the_end, R.string.arraylist, "N/A"),
                new CellOperation(R.string.adding_in_the_end, R.string.linkedlist, "N/A"),
                new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, "N/A"),
                new CellOperation(R.string.search_by_value, R.string.arraylist, "N/A"),
                new CellOperation(R.string.search_by_value, R.string.linkedlist, "N/A"),
                new CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, "N/A"),
                new CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, "N/A"),
                new CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, "N/A"),
                new CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist, "N/A"),
                new CellOperation(R.string.removing_in_the_middle, R.string.arraylist, "N/A"),
                new CellOperation(R.string.removing_in_the_middle, R.string.linkedlist, "N/A"),
                new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, "N/A"),
                new CellOperation(R.string.removing_in_the_end, R.string.arraylist, "N/A"),
                new CellOperation(R.string.removing_in_the_end, R.string.linkedlist, "N/A"),
                new CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist, "N/A")
        );
    }


    @Override
    protected void makeBenchmark(int number) {

        List<String> arrayList = benchmarkManager.getCollectionProvider().getArrayList();
        List<String> linkedList = benchmarkManager.getCollectionProvider().getLinkedList();
        List<String> copyOnWriteArrayList = benchmarkManager.getCollectionProvider().getCopyOnWriteArrayList();

        Future<Long> arrayListAddStartResult = benchmarkManager.addStart(arrayList, number);
        Future<Long> linkedListAddStartResult = benchmarkManager.addStart(linkedList, number);
        Future<Long> copyOnWriteArrayListStartResult = benchmarkManager.addStart(copyOnWriteArrayList, number);

        Future<Long> arrayListAddMiddleResult = benchmarkManager.addMiddle(arrayList, number);
        Future<Long> linkedListAddMiddleResult = benchmarkManager.addMiddle(linkedList, number);
        Future<Long> copyOnWriteArrayListMiddleResult = benchmarkManager.addMiddle(copyOnWriteArrayList, number);

        Future<Long> arrayListAddEndResult = benchmarkManager.addEnd(arrayList, number);
        Future<Long> linkedListAddEndResult = benchmarkManager.addEnd(linkedList, number);
        Future<Long> copyOnWriteArrayListEndResult = benchmarkManager.addEnd(copyOnWriteArrayList, number);

        Future<Long> arrayListSearchValueResult = benchmarkManager.searchValue(arrayList, number);
        Future<Long> linkedListSearchValueResult = benchmarkManager.searchValue(linkedList, number);
        Future<Long> copyOnWriteArrayListSearchValueResult = benchmarkManager.searchValue(copyOnWriteArrayList, number);

        Future<Long> arrayListRemoveStartResult = benchmarkManager.removeStart(arrayList, number);
        Future<Long> linkedListRemoveStartResult = benchmarkManager.removeStart(linkedList, number);
        Future<Long> copyOnWriteArrayListRemoveStartResult = benchmarkManager.removeStart(copyOnWriteArrayList, number);

        Future<Long> arrayListRemoveMiddleResult = benchmarkManager.removeMiddle(arrayList, number);
        Future<Long> linkedListRemoveMiddleResult = benchmarkManager.removeMiddle(linkedList, number);
        Future<Long> copyOnWriteArrayListRemoveMiddleResult = benchmarkManager.removeMiddle(copyOnWriteArrayList, number);

        Future<Long> arrayListRemoveEndResult = benchmarkManager.removeEnd(arrayList, number);
        Future<Long> linkedListRemoveEndResult = benchmarkManager.removeEnd(linkedList, number);
        Future<Long> copyOnWriteArrayListRemoveEndResult = benchmarkManager.removeEnd(copyOnWriteArrayList, number);

        Handler handler = new Handler(Looper.myLooper());

        handler.post(() -> {
            try {
                String arrayListAddStartTime = String.valueOf(arrayListAddStartResult.get());
                String linkedListAddStartTime = String.valueOf(linkedListAddStartResult.get());
                String copyOnWriteArrayListAddStartTime = String.valueOf(copyOnWriteArrayListStartResult.get());

                String arrayListAddMiddleTime = String.valueOf(arrayListAddMiddleResult.get());
                String linkedListAddMiddleTime = String.valueOf(linkedListAddMiddleResult.get());
                String copyOnWriteArrayListAddMiddleListTime = String.valueOf(copyOnWriteArrayListMiddleResult.get());

                String arrayListAddEndTime = String.valueOf(arrayListAddEndResult.get());
                String linkedListAddEndTime = String.valueOf(linkedListAddEndResult.get());
                String copyOnWriteArrayListAddEndTime = String.valueOf(copyOnWriteArrayListEndResult.get());

                String arrayListSearchValueTime = String.valueOf(arrayListSearchValueResult.get());
                String linkedListSearchValueTime = String.valueOf(linkedListSearchValueResult.get());
                String copyOnWriteArrayListSearchValueTime = String.valueOf(copyOnWriteArrayListSearchValueResult.get());

                String arrayListRemoveStartTime = String.valueOf(arrayListRemoveStartResult.get());
                String linkedListRemoveStartTime = String.valueOf(linkedListRemoveStartResult.get());
                String copyOnWriteArrayListRemoveStartTime = String.valueOf(copyOnWriteArrayListRemoveStartResult.get());

                String arrayListRemoveMiddleTime = String.valueOf(arrayListRemoveMiddleResult.get());
                String linkedListRemoveMiddleTime = String.valueOf(linkedListRemoveMiddleResult.get());
                String copyOnWriteArrayListRemoveMiddleTime = String.valueOf(copyOnWriteArrayListRemoveMiddleResult.get());


                String arrayListRemoveEndTime = String.valueOf(arrayListRemoveEndResult.get());
                String linkedListRemoveEndTime = String.valueOf(linkedListRemoveEndResult.get());
                String copyOnWriteArrayListRemoveEndTime = String.valueOf(copyOnWriteArrayListRemoveEndResult.get());

                adapter.setItems(
                        Arrays.asList(
                                new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, arrayListAddStartTime),
                                new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, linkedListAddStartTime),
                                new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, copyOnWriteArrayListAddStartTime),
                                new CellOperation(R.string.adding_in_the_middle, R.string.arraylist, arrayListAddMiddleTime),
                                new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, linkedListAddMiddleTime),
                                new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, copyOnWriteArrayListAddMiddleListTime),
                                new CellOperation(R.string.adding_in_the_end, R.string.arraylist, arrayListAddEndTime),
                                new CellOperation(R.string.adding_in_the_end, R.string.linkedlist, linkedListAddEndTime),
                                new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, copyOnWriteArrayListAddEndTime),
                                new CellOperation(R.string.search_by_value, R.string.arraylist, arrayListSearchValueTime),
                                new CellOperation(R.string.search_by_value, R.string.linkedlist, linkedListSearchValueTime),
                                new CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, copyOnWriteArrayListSearchValueTime),
                                new CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, arrayListRemoveStartTime),
                                new CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, linkedListRemoveStartTime),
                                new CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist, copyOnWriteArrayListRemoveStartTime),
                                new CellOperation(R.string.removing_in_the_middle, R.string.arraylist, arrayListRemoveMiddleTime),
                                new CellOperation(R.string.removing_in_the_middle, R.string.linkedlist, linkedListRemoveMiddleTime),
                                new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, copyOnWriteArrayListRemoveMiddleTime),
                                new CellOperation(R.string.removing_in_the_end, R.string.arraylist, arrayListRemoveEndTime),
                                new CellOperation(R.string.removing_in_the_end, R.string.linkedlist, linkedListRemoveEndTime),
                                new CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist, copyOnWriteArrayListRemoveEndTime)
                        )
                );

                recyclerView.post(() -> adapter.notifyDataSetChanged());
                System.out.println("ArrayList addStart time: " + arrayListAddStartTime);
                System.out.println("LinkedList addMiddle time: " + linkedListAddStartTime);
                System.out.println("CopyOnWriteArrayList addEnd time: " + copyOnWriteArrayListAddStartTime);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        benchmarkManager.shutdown();
    }
}
