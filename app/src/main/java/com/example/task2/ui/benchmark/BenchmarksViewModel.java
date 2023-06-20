package com.example.task2.ui.benchmark;

import android.util.Log;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.task2.R;
import com.example.task2.models.Benchmark;
import com.example.task2.models.CellOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BenchmarksViewModel extends ViewModel {
    private final Benchmark benchmark;
    private final MutableLiveData<List<CellOperation>> cellOperationsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> allTasksCompletedLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> validNumberLiveData = new MutableLiveData<>();
    private Disposable disposable = Disposable.disposed();

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
        if (disposable != null && !disposable.isDisposed()) {
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
        final List<CellOperation> operations = benchmark.createItemsList(true);
        cellOperationsLiveData.postValue(new ArrayList<>(operations));

        disposable = Observable.fromIterable(operations)
                .flatMap(cell -> Observable.fromCallable(() -> {
                    final long operationTime = benchmark.measureTime(cell, number);
                    return new Pair<>(operations.indexOf(cell), cell.withTime(operationTime));
                }))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> allTasksCompletedLiveData.setValue(true))
                .subscribe(pair -> {
                    int position = pair.first;
                    CellOperation updatedCell = pair.second;
                    operations.set(position, updatedCell);
                    cellOperationsLiveData.setValue(new ArrayList<>(operations));
                }, throwable -> Log.e("LOGG:", "Error: " + throwable.getMessage()));
    }

    public void stopBenchmark() {
        disposable.dispose();

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
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
