package com.example.task2

import android.app.Application
import com.example.task2.models.BenchmarkComponent
import com.example.task2.models.DaggerBenchmarkComponent

class BenchmarksApp : Application() {

    val component: BenchmarkComponent?
        get() = Companion.component

    companion object {
        @JvmStatic
        var instance: BenchmarksApp? = null
            private set
        private var component: BenchmarkComponent? = null

        @JvmStatic
        fun setAppComponent(testComponent: BenchmarkComponent?) {
            component = testComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Companion.component = DaggerBenchmarkComponent.create()
    }
}