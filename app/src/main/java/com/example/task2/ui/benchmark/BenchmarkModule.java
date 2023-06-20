package com.example.task2.ui.benchmark;

import com.example.task2.models.Benchmark;
import com.example.task2.models.CollectionBenchmark;
import com.example.task2.models.MapBenchmark;

import dagger.Module;
import dagger.Provides;

@Module
public class BenchmarkModule {

    @Provides
    public Benchmark provideCollectionBenchmark() {
        return new CollectionBenchmark();
    }

    @Provides
    public Benchmark provideMapBenchmark() {
        return new MapBenchmark();
    }

    @Provides
    public BenchmarkModule getBenchmarkModule() {
        return this;
    }
}
