package com.example.task2.models

import com.example.task2.ui.benchmark.BenchmarksViewModelFactory
import dagger.Component

@Component(modules = [BenchmarkModule::class])
interface BenchmarkComponent {
    fun inject(factory: BenchmarksViewModelFactory?)
}
