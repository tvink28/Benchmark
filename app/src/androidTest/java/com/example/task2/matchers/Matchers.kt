package com.example.task2.matchers

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.tabs.TabLayout
import org.hamcrest.Description

object Matchers {
    fun withTabSelected(position: Int): BoundedMatcher<View?, TabLayout> {
        return object : BoundedMatcher<View?, TabLayout>(TabLayout::class.java) {

            override fun matchesSafely(tabLayout: TabLayout): Boolean {
                val tab = tabLayout.getTabAt(position)
                return tab != null && tab.isSelected
            }

            override fun describeTo(description: Description) {
                description.appendText("Tab at position: $position is selected")
            }
        }
    }
}
