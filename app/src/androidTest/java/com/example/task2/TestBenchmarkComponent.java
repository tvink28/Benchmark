package com.example.task2;

import com.example.task2.models.BenchmarkComponent;
import com.example.task2.ui.benchmark.BenchmarksViewModelFactory;

import dagger.Component;

@Component(modules = {TestBenchmarkModule.class})
public interface TestBenchmarkComponent extends BenchmarkComponent {
    void inject(BenchmarksViewModelFactory factory);
}
