package com.example.task2.ui.benchmark;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.task2.models.Benchmark;

import javax.inject.Inject;

public class BenchmarksViewModelFactory implements ViewModelProvider.Factory {
    private static final int COLLECTION_BENCHMARK = 0;
    private final int benchmarkType;
    BenchmarkComponent component = DaggerBenchmarkComponent.create();

    @Inject
    public BenchmarksViewModelFactory(int benchmarkType) {
        this.benchmarkType = benchmarkType;
        component.inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BenchmarksViewModel.class)) {
            Benchmark benchmark;
            if (benchmarkType == COLLECTION_BENCHMARK) {
                benchmark = component.getBenchmarkModule().provideCollectionBenchmark();
            } else {
                benchmark = component.getBenchmarkModule().provideMapBenchmark();
            }
            T viewModel = modelClass.cast(new BenchmarksViewModel(benchmark));
            if (viewModel != null) {
                return viewModel;
            }
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
