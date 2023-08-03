package com.example.task2.ui.benchmark

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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BenchmarksViewModel(private val benchmark: Benchmark) : ViewModel() {

    private val cellOperationsLiveData = MutableLiveData<List<CellOperation>>(emptyList())
    private val allTasksCompletedLiveData = MutableLiveData(true)
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
            val jobs = mutableListOf<Job>()

            operations.withIndex().forEach { (index, cell) ->
                val childJob = launch(Dispatchers.Default) {
                    val operationTime = benchmark.measureTime(cell, number)
                    val updatedCell = cell.withTime(operationTime)

                    withContext(Dispatchers.Main) {
                        operations[index] = updatedCell
                        cellOperationsLiveData.value = ArrayList(operations)
                    }
                }
                jobs.add(childJob)
            }
            jobs.forEach { it.join() }
            allTasksCompletedLiveData.value = true
        }
    }

    private fun stopBenchmark() {
        benchmarkJob?.cancel()
        cellOperationsLiveData.value = cellOperationsLiveData.value?.map { it.withIsRunning(false) }
    }
}
