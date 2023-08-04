package com.example.task2.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.task2.matchers.Matchers.withTabSelected;
import static com.example.task2.models.benchmarks.BenchmarkTypes.LISTS;
import static com.example.task2.models.benchmarks.BenchmarkTypes.MAPS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.task2.R;
import com.example.task2.ui.benchmark.BenchmarksFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestActivity {

    private ActivityScenario<MainActivity> scenario;

    @Before
    public void setUp() {
        scenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void test_swipeViewPager() {
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()));
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(0)));

        onView(withId(R.id.view_pager)).perform(swipeLeft());
        onView(withText(R.string.tab_maps)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(1)));

        onView(withId(R.id.view_pager)).perform(swipeRight());
        onView(withText(R.string.tab_collections)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(0)));
    }

    @Test
    public void test_clickTabs() {
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(0)));
        onView(withText(R.string.tab_maps))
                .check(matches(isCompletelyDisplayed()))
                .perform(click());
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(1)));

        verifyActiveFragment(MAPS);

        onView(withText(R.string.tab_collections))
                .check(matches(isCompletelyDisplayed()))
                .perform(click());
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(0)));

        verifyActiveFragment(LISTS);
    }

    private void verifyActiveFragment(int benchmarkType) {
        scenario.onActivity(activity -> {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment activeFragment = null;

            for (Fragment fragment : fragmentManager.getFragments()) {
                if (fragment.isVisible()) {
                    activeFragment = fragment;
                }
            }

            assertNotNull(activeFragment);
            assertTrue(activeFragment instanceof BenchmarksFragment);
            assertNotNull(activeFragment.getArguments());
            assertEquals(benchmarkType, activeFragment.getArguments().getInt(BenchmarksFragment.ARG_BENCHMARK_TYPE));
        });
    }
}
