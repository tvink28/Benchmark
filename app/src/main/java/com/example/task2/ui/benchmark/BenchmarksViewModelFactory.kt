package com.example.task2.ui.benchmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task2.BenchmarksApp.Companion.instance
import com.example.task2.models.benchmarks.Benchmark
import com.example.task2.models.benchmarks.BenchmarkTypes
import javax.inject.Inject
import javax.inject.Named

class BenchmarksViewModelFactory(private val benchmarkType: Int) : ViewModelProvider.Factory {

    @Inject
    @Named("CollectionBenchmark")
    lateinit var collectionBenchmark: Benchmark

    @Inject
    @Named("MapBenchmark")
    lateinit var mapBenchmark: Benchmark

    init {
        instance!!.component!!.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BenchmarksViewModel::class.java)) {
            val benchmark = when (benchmarkType) {
                BenchmarkTypes.LISTS -> collectionBenchmark
                BenchmarkTypes.MAPS -> mapBenchmark
                else -> throw IllegalArgumentException("Unknown benchmark type")
            }
            val viewModel = modelClass.cast(BenchmarksViewModel(benchmark))
            viewModel?.let { return it }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}