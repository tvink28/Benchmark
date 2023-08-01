package com.example.task2.matchers;

import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

public class RecyclerViewMatcher {
    private final int recyclerViewId;

    public static RecyclerViewMatcher withRecyclerView(int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    public RecyclerViewMatcher(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId, Matcher<View> viewMatcher) {
        // BoundedMatcher<View, RecyclerView>(RecyclerView.class)
        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(recyclerViewId);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(recyclerViewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)", recyclerViewId);
                    }
                }
                description.appendText("with id: " + idDescription);
            }

            @Override
            public boolean matchesSafely(View view) {
                this.resources = view.getResources();
                if (childView == null) {
                    RecyclerView recyclerView = view.getRootView().findViewById(recyclerViewId);
                    if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                        childView = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView;
                    } else {
                        return false;
                    }
                }
                return viewMatcher.matches(childView.findViewById(targetViewId));
            }
        };
    }
}
