package com.example.task2

import com.example.task2.ui.TestActivity
import com.example.task2.ui.benchmark.TestCollectionBenchmark
import com.example.task2.ui.benchmark.TestMapBenchmark
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(TestCollectionBenchmark::class, TestMapBenchmark::class, TestActivity::class)
class TestSuitUI 