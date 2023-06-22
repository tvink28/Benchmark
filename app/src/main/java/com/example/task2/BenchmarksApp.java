package com.example.task2;

import android.app.Application;

import com.example.task2.models.BenchmarkComponent;
import com.example.task2.models.DaggerBenchmarkComponent;

public class BenchmarksApp extends Application {
    private static BenchmarksApp instance;
    private BenchmarkComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerBenchmarkComponent.create();
    }

    public static BenchmarksApp getInstance() {
        return instance;
    }

    public BenchmarkComponent getComponent() {
        return component;
    }
}
