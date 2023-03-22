package com.example.task2.ui.benchmark;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.task2.R;
import com.example.task2.models.CellOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionsFragment extends BenchmarkFragment {

    private static final int COLLECTION_COUNT = 3;
    private static final int OPERATION_COUNT = 3;

    private int getCellPosition(int collectionId, int operationId) {
        int collectionIndex = -1;
        int operationIndex = -1;

        if (collectionId == R.string.arraylist) {
            collectionIndex = 0;
        } else if (collectionId == R.string.linkedlist) {
            collectionIndex = 1;
        } else if (collectionId == R.string.copyonwritearraylist) {
            collectionIndex = 2;
        }

        if (operationId == R.string.adding_in_the_beginning) {
            operationIndex = 0;
        } else if (operationId == R.string.adding_in_the_middle) {
            operationIndex = 1;
        } else if (operationId == R.string.adding_in_the_end) {
            operationIndex = 2;
        }

        return operationIndex * COLLECTION_COUNT + collectionIndex;
    }

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

//        CompletableFuture<Long> arrayListAddStartResult = benchmarkManager.addStart(new ArrayList<>(), number);
//        CompletableFuture<Long> linkedListAddStartResult = benchmarkManager.addStart(new LinkedList<>(), number);
//        CompletableFuture<Long> copyOnWriteArrayListStartResult = benchmarkManager.addStart(new CopyOnWriteArrayList<>(), number);
//
//        CompletableFuture<Long> arrayListAddMiddleResult = benchmarkManager.addMiddle(new ArrayList<>(), number);
//        CompletableFuture<Long> linkedListAddMiddleResult = benchmarkManager.addMiddle(new LinkedList<>(), number);
//        CompletableFuture<Long> copyOnWriteArrayListMiddleResult = benchmarkManager.addMiddle(new CopyOnWriteArrayList<>(), number);
//
//        CompletableFuture<Long> arrayListAddEndResult = benchmarkManager.addEnd(new ArrayList<>(), number);
//        CompletableFuture<Long> linkedListAddEndResult = benchmarkManager.addEnd(new LinkedList<>(), number);
//        CompletableFuture<Long> copyOnWriteArrayListEndResult = benchmarkManager.addEnd(new CopyOnWriteArrayList<>(), number);

        benchmarkManager.addStart(new ArrayList<>(), number)
                .thenAccept(arrayListAddStartResult -> adapter.updateCell(getCellPosition(R.string.arraylist, R.string.adding_in_the_beginning), String.valueOf(arrayListAddStartResult)));

        benchmarkManager.addStart(new LinkedList<>(), number)
                .thenAccept(linkedListAddStartResult -> adapter.updateCell(getCellPosition(R.string.linkedlist, R.string.adding_in_the_beginning), String.valueOf(linkedListAddStartResult)));

        benchmarkManager.addStart(new CopyOnWriteArrayList<>(), number)
                .thenAccept(copyOnWriteArrayListStartResult -> adapter.updateCell(getCellPosition(R.string.copyonwritearraylist, R.string.adding_in_the_beginning), String.valueOf(copyOnWriteArrayListStartResult)));


        benchmarkManager.addMiddle(new ArrayList<>(), number)
                .thenAccept(arrayListAddMiddleResult -> adapter.updateCell(getCellPosition(R.string.arraylist, R.string.adding_in_the_middle), String.valueOf(arrayListAddMiddleResult)));

        benchmarkManager.addMiddle(new LinkedList<>(), number)
                .thenAccept(linkedListAddMiddleResult -> adapter.updateCell(getCellPosition(R.string.linkedlist, R.string.adding_in_the_middle), String.valueOf(linkedListAddMiddleResult)));

        benchmarkManager.addMiddle(new CopyOnWriteArrayList<>(), number)
                .thenAccept(copyOnWriteArrayListMiddleResult -> adapter.updateCell(getCellPosition(R.string.copyonwritearraylist, R.string.adding_in_the_middle), String.valueOf(copyOnWriteArrayListMiddleResult)));


        benchmarkManager.addEnd(new ArrayList<>(), number)
                .thenAccept(arrayListAddEndResult -> adapter.updateCell(getCellPosition(R.string.arraylist, R.string.adding_in_the_end), String.valueOf(arrayListAddEndResult)));

        benchmarkManager.addEnd(new LinkedList<>(), number)
                .thenAccept(linkedListAddEndResult -> adapter.updateCell(getCellPosition(R.string.linkedlist, R.string.adding_in_the_end), String.valueOf(linkedListAddEndResult)));

        benchmarkManager.addEnd(new CopyOnWriteArrayList<>(), number)
                .thenAccept(copyOnWriteArrayListEndResult -> adapter.updateCell(getCellPosition(R.string.copyonwritearraylist, R.string.adding_in_the_end), String.valueOf(copyOnWriteArrayListEndResult)));


//        Future<Long> arrayListSearchValueResult = benchmarkManager.searchValue(new ArrayList<>(), number);
//        Future<Long> linkedListSearchValueResult = benchmarkManager.searchValue(new LinkedList<>(), number);
//        Future<Long> copyOnWriteArrayListSearchValueResult = benchmarkManager.searchValue(new CopyOnWriteArrayList<>(), number);
//
//        Future<Long> arrayListRemoveStartResult = benchmarkManager.removeStart(new ArrayList<>(), number);
//        Future<Long> linkedListRemoveStartResult = benchmarkManager.removeStart(new LinkedList<>(), number);
//        Future<Long> copyOnWriteArrayListRemoveStartResult = benchmarkManager.removeStart(new CopyOnWriteArrayList<>(), number);
//
//        Future<Long> arrayListRemoveMiddleResult = benchmarkManager.removeMiddle(new ArrayList<>(), number);
//        Future<Long> linkedListRemoveMiddleResult = benchmarkManager.removeMiddle(new LinkedList<>(), number);
//        Future<Long> copyOnWriteArrayListRemoveMiddleResult = benchmarkManager.removeMiddle(new CopyOnWriteArrayList<>(), number);
//
//        Future<Long> arrayListRemoveEndResult = benchmarkManager.removeEnd(new ArrayList<>(), number);
//        Future<Long> linkedListRemoveEndResult = benchmarkManager.removeEnd(new LinkedList<>(), number);
//        Future<Long> copyOnWriteArrayListRemoveEndResult = benchmarkManager.removeEnd(new CopyOnWriteArrayList<>(), number);


//        Handler handler = new Handler(Looper.myLooper());
//
//        handler.post(() -> {
//            try {
//                String arrayListAddStartTime = String.valueOf(arrayListAddStartResult.get());
//                String linkedListAddStartTime = String.valueOf(linkedListAddStartResult.get());
//                String copyOnWriteArrayListAddStartTime = String.valueOf(copyOnWriteArrayListStartResult.get());
//
//                String arrayListAddMiddleTime = String.valueOf(arrayListAddMiddleResult.get());
//                String linkedListAddMiddleTime = String.valueOf(linkedListAddMiddleResult.get());
//                String copyOnWriteArrayListAddMiddleListTime = String.valueOf(copyOnWriteArrayListMiddleResult.get());
//
//                String arrayListAddEndTime = String.valueOf(arrayListAddEndResult.get());
//                String linkedListAddEndTime = String.valueOf(linkedListAddEndResult.get());
//                String copyOnWriteArrayListAddEndTime = String.valueOf(copyOnWriteArrayListEndResult.get());


//                String arrayListSearchValueTime = String.valueOf(arrayListSearchValueResult.get());
//                String linkedListSearchValueTime = String.valueOf(linkedListSearchValueResult.get());
//                String copyOnWriteArrayListSearchValueTime = String.valueOf(copyOnWriteArrayListSearchValueResult.get());
//
//                String arrayListRemoveStartTime = String.valueOf(arrayListRemoveStartResult.get());
//                String linkedListRemoveStartTime = String.valueOf(linkedListRemoveStartResult.get());
//                String copyOnWriteArrayListRemoveStartTime = String.valueOf(copyOnWriteArrayListRemoveStartResult.get());
//
//                String arrayListRemoveMiddleTime = String.valueOf(arrayListRemoveMiddleResult.get());
//                String linkedListRemoveMiddleTime = String.valueOf(linkedListRemoveMiddleResult.get());
//                String copyOnWriteArrayListRemoveMiddleTime = String.valueOf(copyOnWriteArrayListRemoveMiddleResult.get());
//
//
//                String arrayListRemoveEndTime = String.valueOf(arrayListRemoveEndResult.get());
//                String linkedListRemoveEndTime = String.valueOf(linkedListRemoveEndResult.get());
//                String copyOnWriteArrayListRemoveEndTime = String.valueOf(copyOnWriteArrayListRemoveEndResult.get());

//                requireView().post(() -> adapter.setItems(
//                                Arrays.asList(
//                                        new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, arrayListAddStartTime),
//                                        new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, linkedListAddStartTime),
//                                        new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, copyOnWriteArrayListAddStartTime),
//                                        new CellOperation(R.string.adding_in_the_middle, R.string.arraylist, arrayListAddMiddleTime),
//                                        new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, linkedListAddMiddleTime),
//                                        new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, copyOnWriteArrayListAddMiddleListTime),
//                                        new CellOperation(R.string.adding_in_the_end, R.string.arraylist, arrayListAddEndTime),
//                                        new CellOperation(R.string.adding_in_the_end, R.string.linkedlist, linkedListAddEndTime),
//                                        new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, copyOnWriteArrayListAddEndTime)


//                                        new CellOperation(R.string.search_by_value, R.string.arraylist, arrayListSearchValueTime),
//                                        new CellOperation(R.string.search_by_value, R.string.linkedlist, linkedListSearchValueTime),
//                                        new CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, copyOnWriteArrayListSearchValueTime),
//                                        new CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, arrayListRemoveStartTime),
//                                        new CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, linkedListRemoveStartTime),
//                                        new CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist, copyOnWriteArrayListRemoveStartTime),
//                                        new CellOperation(R.string.removing_in_the_middle, R.string.arraylist, arrayListRemoveMiddleTime),
//                                        new CellOperation(R.string.removing_in_the_middle, R.string.linkedlist, linkedListRemoveMiddleTime),
//                                        new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, copyOnWriteArrayListRemoveMiddleTime),
//                                        new CellOperation(R.string.removing_in_the_end, R.string.arraylist, arrayListRemoveEndTime),
//                                        new CellOperation(R.string.removing_in_the_end, R.string.linkedlist, linkedListRemoveEndTime),
//                                        new CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist, copyOnWriteArrayListRemoveEndTime)
//                                )
//                        )
//                );
//
//                System.out.println("ArrayList addStart time: " + arrayListAddStartTime);
//                System.out.println("LinkedList addMiddle time: " + linkedListAddStartTime);
//                System.out.println("CopyOnWriteArrayList addEnd time: " + copyOnWriteArrayListAddStartTime);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
//        benchmarkManager.shutdown();
    }
}
