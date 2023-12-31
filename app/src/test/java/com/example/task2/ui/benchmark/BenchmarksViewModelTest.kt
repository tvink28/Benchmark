package com.example.task2.ui.benchmark

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.task2.CoroutineTestRule
import com.example.task2.R
import com.example.task2.models.benchmarks.Benchmark
import com.example.task2.models.benchmarks.CellOperation
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BenchmarksViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var viewModel: BenchmarksViewModel

    private val mockBenchmark = mockk<Benchmark>(relaxed = true)
    private val mockValidNumberObserver = mockk<Observer<Int?>>(relaxed = true)
    private val mockCellOperationsObserver = mockk<Observer<List<CellOperation>>>(relaxed = true)
    private val mockAllTasksCompletedObserver = mockk<Observer<Boolean>>(relaxed = true)

    @Before
    fun setup() {
        clearAllMocks()
        viewModel = BenchmarksViewModel(mockBenchmark)
        viewModel.getValidNumberLiveData().observeForever(mockValidNumberObserver)
        viewModel.getCellOperationsLiveData().observeForever(mockCellOperationsObserver)
        viewModel.getAllTasksCompletedLiveData().observeForever(mockAllTasksCompletedObserver)
    }

    @After
    fun tearDown() {
        viewModel.getValidNumberLiveData().removeObserver(mockValidNumberObserver)
        viewModel.getCellOperationsLiveData().removeObserver(mockCellOperationsObserver)
        viewModel.getAllTasksCompletedLiveData().removeObserver(mockAllTasksCompletedObserver)
    }

    @Test
    fun testOnCreate() {
        viewModel.onCreate()
        verify { mockBenchmark.createItemsList(false) }
        verify { mockCellOperationsObserver.onChanged(any()) }
    }

    @Test
    fun testGetNumberOfColumns() {
        val expectedColumns = 3
        every { mockBenchmark.getNumberOfColumns() } returns expectedColumns
        val columns = viewModel.getNumberOfColumns()

        assertEquals(expectedColumns, columns)
        verify { mockBenchmark.getNumberOfColumns() }
    }

    @Test
    fun testValidateNumber_validInput() {
        val input = "10"
        viewModel.validateNumber(input)
        val errorMessage = viewModel.getValidNumberLiveData().value

        assertNull(errorMessage)
        verify(exactly = 1) { mockValidNumberObserver.onChanged(any()) }
    }

    @Test
    fun testValidateNumber_invalidInput() {
        val input = "one"
        viewModel.validateNumber(input)
        val errorMessage = viewModel.getValidNumberLiveData().value

        assertNotNull(errorMessage)
        assertEquals(R.string.error_valid, errorMessage)
        verify(exactly = 1) { mockValidNumberObserver.onChanged(any()) }
    }

    @Test
    fun testValidateNumber_invalidCount() {
        val input = "0"
        viewModel.validateNumber(input)
        val errorMessage = viewModel.getValidNumberLiveData().value

        assertNotNull(errorMessage)
        assertEquals(R.string.error_count, errorMessage)
        verify(exactly = 1) { mockValidNumberObserver.onChanged(any()) }
    }

    @Test
    fun testOnButtonClicked_startBenchmark() {
        val input = "10"
        val number = 10
        val time = 100L
        val operations = mutableListOf<CellOperation>()

        operations.add(CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na.toLong(), true))

        every { mockBenchmark.createItemsList(true) } returns operations
        every { mockBenchmark.measureTime(any(), number) } returns time

        viewModel.onButtonClicked(input)

        verify { mockBenchmark.createItemsList(true) }
        verify(exactly = operations.size) { mockBenchmark.measureTime(any(), number) }
        verify { mockCellOperationsObserver.onChanged(any()) }
        verify { mockAllTasksCompletedObserver.onChanged(true) }

        val updatedOperations = viewModel.getCellOperationsLiveData().value
        assertEquals(operations.size, updatedOperations?.size)
        updatedOperations?.forEach { operation ->
            assertEquals(time, operation.time)
            assertEquals(false, operation.isRunning)
        }
        Unit
    }

    @Test
    fun testOnButtonClicked_stopBenchmark() {
        val input = "10"
        val number = 10
        val time = 100L
        val operations = mutableListOf<CellOperation>()

        operations.add(CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.adding_in_the_middle, R.string.arraylist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.adding_in_the_end, R.string.arraylist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.adding_in_the_end, R.string.linkedlist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.search_by_value, R.string.arraylist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.search_by_value, R.string.linkedlist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, R.string.na.toLong(), true))
        operations.add(CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, R.string.na.toLong(), true))

        every { mockBenchmark.createItemsList(true) } returns operations
        coEvery { mockBenchmark.measureTime(any(), number) } coAnswers {
            Thread.sleep(3000)
            time
        }
        viewModel.onButtonClicked(input)
        try {
            Thread.sleep(1000)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        viewModel.onButtonClicked(input)

        verify { mockBenchmark.createItemsList(true) }
        verify(exactly = operations.size) { mockBenchmark.measureTime(any(), number) }

        val updatedOperations = viewModel.getCellOperationsLiveData().value
        assertEquals(operations.size, updatedOperations?.size)
        updatedOperations?.forEach { operation ->
            assertNotEquals(time, operation.time)
            assertEquals(false, operation.isRunning)
        }
        Unit
    }
}
