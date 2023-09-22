package com.example.task2.models

import com.example.task2.models.benchmarks.CollectionBenchmark
import com.example.task2.models.benchmarks.MapBenchmark
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class])
interface RoomComponent {
    fun inject(collectionBenchmark: CollectionBenchmark)
    fun inject(mapBenchmark: MapBenchmark)
}