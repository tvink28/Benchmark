package com.example.task2.ui.benchmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task2.BenchmarksApp
import com.example.task2.R
import com.example.task2.models.benchmarks.Benchmark
import com.example.task2.models.benchmarks.CellOperation
import com.example.task2.models.room.AppDatabase
import com.example.task2.models.room.OperationResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BenchmarksViewModel(private val benchmark: Benchmark) : ViewModel() {

	private val operationResultDao = AppDatabase.getInstance().operationResultDao()
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
		println("Job running ${benchmarkJob?.isActive}, completed  ${benchmarkJob?.isCompleted}")
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

	private fun addResultToDatabase(result: List<CellOperation>, input: Int) {
		viewModelScope.launch(Dispatchers.IO) {
			for (cell in result) {
				val appContext = BenchmarksApp.instance
				val action = appContext?.getString(cell.action)
				val type = appContext?.getString(cell.type)
				if (action != null && type != null) {
					val operationResult = OperationResult(
						action = action,
						type = type,
						time = cell.time,
						input = input
					)
					try {
						operationResultDao.insertResult(operationResult)
					} catch (e: Exception) {
						e.printStackTrace()
					}
				}
			}
		}
	}

	suspend fun getLast21Results(): List<OperationResult> {
		return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
			operationResultDao.getLast21Results()
		}
	}

	private fun runBenchmark(number: Int) {
		val handler = CoroutineExceptionHandler { _, exception ->
			exception.printStackTrace()
		}

		val operations = benchmark.createItemsList(true).toMutableList()
		cellOperationsLiveData.value = ArrayList(operations)

		benchmarkJob = viewModelScope.launch(Dispatchers.IO + handler) {
			operations.mapIndexed { index, cell ->
				async {
					val operationTime = benchmark.measureTime(cell, number)
					val updatedCell = cell.withTime(operationTime)

					withContext(Dispatchers.Main) {
						operations[index] = updatedCell
						cellOperationsLiveData.value = ArrayList(operations)
					}
				}
			}.awaitAll()
			addResultToDatabase(operations, number)
			withContext(Dispatchers.Main) {
				allTasksCompletedLiveData.value = true
			}
		}
	}

	private fun stopBenchmark() {
		benchmarkJob?.cancel()
		cellOperationsLiveData.value = cellOperationsLiveData.value?.map { it.withIsRunning(false) }
	}
}
