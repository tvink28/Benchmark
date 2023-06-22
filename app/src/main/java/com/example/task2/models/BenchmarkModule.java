package com.example.task2.models;

import com.example.task2.models.benchmarks.Benchmark;
import com.example.task2.models.benchmarks.CollectionBenchmark;
import com.example.task2.models.benchmarks.MapBenchmark;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class BenchmarkModule {

    @Provides
    @Named("CollectionBenchmark")
    public Benchmark provideCollectionBenchmark() {
        return new CollectionBenchmark();
    }

    @Provides
    @Named("MapBenchmark")
    public Benchmark provideMapBenchmark() {
        return new MapBenchmark();
    }
}
