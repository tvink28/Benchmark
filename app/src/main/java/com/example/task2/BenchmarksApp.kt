package com.example.task2

import android.app.Application
import com.example.task2.models.BenchmarkComponent
import com.example.task2.models.DaggerBenchmarkComponent
import com.example.task2.models.DaggerRoomComponent
import com.example.task2.models.RoomComponent
import com.example.task2.models.RoomModule

class BenchmarksApp : Application() {

    val roomComponent: RoomComponent?
        get() = Companion.roomComponent

    val component: BenchmarkComponent?
        get() = Companion.component

    companion object {
        @JvmStatic
        var instance: BenchmarksApp? = null
            private set
        private var component: BenchmarkComponent? = null
        private var roomComponent: RoomComponent? = null

        @JvmStatic
        fun setAppComponent(testComponent: BenchmarkComponent?) {
            component = testComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Companion.component = DaggerBenchmarkComponent.create()
        Companion.roomComponent = DaggerRoomComponent.builder()
                .roomModule(RoomModule(applicationContext))
                .build()
    }
}
