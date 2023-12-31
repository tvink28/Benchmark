@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.task2

import com.example.task2.models.benchmarks.CellOperationTest
import com.example.task2.models.benchmarks.CollectionBenchmarkTest
import com.example.task2.models.benchmarks.MapBenchmarkTest
import com.example.task2.ui.benchmark.BenchmarksViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(CollectionBenchmarkTest::class, MapBenchmarkTest::class, BenchmarksViewModelTest::class, CellOperationTest::class)
class TestSuit
