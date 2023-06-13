package com.example.task2.ui.benchmark;

import com.example.task2.models.Benchmark;
import com.example.task2.models.CollectionBenchmark;
import com.example.task2.models.MapBenchmark;

import dagger.Module;
import dagger.Provides;

@Module
public class BenchmarkModule {
    private final int benchmarkType;

    public BenchmarkModule(int benchmarkType) {
        this.benchmarkType = benchmarkType;
    }

    @Provides
    public Benchmark provideBenchmark() {
        if (benchmarkType == 0) {
            return new CollectionBenchmark();
        } else {
            return new MapBenchmark();
        }
    }

    @Provides
    public BenchmarksViewModelFactory provideBenchmarksViewModelFactory(Benchmark benchmark) {
        return new BenchmarksViewModelFactory(benchmark);
    }
}
