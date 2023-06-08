package com.example.task2.ui.benchmark;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.task2.R;
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
    private final MutableLiveData<Integer> validNumberLiveData = new MutableLiveData<>();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private ExecutorService executorService;

    public BenchmarksViewModel(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    public LiveData<List<CellOperation>> getCellOperationsLiveData() {
        return cellOperationsLiveData;
    }

    public LiveData<Boolean> getAllTasksCompletedLiveData() {
        return allTasksCompletedLiveData;
    }

    public LiveData<Integer> getValidNumberLiveData() {
        return validNumberLiveData;
    }

    public void onButtonClicked(String input) {
        if (executorService != null && !executorService.isShutdown()) {
            stopBenchmark();
        } else {
            final int number = Integer.parseInt(input);
            runBenchmark(number);
        }
    }

    public void validateNumber(String input) {
        int number;
        Integer errorMessage = null;

        try {
            number = Integer.parseInt(input);
            if (number < 1) {
                errorMessage = R.string.error_count;
            }
        } catch (NumberFormatException e) {
            errorMessage = R.string.error_valid;
        }

        validNumberLiveData.setValue(errorMessage);
    }

    public void onCreate() {
        cellOperationsLiveData.setValue(benchmark.createItemsList(false));
    }

    public int getNumberOfColumns() {
        return benchmark.getNumberOfColumns();
    }

    public void runBenchmark(int number) {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final List<CellOperation> operations = benchmark.createItemsList(true);
        cellOperationsLiveData.postValue(new ArrayList<>(operations));

        final AtomicInteger completedTasks = new AtomicInteger(0);
        for (int position = 0; position < operations.size(); position++) {
            final int pos = position;
            executorService.submit(() -> {
                final CellOperation cell = operations.get(pos);
                final long operationTime = benchmark.measureTime(cell, number);

                final CellOperation update = cell.withTime(Math.toIntExact(operationTime));
                operations.set(pos, update);
                handler.post(() -> cellOperationsLiveData.setValue(new ArrayList<>(operations)));

                if (completedTasks.incrementAndGet() == operations.size()) {
                    allTasksCompletedLiveData.postValue(true);
                }
            });
        }
        executorService.shutdown();
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
