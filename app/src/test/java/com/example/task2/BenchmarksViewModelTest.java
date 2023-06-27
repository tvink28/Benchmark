package com.example.task2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.task2.models.benchmarks.Benchmark;
import com.example.task2.models.benchmarks.CellOperation;
import com.example.task2.ui.benchmark.BenchmarksViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        when(mockBenchmark.createItemsList(Mockito.anyBoolean())).thenReturn(new ArrayList<>());
//        when(mockBenchmark.getNumberOfColumns()).thenReturn(3);
        viewModel = new BenchmarksViewModel(mockBenchmark);
        viewModel.getCellOperationsLiveData().observeForever(mockCellOperationsObserver);
        viewModel.getAllTasksCompletedLiveData().observeForever(mockAllTasksCompletedObserver);
        viewModel.getValidNumberLiveData().observeForever(mockValidNumberObserver);
    }

    @Test
    public void testOnCreate() {
        viewModel.onCreate();
        verify(mockBenchmark).createItemsList(false);
        verify(mockCellOperationsObserver).onChanged(Mockito.anyList());
    }

    @Test
    public void testValidateNumber_validInput() {
        String input = "10";
        viewModel.validateNumber(input);
        Integer errorMessage = viewModel.getValidNumberLiveData().getValue();
        assertNull(errorMessage);

        String input2 = "10000000";
        viewModel.validateNumber(input2);
        Integer errorMessage2 = viewModel.getValidNumberLiveData().getValue();
        assertNull(errorMessage2);
    }

    @Test
    public void testValidateNumber_invalidInput() {
        String input = "one";
        viewModel.validateNumber(input);
        Integer errorMessage = viewModel.getValidNumberLiveData().getValue();
        assertNotNull(errorMessage);
        assertEquals(R.string.error_valid, (int) errorMessage);

        String input2 = "fifty hundred";
        viewModel.validateNumber(input2);
        Integer errorMessage2 = viewModel.getValidNumberLiveData().getValue();
        assertNotNull(errorMessage);
        assertEquals(R.string.error_valid, (int) errorMessage2);
    }

    @Test
    public void testValidateNumber_invalidCount() {
        String input = "0";
        viewModel.validateNumber(input);
        Integer errorMessage = viewModel.getValidNumberLiveData().getValue();
        assertNotNull(errorMessage);
        assertEquals(R.string.error_count, (int) errorMessage);

        String input2 = "-4";
        viewModel.validateNumber(input2);
        Integer errorMessage2 = viewModel.getValidNumberLiveData().getValue();
        assertNotNull(errorMessage);
        assertEquals(R.string.error_count, (int) errorMessage2);
    }

    @Test
    public void testRunBenchmark() {
        int number = 10;
        List<CellOperation> operations = new ArrayList<>();
        operations.add(new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na, true));
        operations.add(new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na, true));
        when(mockBenchmark.createItemsList(true)).thenReturn(operations);
        long operationTime = 100;
        for (CellOperation cellOperations : operations) {
            when(mockBenchmark.measureTime(cellOperations, number)).thenReturn(operationTime);
        }

        viewModel.runBenchmark(number);

        List<CellOperation> upgradeOperations = viewModel.getCellOperationsLiveData().getValue();

        assertEquals(operations.size(), upgradeOperations.size());

        for (int i = 0; i < operations.size(); i++) {
            CellOperation operationsCell = operations.get(i);
            CellOperation upgradeCell = upgradeOperations.get(i);
            assertEquals(upgradeCell.type,operationsCell.type);
            assertEquals(upgradeCell.action,operationsCell.action);
            assertNotEquals(upgradeCell.time,operationsCell.time);        // Тут ошибка
        }
        verify(mockAllTasksCompletedObserver).onChanged(true);         // Тут ошибка
    }


//    @Test
//    public void testGetNumberOfColumnsForCollections() {
//        viewModel = new BenchmarksViewModel(collectionBenchmark);
//        assertEquals(3, viewModel.getNumberOfColumns());
//    }
//
//    @Test
//    public void testGetNumberOfColumnsForMaps() {
//        viewModel = new BenchmarksViewModel(mapBenchmark);
//        assertEquals(2, viewModel.getNumberOfColumns());
//    }
}
