package com.example.task2.models;

import com.example.task2.ui.benchmark.CollectionsFragment;

import dagger.Component;

@Component(modules = {AppModule.class})
public interface AppComponent {

    BenchmarkManager provideBenchmarkManager();

    void inject(CollectionsFragment collectionsFragment);
}
