package com.example.task2.ui.benchmark;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.task2.matchers.Matchers.withAlpha;
import static com.example.task2.matchers.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.task2.R;
import com.example.task2.matchers.RecyclerViewMatcher;
import com.example.task2.ui.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ViewTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void test_textView() {
        onView(ViewMatchers.withId(R.id.textView)).check(matches(withText(R.string.please_enter_collection_size_and_number_of_elements_to_compare)));
    }

    @Test
    public void test_invalidInput() {
        final String textInput = "qwe";
        onView(withId(R.id.editText)).perform(typeText(textInput));
        onView(withId(R.id.editText)).check(matches(withText("")));
        onView(withId(R.id.btnStopStart)).check(matches(not(isEnabled())));

        final String textInput2 = "0";
        onView(withId(R.id.editText)).perform(typeText(textInput2));
        onView(withId(R.id.btnStopStart)).check(matches(not(isEnabled())));
        onView(withId(R.id.errorLayout)).inRoot(isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.errorText)).inRoot(isPlatformPopup()).check(matches(withText(R.string.error_count)));

        onView(withId(R.id.editText)).perform(clearText());
        onView(withId(R.id.btnStopStart)).check(matches(not(isEnabled())));
        onView(withId(R.id.errorLayout)).inRoot(isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.errorText)).inRoot(isPlatformPopup()).check(matches(withText(R.string.error_valid)));
    }

    @Test
    public void test_recyclerView_collection() {

        final String textViewAction = createTextViewAction(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na);
        final String textViewAction2 = createTextViewAction(R.string.removing_in_the_end, R.string.linkedlist, R.string.na);

        onView(withId(R.id.rv)).check(matches(isDisplayed()));

        onView(new RecyclerViewMatcher(R.id.rv)
                .atPositionOnView(0, R.id.item_text_action))
                .check(matches(isDisplayed()))
                .check(matches(withText(textViewAction)));
        onView(new RecyclerViewMatcher(R.id.rv)
                .atPositionOnView(0, R.id.progressBar))
                .check(matches(withAlpha(0f)));

        onView(withId(R.id.rv)).perform(scrollToPosition(18));

        onView(new RecyclerViewMatcher(R.id.rv)
                .atPositionOnView(19, R.id.item_text_action))
                .check(matches(isDisplayed()))
                .check(matches(withText(textViewAction2)));
        onView(new RecyclerViewMatcher(R.id.rv)
                .atPositionOnView(19, R.id.progressBar))
                .check(matches(withAlpha(0f)));
    }

    @Test
    public void test_recyclerView_map() {

        final String textViewAction = createTextViewAction(R.string.adding_new, R.string.treemap, R.string.na);
        final String textViewAction2 = createTextViewAction(R.string.search_by_key, R.string.hashmap, R.string.na);

        onView(withText(R.string.tab_maps)).perform(click());
        onView(withId(R.id.rv)).check(matches(isDisplayed()));

        onView(new RecyclerViewMatcher(R.id.rv)
                .atPositionOnView(0, R.id.item_text_action))
                .check(matches(isDisplayed()))
                .check(matches(withText(textViewAction)));
        onView(new RecyclerViewMatcher(R.id.rv)
                .atPositionOnView(0, R.id.progressBar))
                .check(matches(withAlpha(0f)));


        onView(withId(R.id.rv)).perform(scrollToPosition(18));

        onView(new RecyclerViewMatcher(R.id.rv)
                .atPositionOnView(3, R.id.item_text_action))
                .check(matches(isDisplayed()))
                .check(matches(withText(textViewAction2)));
        onView(new RecyclerViewMatcher(R.id.rv)
                .atPositionOnView(3, R.id.progressBar))
                .check(matches(withAlpha(0f)));
    }

    @Test
    public void test_complete_recyclerView_collection() {

        final int itemCount = 21;
        final String time = InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.na);

        onView(withId(R.id.editText)).perform(typeText("5999999"));
        onView(withId(R.id.editText)).check(matches(withText("5999999")));
        pressBack();
        onView(withId(R.id.btnStopStart)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < itemCount; i++) {
            onView(withId(R.id.rv)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action))
                    .check(matches(withText(not(containsString(time)))));
        }
    }

    @Test
    public void test_complete_recyclerView_map() {

        final int itemCount = 6;
        final String time = InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.na);

        onView(withId(R.id.editText)).perform(typeText("999999"));
        onView(withId(R.id.editText)).check(matches(withText("999999")));
        pressBack();
        onView(withId(R.id.btnStopStart)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < itemCount; i++) {
            onView(withId(R.id.rv)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action))
                    .check(matches(withText(not(containsString(time)))));
        }
    }

    private String createTextViewAction(int action, int type, int time) {
        final String actionText = getApplicationContext().getString(action);
        final String typeText = getApplicationContext().getString(type);
        final String timeText = getApplicationContext().getString(time);
        return String.format("%s\n%s\n%s ns", actionText, typeText, timeText);
    }
}
