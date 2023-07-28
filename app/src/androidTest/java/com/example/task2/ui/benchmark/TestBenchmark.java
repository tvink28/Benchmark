package com.example.task2.ui.benchmark;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.task2.Constants.INPUT_TEXT;
import static com.example.task2.Constants.SLEEP_TIME_500;
import static com.example.task2.Constants.SLEEP_TIME_FOR_TESTS;
import static com.example.task2.matchers.Matchers.withAlpha;
import static com.example.task2.matchers.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.Matchers.not;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.example.task2.BenchmarksApp;
import com.example.task2.Constants;
import com.example.task2.R;
import com.example.task2.models.DaggerTestBenchmarkComponent;
import com.example.task2.models.TestBenchmarkComponent;
import com.example.task2.models.TestBenchmarkModule;
import com.example.task2.models.benchmarks.CellOperation;
import com.example.task2.ui.MainActivity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public abstract class TestBenchmark {

    protected final List<CellOperation> cellOperationList = createItemsList();

    @BeforeClass
    public static void set() {
        final TestBenchmarkComponent appComponent = DaggerTestBenchmarkComponent.builder()
                .testBenchmarkModule(new TestBenchmarkModule())
                .build();
        BenchmarksApp.setAppComponent(appComponent);
    }

    protected abstract List<CellOperation> createItemsList();

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void test_invalidInput() {
        final String textInput = "qwe";
        onView(withId(R.id.editText)).perform(typeText(textInput));
        onView(withId(R.id.editText)).check(matches(withText("")));
        onView(withId(R.id.btnStopStart)).check(matches(not(isEnabled())));

        final String textInput2 = "0";
        onView(withId(R.id.editText)).perform(typeText(textInput2), closeSoftKeyboard());
        SystemClock.sleep(SLEEP_TIME_500);
        onView(withId(R.id.btnStopStart)).check(matches(not(isEnabled())));
        onView(withId(R.id.errorLayout)).inRoot(isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.errorText)).inRoot(isPlatformPopup()).check(matches(withText(R.string.error_count)));

        onView(withId(R.id.editText)).perform(clearText(), closeSoftKeyboard());
        SystemClock.sleep(SLEEP_TIME_500);
        onView(withId(R.id.btnStopStart)).check(matches(not(isEnabled())));
        onView(withId(R.id.errorLayout)).inRoot(isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.errorText)).inRoot(isPlatformPopup()).check(matches(withText(R.string.error_valid)));
    }

    @Test
    public void test_recyclerView() {
        onView(withId(R.id.rv)).check(matches(isDisplayed()));

        for (int i = 0; i < cellOperationList.size(); i++) {
            final CellOperation cellOperation = cellOperationList.get(i);
            final String textViewAction = createTextViewAction(cellOperation.action, cellOperation.type, cellOperation.time);

            onView(withId(R.id.rv)).perform(scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action, hasDescendant(withText(textViewAction))));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.progressBar, withAlpha(0F)));
        }
    }

    @Test
    public void test_startCalculations() {
        onView(withId(R.id.editText)).perform(typeText(INPUT_TEXT));
        onView(withId(R.id.editText)).check(matches(withText(INPUT_TEXT)));
        pressBack();
        onView(withId(R.id.btnStopStart)).perform(click());

        SystemClock.sleep(SLEEP_TIME_500);

        for (int i = 0; i < cellOperationList.size(); i++) {
            onView(withId(R.id.rv)).perform(scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.progressBar, withAlpha(1f)));
        }

        SystemClock.sleep(SLEEP_TIME_FOR_TESTS * cellOperationList.size());

        for (int i = 0; i < cellOperationList.size(); i++) {
            final CellOperation cellOperation = cellOperationList.get(i);
            final String textViewAction = createTextViewAction_withTimeResult(cellOperation.action, cellOperation.type);

            onView(withId(R.id.rv)).perform(scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action, withText(textViewAction)));
        }
    }

    @Test
    public void test_stopCalculations() {
        onView(withId(R.id.editText)).perform(typeText(INPUT_TEXT));
        onView(withId(R.id.editText)).check(matches(withText(INPUT_TEXT)));
        pressBack();
        onView(withId(R.id.btnStopStart)).perform(click());

        SystemClock.sleep(SLEEP_TIME_FOR_TESTS);

        onView(withId(R.id.btnStopStart)).perform(click());

        for (int i = 0; i <= 1; i++) {
            final CellOperation cellOperation = cellOperationList.get(i);
            final String textViewAction = createTextViewAction(cellOperation.action, cellOperation.type, cellOperation.time);

            onView(withId(R.id.rv)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action, withText(textViewAction)));
        }

        for (int i = 2; i < cellOperationList.size(); i++) {
            final CellOperation cellOperation = cellOperationList.get(i);
            final String textViewAction = createTextViewAction_withTimeResult(cellOperation.action, cellOperation.type);

            onView(withId(R.id.rv)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action, withText(textViewAction)));
        }
    }

    private String createTextViewAction(int action, int type, long time) {
        final String actionText = getApplicationContext().getString(action);
        final String typeText = getApplicationContext().getString(type);
        final String timeText = Long.toString(time);
        return String.format("%s\n%s\n%s ns", actionText, typeText, timeText);
    }

    private String createTextViewAction_withTimeResult(int action, int type) {
        final String actionText = getApplicationContext().getString(action);
        final String typeText = getApplicationContext().getString(type);
        final String timeText = String.valueOf(Constants.RESULT_COLLECTION);
        return String.format("%s\n%s\n%s ns", actionText, typeText, timeText);
    }
}
