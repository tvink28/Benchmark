package com.example.task2;

import com.example.task2.models.benchmarks.Benchmark;
import com.example.task2.models.benchmarks.FakeCollectionBenchmark;
import com.example.task2.models.benchmarks.FakeMapBenchmark;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class TestBenchmarkModule {

    @Provides
    @Named("CollectionBenchmark")
    public Benchmark provideCollectionBenchmark() {
        return new FakeCollectionBenchmark();
    }

    @Provides
    @Named("MapBenchmark")
    public Benchmark provideMapBenchmark() {
        return new FakeMapBenchmark();
    }
}
