package com.example.task2.ui.benchmark

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task2.R
import com.example.task2.models.benchmarks.Benchmark
import com.example.task2.models.benchmarks.CellOperation
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BenchmarksViewModel(private val benchmark: Benchmark) : ViewModel() {

    private val cellOperationsLiveData = MutableLiveData<List<CellOperation>>()
    private val allTasksCompletedLiveData = MutableLiveData<Boolean>()
    private val validNumberLiveData = MutableLiveData<Int?>()

    private var benchmarkJob: Job? = null

    fun getCellOperationsLiveData(): LiveData<List<CellOperation>> = cellOperationsLiveData
    fun getAllTasksCompletedLiveData(): LiveData<Boolean> = allTasksCompletedLiveData
    fun getValidNumberLiveData(): LiveData<Int?> = validNumberLiveData
    fun getNumberOfColumns(): Int = benchmark.getNumberOfColumns()

    fun onButtonClicked(input: String) {
        val number = input.toIntOrNull()
        when {
            benchmarkJob?.isActive == true -> stopBenchmark()
            number != null -> runBenchmark(number)
        }
    }

    fun validateNumber(input: String) {
        val number = input.toIntOrNull()
        val errorMessage = when {
            number == null -> R.string.error_valid
            number < 1 -> R.string.error_count
            else -> null
        }
        validNumberLiveData.value = errorMessage
    }

    fun onCreate() {
        cellOperationsLiveData.value = benchmark.createItemsList(false)
    }

    private fun runBenchmark(number: Int) {
        val handler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }

        val operations = benchmark.createItemsList(true).toMutableList()
        cellOperationsLiveData.value = ArrayList(operations)

        benchmarkJob = viewModelScope.launch(handler) {
            operations.withIndex().asFlow()
                    .map { (index, cell) ->
                        val operationTime = withContext(Dispatchers.Default) {
                            benchmark.measureTime(cell, number)
                        }
                        index to cell.withTime(operationTime)
                    }
                    .onCompletion { allTasksCompletedLiveData.value = true }
                    .collect { (position, updatedCell) ->
                        operations[position] = updatedCell
                        cellOperationsLiveData.value = ArrayList(operations)
                    }
        }
    }

    private fun stopBenchmark() {
        benchmarkJob?.cancel()
        cellOperationsLiveData.value = cellOperationsLiveData.value?.map { it.withIsRunning(false) }
    }
}
