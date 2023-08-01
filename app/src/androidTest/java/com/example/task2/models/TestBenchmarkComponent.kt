package com.example.task2.models

import com.example.task2.ui.benchmark.BenchmarksViewModelFactory
import dagger.Component

@Component(modules = [TestBenchmarkModule::class])
interface TestBenchmarkComponent : BenchmarkComponent {
    override fun inject(factory: BenchmarksViewModelFactory?)
}
