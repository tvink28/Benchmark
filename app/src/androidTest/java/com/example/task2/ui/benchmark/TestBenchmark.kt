package com.example.task2.ui.benchmark

import android.content.Context
import android.os.SystemClock.sleep
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withAlpha
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.task2.BenchmarksApp.Companion.setAppComponent
import com.example.task2.Constants.INPUT_TEXT
import com.example.task2.Constants.RESULT_COLLECTION
import com.example.task2.Constants.SLEEP_TIME_500
import com.example.task2.Constants.SLEEP_TIME_FOR_TESTS
import com.example.task2.R
import com.example.task2.matchers.RecyclerViewMatcher.Companion.withRecyclerView
import com.example.task2.models.DaggerTestBenchmarkComponent
import com.example.task2.models.TestBenchmarkModule
import com.example.task2.models.benchmarks.CellOperation
import com.example.task2.ui.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

abstract class TestBenchmark {

    companion object {
        @JvmStatic
        @BeforeClass
        fun set() {
            val appComponent = DaggerTestBenchmarkComponent.builder()
                    .testBenchmarkModule(TestBenchmarkModule())
                    .build()
            setAppComponent(appComponent)
        }
    }

    private val cellOperationList = this.createItemsList()

    protected abstract fun createItemsList(): List<CellOperation>

    @Before
    open fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun test_invalidInput() {
        val textInput = "qwe"
        onView(withId(R.id.editText)).perform(typeText(textInput))
        onView(withId(R.id.editText)).check(matches(withText("")))
        onView(withId(R.id.btnStopStart)).check(matches(not(isEnabled())))

        val textInput2 = "0"
        onView(withId(R.id.editText)).perform(typeText(textInput2), closeSoftKeyboard())
        sleep(SLEEP_TIME_500)
        onView(withId(R.id.btnStopStart)).check(matches(not(isEnabled())))
        onView(withId(R.id.errorLayout)).inRoot(isPlatformPopup()).check(matches(isDisplayed()))
        onView(withId(R.id.errorText)).inRoot(isPlatformPopup()).check(matches(withText(R.string.error_count)))

        onView(withId(R.id.editText)).perform(clearText(), closeSoftKeyboard())
        sleep(SLEEP_TIME_500)
        onView(withId(R.id.btnStopStart)).check(matches(not(isEnabled())))
        onView(withId(R.id.errorLayout)).inRoot(isPlatformPopup()).check(matches(isDisplayed()))
        onView(withId(R.id.errorText)).inRoot(isPlatformPopup()).check(matches(withText(R.string.error_valid)))
    }

    @Test
    fun test_recyclerView() {
        onView(withId(R.id.rv)).check(matches(isDisplayed()))

        for (i in cellOperationList.indices) {
            val (action, type, time) = cellOperationList[i]
            val textViewAction = createTextViewAction(action, type, time)
            onView(withId(R.id.rv)).perform(scrollToPosition<RecyclerView.ViewHolder>(i))
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action, hasDescendant(withText(textViewAction))))
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.progressBar, withAlpha(0f)))
        }
    }

    @Test
    fun test_startCalculations() {
        onView(withId(R.id.editText)).perform(typeText(INPUT_TEXT))
        onView(withId(R.id.editText)).check(matches(withText(INPUT_TEXT)))
        pressBack()
        onView(withId(R.id.btnStopStart)).perform(click())

        sleep(SLEEP_TIME_500)

        for (i in cellOperationList.indices) {
            onView(withId(R.id.rv)).perform(scrollToPosition<RecyclerView.ViewHolder>(i))
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.progressBar, withAlpha(1f)))
        }

        sleep(SLEEP_TIME_FOR_TESTS * cellOperationList.size)

        for (i in cellOperationList.indices) {
            val (action, type) = cellOperationList[i]
            val textViewAction = createTextViewAction_withTimeResult(action, type)

            onView(withId(R.id.rv)).perform(scrollToPosition<RecyclerView.ViewHolder>(i))
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action, withText(textViewAction)))
        }
    }

    @Test
    fun test_stopCalculations() {
        onView(withId(R.id.editText)).perform(typeText(INPUT_TEXT))
        onView(withId(R.id.editText)).check(matches(withText(INPUT_TEXT)))
        pressBack()
        onView(withId(R.id.btnStopStart)).perform(click())

        sleep(SLEEP_TIME_FOR_TESTS)

        onView(withId(R.id.btnStopStart)).perform(click())

        for (i in 0..1) {
            val (action, type, time) = cellOperationList[i]
            val textViewAction = createTextViewAction(action, type, time)
            onView(withId(R.id.rv)).perform(scrollToPosition<RecyclerView.ViewHolder>(i))
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action, withText(textViewAction)))
        }

        for (i in 2 until cellOperationList.size) {
            val (action, type) = cellOperationList[i]
            val textViewAction = createTextViewAction_withTimeResult(action, type)

            onView(withId(R.id.rv)).perform(scrollToPosition<RecyclerView.ViewHolder>(i))
            onView(withRecyclerView(R.id.rv)
                    .atPositionOnView(i, R.id.item_text_action, withText(textViewAction)))
        }
    }

    private fun createTextViewAction(action: Int, type: Int, time: Long): String {
        val actionText = ApplicationProvider.getApplicationContext<Context>().getString(action)
        val typeText = ApplicationProvider.getApplicationContext<Context>().getString(type)
        val timeText = time.toString()
        return "$actionText\n$typeText\n${timeText}ns"
    }

    private fun createTextViewAction_withTimeResult(action: Int, type: Int): String {
        val actionText = ApplicationProvider.getApplicationContext<Context>().getString(action)
        val typeText = ApplicationProvider.getApplicationContext<Context>().getString(type)
        val timeText = RESULT_COLLECTION.toString()
        return "$actionText\n$typeText\n${timeText}ns"
    }
}