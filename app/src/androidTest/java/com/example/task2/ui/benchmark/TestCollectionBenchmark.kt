package com.example.task2.ui.benchmark

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.task2.models.benchmarks.CellOperation
import com.example.task2.models.benchmarks.CollectionBenchmark
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestCollectionBenchmark : TestBenchmark() {

    override fun createItemsList(): List<CellOperation> = CollectionBenchmark().createItemsList(false)
}
