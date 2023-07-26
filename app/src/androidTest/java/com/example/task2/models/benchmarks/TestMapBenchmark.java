package com.example.task2.models.benchmarks;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.task2.matchers.Matchers.withAlpha;
import static com.example.task2.matchers.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.Matchers.containsString;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.task2.BenchmarksApp;
import com.example.task2.DaggerTestBenchmarkComponent;
import com.example.task2.R;
import com.example.task2.TestBenchmarkComponent;
import com.example.task2.TestBenchmarkModule;
import com.example.task2.ui.MainActivity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestMapBenchmark {

    private final int ITEM_COUNT = 6;
    private final String TIME = "99";

    @BeforeClass
    public static void set() {
        TestBenchmarkComponent appComponent = DaggerTestBenchmarkComponent.builder().testBenchmarkModule(new TestBenchmarkModule()).build();
        BenchmarksApp.setAppComponent(appComponent);
    }

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
        onView(withText(R.string.tab_maps)).perform(click());

    }

    @Test
    public void test_startCalculations() {

        onView(withId(R.id.editText)).perform(typeText("5"));
        onView(withId(R.id.editText)).check(matches(withText("5")));
        pressBack();
        onView(withId(R.id.btnStopStart)).perform(click());

        for (int i = 0; i < ITEM_COUNT; i++) {
            onView(withId(R.id.rv)).perform(scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.progressBar))
                    .check(matches(withAlpha(1f)));
        }

        SystemClock.sleep(1100 * ITEM_COUNT);

        for (int i = 0; i < ITEM_COUNT; i++) {
            onView(withId(R.id.rv)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action))
                    .check(matches(withText(containsString(TIME))));
        }
    }

    @Test
    public void test_stopCalculations() {

        final String naTime = InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.na);

        onView(withId(R.id.editText)).perform(typeText("5"));
        onView(withId(R.id.editText)).check(matches(withText("5")));
        pressBack();
        onView(withId(R.id.btnStopStart)).perform(click());

        SystemClock.sleep(1100);

        onView(withId(R.id.btnStopStart)).perform(click());

        for (int i = 0; i < 1; i++) {
            onView(withId(R.id.rv)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action))
                    .check(matches(withText(containsString(TIME))));
        }

        for (int i = 2; i < ITEM_COUNT; i++) {
            onView(withId(R.id.rv)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action))
                    .check(matches(withText(containsString(naTime))));
        }
    }
}
