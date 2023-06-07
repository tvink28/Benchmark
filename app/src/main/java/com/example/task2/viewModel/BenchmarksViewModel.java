package com.example.task2.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.task2.models.Benchmark;
import com.example.task2.models.CellOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BenchmarksViewModel extends ViewModel {
    private final Benchmark benchmark;
    private final MutableLiveData<List<CellOperation>> cellOperationsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> allTasksCompletedLiveData = new MutableLiveData<>();
    private ExecutorService executorService;


    public BenchmarksViewModel(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    public MutableLiveData<List<CellOperation>> getCellOperationsLiveData() {
        return cellOperationsLiveData;
    }

    public MutableLiveData<Boolean> getAllTasksCompletedLiveData() {
        return allTasksCompletedLiveData;
    }

    public void updateCellOperationsList(boolean setRunning) {
        List<CellOperation> cellOperations = benchmark.createItemsList(setRunning);
        cellOperationsLiveData.setValue(cellOperations);
    }

    public void runBenchmark(int number) {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final List<CellOperation> operations = benchmark.createItemsList(true);
        cellOperationsLiveData.postValue(new ArrayList<>(operations));

        final AtomicInteger completedTasks = new AtomicInteger(0);
        for (int position = 0; position < operations.size(); position++) {
            final int pos = position;
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final CellOperation cell = operations.get(pos);
                final long operationTime = benchmark.measureTime(cell, number);

                final CellOperation update = cell.withTime(Math.toIntExact(operationTime));
                operations.set(pos, update);
                cellOperationsLiveData.postValue(new ArrayList<>(operations));

                if (completedTasks.incrementAndGet() == operations.size()) {
                    allTasksCompletedLiveData.postValue(true);
                }
            });
        }
        executorService.shutdown();
    }

    public boolean isShutdown() {
        return executorService != null && !executorService.isShutdown();
    }

    public void stopBenchmark() {
        executorService.shutdownNow();
        executorService = null;

        final List<CellOperation> list = new ArrayList<>(Objects.requireNonNull(cellOperationsLiveData.getValue()));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isRunning) {
                list.set(i, list.get(i).withIsRunning(false));
            }
        }
        cellOperationsLiveData.setValue(list);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }
}
