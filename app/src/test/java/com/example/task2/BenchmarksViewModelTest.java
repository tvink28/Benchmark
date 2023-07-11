package com.example.task2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.task2.models.benchmarks.Benchmark;
import com.example.task2.models.benchmarks.CellOperation;
import com.example.task2.ui.benchmark.BenchmarksViewModel;

import org.junit.After;
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
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.TestScheduler;

@RunWith(MockitoJUnitRunner.class)
public class BenchmarksViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private Benchmark mockBenchmark;

    @Mock
    private Observer<List<CellOperation>> mockCellOperationsObserver;

    @Mock
    private Observer<Boolean> mockAllTasksCompletedObserver;

    @Mock
    private Observer<Integer> mockValidNumberObserver;
    private BenchmarksViewModel viewModel;
    private TestScheduler testScheduler;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> testScheduler);
        viewModel = spy(new BenchmarksViewModel(mockBenchmark));
        viewModel.getCellOperationsLiveData().observeForever(mockCellOperationsObserver);
        viewModel.getAllTasksCompletedLiveData().observeForever(mockAllTasksCompletedObserver);
        viewModel.getValidNumberLiveData().observeForever(mockValidNumberObserver);
    }

    @After
    public void tearDown() {
        viewModel.getCellOperationsLiveData().removeObserver(mockCellOperationsObserver);
        viewModel.getAllTasksCompletedLiveData().removeObserver(mockAllTasksCompletedObserver);
        viewModel.getValidNumberLiveData().removeObserver(mockValidNumberObserver);
        verifyNoMore();
    }

    public void verifyNoMore() {
        verifyNoMoreInteractions(mockBenchmark);
        verifyNoMoreInteractions(mockCellOperationsObserver);
        verifyNoMoreInteractions(mockAllTasksCompletedObserver);
        verifyNoMoreInteractions(mockValidNumberObserver);
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
        assertEquals(R.string.error_valid, (int) errorMessage);
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
        when(mockBenchmark.measureTime(any(CellOperation.class), Mockito.eq(number))).thenReturn(time);

        viewModel.onButtonClicked(input);
        testScheduler.triggerActions();

        verify(viewModel).runBenchmark(number);
        verify(mockBenchmark).createItemsList(true);
        verify(mockBenchmark, times(operations.size())).measureTime(any(CellOperation.class), Mockito.eq(number));
        verify(mockCellOperationsObserver, times(operations.size() + 1)).onChanged(Mockito.anyList());

        final List<CellOperation> updatedOperations = viewModel.getCellOperationsLiveData().getValue();
        for (int i = 0; i < operations.size(); i++) {
            CellOperation upgradeCell = updatedOperations.get(i);
            assertEquals(time, upgradeCell.time);
            assertFalse(upgradeCell.isRunning);
        }
        verify(mockAllTasksCompletedObserver).onChanged(true);
    }

    @Test
    public void testOnButtonClicked_stopBenchmark() {
        final String input = "10";
        final int number = 10;
        final long time = 100;

        final List<CellOperation> operations = new ArrayList<>();
        operations.add(new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, true));
        operations.add(new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na, true));
        when(mockBenchmark.createItemsList(true)).thenReturn(operations);
        lenient().when(mockBenchmark.measureTime(any(CellOperation.class), Mockito.eq(number)))
                .thenAnswer(invocation -> {
                    testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
                    return time;
                });

        viewModel.onButtonClicked(input);

        testScheduler.triggerActions();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        viewModel.onButtonClicked(input);

        verify(viewModel).runBenchmark(number);
        verify(viewModel).stopBenchmark();
        verify(mockBenchmark).createItemsList(true);
        verify(mockBenchmark, atMost(1)).measureTime(Mockito.any(CellOperation.class), Mockito.eq(number));
        verify(mockAllTasksCompletedObserver).onChanged(true);   // Тут почему то выполнилось действие allTasksCompletedLiveData.setValue(true), но не должно было, операции то не выполнились
        verify(mockCellOperationsObserver, times(2)).onChanged(Mockito.anyList());

        final List<CellOperation> updatedOperations = viewModel.getCellOperationsLiveData().getValue();

        for (int i = 0; i < operations.size(); i++) {
            assertEquals(time, updatedOperations.get(0).time);      // Проверка не проходит, operations.time не изменяется
            assertFalse(updatedOperations.get(0).isRunning);        // А тут проходит, operations.isRunning изменился
        }
    }
}
