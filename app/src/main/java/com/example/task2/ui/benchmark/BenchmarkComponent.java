package com.example.task2.ui.benchmark;

import dagger.Component;

@Component(modules = {BenchmarkModule.class})
public interface BenchmarkComponent {

    void inject(BenchmarksViewModelFactory factory);
}
