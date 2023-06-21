package com.example.task2.ui.benchmark;

import com.example.task2.models.Benchmark;
import com.example.task2.models.CollectionBenchmark;
import com.example.task2.models.MapBenchmark;

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
