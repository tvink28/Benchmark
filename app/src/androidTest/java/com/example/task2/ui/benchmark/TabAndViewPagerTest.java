package com.example.task2.ui.benchmark;

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

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.task2.R;
import com.example.task2.ui.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TabAndViewPagerTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void test_swipeViewPager() {

        onView(ViewMatchers.withId(R.id.view_pager)).check(matches(isDisplayed()));
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

        onView(withText(R.string.tab_collections))
                .check(matches(isCompletelyDisplayed()))
                .perform(click());
        onView(withId(R.id.tab_layout)).check(matches(withTabSelected(0)));
    }
}
