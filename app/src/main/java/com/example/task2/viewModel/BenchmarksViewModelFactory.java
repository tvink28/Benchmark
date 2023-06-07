package com.example.task2.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.task2.models.Benchmark;

public class BenchmarksViewModelFactory implements ViewModelProvider.Factory {
    private final Benchmark benchmark;

    public BenchmarksViewModelFactory(Benchmark benchmark) {
        this.benchmark = benchmark;
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
