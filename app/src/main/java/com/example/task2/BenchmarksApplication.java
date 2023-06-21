package com.example.task2;

import android.app.Application;

import com.example.task2.models.BenchmarkComponent;
import com.example.task2.models.DaggerBenchmarkComponent;

public class BenchmarksApplication extends Application {
    private BenchmarkComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerBenchmarkComponent.create();
    }

    public BenchmarkComponent getComponent() {
        return component;
    }
}
