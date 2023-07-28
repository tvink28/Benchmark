package com.example.task2.models.benchmarks;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TestCollectionBenchmark extends TestBenchmark {

    @Override
    protected List<CellOperation> createItemsList() {
        return new CollectionBenchmark().createItemsList(false);
    }
}
