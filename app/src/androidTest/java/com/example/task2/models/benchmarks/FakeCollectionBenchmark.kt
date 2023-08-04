package com.example.task2.models.benchmarks

import com.example.task2.Constants

class FakeCollectionBenchmark : CollectionBenchmark(), Benchmark {

    override fun measureTime(item: CellOperation, number: Int): Long {
        try {
            Thread.sleep(Constants.SLEEP_TIME_FOR_MODELS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return Constants.RESULT_COLLECTION
    }
}
