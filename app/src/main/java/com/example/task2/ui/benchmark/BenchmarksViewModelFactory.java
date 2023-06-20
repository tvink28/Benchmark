package com.example.task2.ui.benchmark;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.task2.models.Benchmark;

import javax.inject.Inject;

public class BenchmarksViewModelFactory implements ViewModelProvider.Factory {
    private final Benchmark benchmark;

    BenchmarkComponent component = DaggerBenchmarkComponent.create();

    @Inject
    public BenchmarksViewModelFactory(int benchmarkType) {
        component.inject(this);
        if (benchmarkType == 0) {
            benchmark = component.getBenchmarkModule().provideCollectionBenchmark();
        } else {
            benchmark = component.getBenchmarkModule().provideMapBenchmark();
        }
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BenchmarksViewModel.class)) {
            T viewModel = modelClass.cast(new BenchmarksViewModel(benchmark));
            if (viewModel != null) {
                return viewModel;
            }
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
