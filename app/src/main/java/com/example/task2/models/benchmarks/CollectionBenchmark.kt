package com.example.task2.models.benchmarks

import com.example.task2.BenchmarksApp
import com.example.task2.R
import com.example.task2.models.room.CollectionResult
import com.example.task2.models.room.CollectionResultDao
import java.util.Collections
import java.util.LinkedList
import java.util.Random
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

open class CollectionBenchmark : Benchmark {

    companion object {
        private const val SPECIFIC_NUMBER = "28"
    }

    override fun injectDependenciesRoom() {
        BenchmarksApp.instance?.roomComponent?.inject(this)
    }

    @Inject
    lateinit var collectionResultDao: CollectionResultDao

    private val random = Random()

    override fun setData(action: String, type: String, time: Long, input: Int) {
        val operationResult = CollectionResult(
                action = action,
                type = type,
                time = time,
                input = input
        )
        try {
            collectionResultDao.insertResult(operationResult)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getData(): List<CollectionResult> {
        return collectionResultDao.getLastResults()
    }

    override fun getNumberOfColumns(): Int = 3

    override fun createItemsList(setRunning: Boolean): List<CellOperation> = listOf(
            CellOperation(R.string.adding_in_the_beginning, R.string.arraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.adding_in_the_middle, R.string.arraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.adding_in_the_middle, R.string.linkedlist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.adding_in_the_end, R.string.arraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.adding_in_the_end, R.string.linkedlist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.search_by_value, R.string.arraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.search_by_value, R.string.linkedlist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.search_by_value, R.string.copyonwritearraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing_in_the_beginning, R.string.arraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing_in_the_middle, R.string.arraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing_in_the_middle, R.string.linkedlist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing_in_the_end, R.string.arraylist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing_in_the_end, R.string.linkedlist, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist, R.string.na.toLong(), setRunning)
    )

    override fun measureTime(item: CellOperation, number: Int): Long {

        val list = when (item.type) {
            R.string.arraylist -> ArrayList(Collections.nCopies(number, "test"))
            R.string.linkedlist -> LinkedList(Collections.nCopies(number, "test"))
            R.string.copyonwritearraylist -> CopyOnWriteArrayList(Collections.nCopies(number, "test"))
            else -> throw RuntimeException("Unsupported collection type")
        }

        return when (item.action) {
            R.string.adding_in_the_beginning -> addStart(list)
            R.string.adding_in_the_middle -> addMiddle(list)
            R.string.adding_in_the_end -> addEnd(list)
            R.string.search_by_value -> searchByValue(list)
            R.string.removing_in_the_beginning -> removeStart(list)
            R.string.removing_in_the_middle -> removeMiddle(list)
            R.string.removing_in_the_end -> removeEnd(list)
            else -> throw RuntimeException("Unsupported action type")
        }
    }

    private fun addStart(list: MutableList<String>): Long {
        val startTime: Long = System.nanoTime()
        list.add(0, "test")
        return System.nanoTime() - startTime
    }

    private fun addMiddle(list: MutableList<String>): Long {
        val startTime: Long = System.nanoTime()
        list.add(list.size / 2, "test")
        return System.nanoTime() - startTime
    }

    private fun addEnd(list: MutableList<String>): Long {
        val startTime: Long = System.nanoTime()
        list.add("test")
        return System.nanoTime() - startTime
    }

    private fun searchByValue(list: MutableList<String>): Long {
        val randomIndex = random.nextInt(list.size)
        list[randomIndex] = SPECIFIC_NUMBER
        val startTime: Long = System.nanoTime()
        list.contains(SPECIFIC_NUMBER)
        return System.nanoTime() - startTime
    }

    private fun removeStart(list: MutableList<String>): Long {
        val startTime: Long = System.nanoTime()
        list.removeAt(0)
        return System.nanoTime() - startTime
    }

    private fun removeMiddle(list: MutableList<String>): Long {
        val startTime: Long = System.nanoTime()
        list.removeAt(list.size / 2)
        return System.nanoTime() - startTime
    }

    private fun removeEnd(list: MutableList<String>): Long {
        val startTime: Long = System.nanoTime()
        list.removeAt(list.size - 1)
        return System.nanoTime() - startTime
    }
}
