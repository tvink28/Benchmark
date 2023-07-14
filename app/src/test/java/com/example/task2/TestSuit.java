package com.example.task2;


import com.example.task2.BenchmarkTests.CollectionBenchmarkTest;
import com.example.task2.BenchmarkTests.MapBenchmarkTest;
import com.example.task2.ViewModelTests.BenchmarksViewModelTest;

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
