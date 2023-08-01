package com.example.task2.ui

import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.task2.R
import com.example.task2.matchers.Matchers.withTabSelected
import com.example.task2.models.benchmarks.BenchmarkTypes.LISTS
import com.example.task2.models.benchmarks.BenchmarkTypes.MAPS
import com.example.task2.ui.benchmark.BenchmarksFragment
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestActivity {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun test_swipeViewPager() {
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(0)))

        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withText(R.string.tab_maps)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(1)))

        onView(withId(R.id.view_pager)).perform(swipeRight())
        onView(withText(R.string.tab_collections)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(0)))
    }

    @Test
    fun test_clickTabs() {
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(0)))
        onView(withText(R.string.tab_maps))
                .check(matches(isCompletelyDisplayed()))
                .perform(click())
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(1)))

        verifyActiveFragment(MAPS)

        onView(withText(R.string.tab_collections))
                .check(matches(isCompletelyDisplayed()))
                .perform(click())
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(0)))

        verifyActiveFragment(LISTS)
    }

    private fun verifyActiveFragment(benchmarkType: Int) {
        scenario.onActivity { activity ->
            val fragmentManager = activity.supportFragmentManager
            var activeFragment: Fragment? = null

            for (fragment in fragmentManager.fragments) {
                if (fragment.isVisible) {
                    activeFragment = fragment
                }
            }

            assertNotNull(activeFragment)
            assertTrue(activeFragment is BenchmarksFragment)
            assertNotNull(activeFragment!!.arguments)
            assertEquals(benchmarkType, activeFragment.arguments!!.getInt(BenchmarksFragment.ARG_BENCHMARK_TYPE))
        }
    }
}
