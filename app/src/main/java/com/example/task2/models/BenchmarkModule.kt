package com.example.task2.models

import com.example.task2.models.benchmarks.Benchmark
import com.example.task2.models.benchmarks.CollectionBenchmark
import com.example.task2.models.benchmarks.MapBenchmark
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class BenchmarkModule {

    @Provides
    @Named("CollectionBenchmark")
    fun provideCollectionBenchmark(): Benchmark = CollectionBenchmark()

    @Provides
    @Named("MapBenchmark")
    fun provideMapBenchmark(): Benchmark = MapBenchmark()
}