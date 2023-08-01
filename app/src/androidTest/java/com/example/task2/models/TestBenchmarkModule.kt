package com.example.task2.models

import com.example.task2.models.benchmarks.Benchmark
import com.example.task2.models.benchmarks.FakeCollectionBenchmark
import com.example.task2.models.benchmarks.FakeMapBenchmark
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class TestBenchmarkModule {

    @Provides
    @Named("CollectionBenchmark")
    fun provideCollectionBenchmark(): Benchmark = FakeCollectionBenchmark()

    @Provides
    @Named("MapBenchmark")
    fun provideMapBenchmark(): Benchmark = FakeMapBenchmark()
}
