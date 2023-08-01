package com.example.task2;

import com.example.task2.models.benchmarks.CellOperationTest;
import com.example.task2.models.benchmarks.CollectionBenchmarkTest;
import com.example.task2.models.benchmarks.MapBenchmarkTest;
import com.example.task2.ui.benchmark.BenchmarksViewModelTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CollectionBenchmarkTest.class,
        MapBenchmarkTest.class,
        BenchmarksViewModelTest.class,
        CellOperationTest.class
})
public class TestSuit {
}
