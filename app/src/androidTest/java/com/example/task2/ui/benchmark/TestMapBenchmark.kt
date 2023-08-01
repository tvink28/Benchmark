package com.example.task2.ui.benchmark

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.task2.R
import com.example.task2.models.benchmarks.CellOperation
import com.example.task2.models.benchmarks.MapBenchmark
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestMapBenchmark : TestBenchmark() {
    override fun createItemsList(): List<CellOperation> = MapBenchmark().createItemsList(false)

    override fun setUp() {
        super.setUp()
        Espresso.onView(ViewMatchers.withText(R.string.tab_maps)).perform(ViewActions.click())
    }
}
