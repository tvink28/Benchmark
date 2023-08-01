package com.example.task2.matchers;

import android.view.View;

import androidx.test.espresso.matcher.BoundedMatcher;

import com.google.android.material.tabs.TabLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class Matchers {

    public static Matcher<View> withTabSelected(final int position) {
        return new BoundedMatcher<View, TabLayout>(TabLayout.class) {

            @Override
            protected boolean matchesSafely(TabLayout tabLayout) {
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                return tab != null && tab.isSelected();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Tab at position: " + position + " is selected");
            }
        };
    }

    public static Matcher<View> withAlpha(final float alpha) {
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public boolean matchesSafely(View view) {
                return view.getAlpha() == alpha;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has alpha: " + alpha);
            }
        };
    }
}
