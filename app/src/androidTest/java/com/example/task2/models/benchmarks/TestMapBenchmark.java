package com.example.task2.models.benchmarks;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.task2.R;

import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TestMapBenchmark extends TestBenchmark {

    @Override
    protected List<CellOperation> createItemsList() {
        return new MapBenchmark().createItemsList(false);
    }

    @Override
    public void setUp() {
        super.setUp();
        onView(withText(R.string.tab_maps)).perform(click());
    }
}
