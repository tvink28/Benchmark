package com.example.task2.models;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    BenchmarkManager provideBenchmarkManager() {
        return new BenchmarkManager(new BenchmarkManager.CollectionProvider());
    }
}
