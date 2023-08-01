package com.example.task2.ui.benchmark;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.task2.models.benchmarks.CellOperation;
import com.example.task2.models.benchmarks.CollectionBenchmark;

import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TestCollectionBenchmark extends TestBenchmark {

    @Override
    protected List<CellOperation> createItemsList() {
        return new CollectionBenchmark().createItemsList(false);
    }
}
