package com.example.task2.ViewModelTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.task2.R;
import com.example.task2.models.benchmarks.Benchmark;
import com.example.task2.models.benchmarks.CellOperation;
import com.example.task2.ui.benchmark.BenchmarksViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class BenchmarksViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Rule
    public RxSchedulerRule rxSchedulerRule = new RxSchedulerRule();

    @Mock
    private Benchmark mockBenchmark;

    @Mock
    private Observer<List<CellOperation>> mockCellOperationsObserver;

    @Mock
    private Observer<Boolean> mockAllTasksCompletedObserver;

    @Mock
    private Observer<Integer> mockValidNumberObserver;
    private BenchmarksViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        viewModel = new BenchmarksViewModel(mockBenchmark);
        viewModel.getValidNumberLiveData().observeForever(mockValidNumberObserver);
        viewModel.getCellOperationsLiveData().observeForever(mockCellOperationsObserver);
        viewModel.getAllTasksCompletedLiveData().observeForever(mockAllTasksCompletedObserver);
    }

    @After
    public void tearDown() {
        verifyNoMore();
        rxSchedulerRule.resetSchedulers();
        viewModel.getValidNumberLiveData().removeObserver(mockValidNumberObserver);
        viewModel.getCellOperationsLiveData().removeObserver(mockCellOperationsObserver);
        viewModel.getAllTasksCompletedLiveData().removeObserver(mockAllTasksCompletedObserver);
    }

    public void verifyNoMore() {
        verifyNoMoreInteractions(mockBenchmark);
        verifyNoMoreInteractions(mockValidNumberObserver);
        verifyNoMoreInteractions(mockCellOperationsObserver);
        verifyNoMoreInteractions(mockAllTasksCompletedObserver);
    }

    @Test
    public void testOnCreate() {
        viewModel.onCreate();
        verify(mockBenchmark).createItemsList(false);
        verify(mockCellOperationsObserver).onChanged(Mockito.anyList());
    }

    @Test
    public void testValidateNumber_validInput() {
        final String input = "10";
        viewModel.validateNumber(input);
        final Integer errorMessage = viewModel.getValidNumberLiveData().getValue();
        assertNull(errorMessage);
        verify(mockValidNumberObserver, times(1)).onChanged(any());
    }

    @Test
    public void testValidateNumber_invalidInput() {
        final String input = "one";
        viewModel.validateNumber(input);
        final Integer errorMessage = viewModel.getValidNumberLiveData().getValue();
        assertNotNull(errorMessage);
        Assert.assertEquals(R.string.error_valid, (int) errorMessage);
        verify(mockValidNumberObserver, times(1)).onChanged(any());
    }

    @Test
    public void testValidateNumber_invalidCount() {
        final String input = "0";
        viewModel.validateNumber(input);
        final Integer errorMessage = viewModel.getValidNumberLiveData().getValue();
        assertNotNull(errorMessage);
        assertEquals(R.string.error_count, (int) errorMessage);
        verify(mockValidNumberObserver, times(1)).onChanged(any());
    }

    @Test
    public void testOnButtonClicked_startBenchmark() {
        final String input = "10";
        final int number = 10;
        final long time = 100;
        final List<CellOperation> operations = new ArrayList<>();
        operations.add(new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, true));
        operations.add(new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na, true));
        when(mockBenchmark.createItemsList(true)).thenReturn(operations);
        when(mockBenchmark.measureTime(any(CellOperation.class), eq(number))).thenReturn(time);

        viewModel.onButtonClicked(input);

        verify(mockBenchmark).createItemsList(true);
        verify(mockBenchmark, times(operations.size())).measureTime(any(CellOperation.class), eq(number));
        verify(mockCellOperationsObserver, times(operations.size() + 1)).onChanged(Mockito.anyList());
        verify(mockAllTasksCompletedObserver).onChanged(true);

        final List<CellOperation> updatedOperations = viewModel.getCellOperationsLiveData().getValue();
        assertEquals(operations.size(), Objects.requireNonNull(updatedOperations).size());
        for (int i = 0; i < operations.size(); i++) {
            CellOperation upgradeCell = updatedOperations.get(i);
            assertEquals(time, upgradeCell.time);
            assertFalse(upgradeCell.isRunning);
        }
    }

    @Test
    public void testOnButtonClicked_stopBenchmark() {
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.single());
        final CountDownLatch latch = new CountDownLatch(1);
        final String input = "10";
        final int number = 10;
        final long time = 100;
        final List<CellOperation> operations = new ArrayList<>();

        operations.add(new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, true));
        operations.add(new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na, true));
        operations.add(new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, R.string.na, true));
        when(mockBenchmark.createItemsList(true)).thenReturn(operations);
        when(mockBenchmark.measureTime(any(CellOperation.class), eq(number))).thenAnswer(invocation -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            latch.countDown();
            return time;
        });

        viewModel.onButtonClicked(input);

        try {
            Thread.sleep(1500);
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        viewModel.onButtonClicked(input);

        verify(mockBenchmark).createItemsList(true);
        verify(mockBenchmark, atMost(3)).measureTime(Mockito.any(CellOperation.class), eq(number));
        verify(mockCellOperationsObserver, times(3)).onChanged(Mockito.anyList());
        verify(mockAllTasksCompletedObserver).onChanged(true);

        final List<CellOperation> updatedOperations = viewModel.getCellOperationsLiveData().getValue();
        assertEquals(operations.size(), Objects.requireNonNull(updatedOperations).size());
        for (int i = 0; i < operations.size(); i++) {
            assertEquals(time, updatedOperations.get(0).time);
            assertFalse(updatedOperations.get(0).isRunning);

            assertNotEquals(time, updatedOperations.get(1).time);
            assertFalse(updatedOperations.get(1).isRunning);

            assertNotEquals(time, updatedOperations.get(2).time);
            assertFalse(updatedOperations.get(2).isRunning);
        }
    }
}
