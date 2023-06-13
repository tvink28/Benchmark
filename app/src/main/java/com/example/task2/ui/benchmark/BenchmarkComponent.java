package com.example.task2.ui.benchmark;

import com.example.task2.models.Benchmark;

import dagger.Component;

@Component(modules = {BenchmarkModule.class})
public interface BenchmarkComponent {
    BenchmarksViewModelFactory benchmarksViewModelFactory();

    Benchmark benchmark();

    void inject(BenchmarksFragment fragment);

    @Component.Factory
    interface Factory {
        BenchmarkComponent create(BenchmarkModule module);
    }
}
