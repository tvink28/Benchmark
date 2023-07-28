package com.example.task2;


import com.example.task2.models.benchmarks.TestCollectionBenchmark;
import com.example.task2.models.benchmarks.TestMapBenchmark;
import com.example.task2.ui.benchmark.TestActivity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestCollectionBenchmark.class,
        TestMapBenchmark.class,
        TestActivity.class
})
public class TestSuitUI {
}
