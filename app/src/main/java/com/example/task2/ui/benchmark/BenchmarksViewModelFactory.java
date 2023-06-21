package com.example.task2.ui.benchmark;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.task2.models.BenchmarkComponent;
import com.example.task2.models.DaggerBenchmarkComponent;
import com.example.task2.models.benchmarks.Benchmark;
import com.example.task2.models.benchmarks.BenchmarkTypes;

import javax.inject.Inject;
import javax.inject.Named;

public class BenchmarksViewModelFactory implements ViewModelProvider.Factory {
    private final int benchmarkType;

    @Inject
    @Named("CollectionBenchmark")
    Benchmark collectionBenchmark;

    @Inject
    @Named("MapBenchmark")
    Benchmark mapBenchmark;
    BenchmarkComponent component = DaggerBenchmarkComponent.create();

    public BenchmarksViewModelFactory(int benchmarkType) {
        this.benchmarkType = benchmarkType;
        component.inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BenchmarksViewModel.class)) {
            Benchmark benchmark;
            if (benchmarkType == BenchmarkTypes.LISTS) {
                benchmark = collectionBenchmark;
            } else if (benchmarkType == BenchmarkTypes.MAPS) {
                benchmark = mapBenchmark;
            } else {
                throw new IllegalArgumentException("Unknown benchmark type");
            }
            T viewModel = modelClass.cast(new BenchmarksViewModel(benchmark));
            if (viewModel != null) {
                return viewModel;
            }
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
