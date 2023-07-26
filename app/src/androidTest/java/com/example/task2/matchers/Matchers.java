package com.example.task2.matchers;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
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

    public static Matcher<View> withViewAtPosition(int position, int targetViewId, Matcher<View> viewMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has view id " + targetViewId + " at position " + position + ": ");
                viewMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                View targetView = viewHolder.itemView.findViewById(targetViewId);
                return viewMatcher.matches(targetView);
            }
        };
    }
}
