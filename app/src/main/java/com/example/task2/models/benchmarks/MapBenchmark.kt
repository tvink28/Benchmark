package com.example.task2.models.benchmarks

import com.example.task2.BenchmarksApp
import com.example.task2.R
import com.example.task2.models.room.MapResult
import com.example.task2.models.room.MapResultDao
import java.util.Random
import java.util.TreeMap
import javax.inject.Inject

open class MapBenchmark : Benchmark {

    companion object {
        private const val SPECIFIC_NUMBER = "28"
    }

    override fun injectDependenciesRoom() {
        BenchmarksApp.instance?.roomComponent?.inject(this)
    }

    @Inject
    lateinit var mapResultDao: MapResultDao

    private val random = Random()

    override fun setData(action: String, type: String, time: Long, input: Int) {
        val operationResult = MapResult(
                action = action,
                type = type,
                time = time,
                input = input
        )
        try {
            mapResultDao.insertResult(operationResult)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getData(): List<MapResult> {
        return mapResultDao.getLastResults()
    }

    override fun getNumberOfColumns(): Int = 2

    override fun createItemsList(setRunning: Boolean): List<CellOperation> = listOf(
            CellOperation(R.string.adding_new, R.string.treemap, R.string.na.toLong(), setRunning),
            CellOperation(R.string.adding_new, R.string.hashmap, R.string.na.toLong(), setRunning),
            CellOperation(R.string.search_by_key, R.string.treemap, R.string.na.toLong(), setRunning),
            CellOperation(R.string.search_by_key, R.string.hashmap, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing, R.string.treemap, R.string.na.toLong(), setRunning),
            CellOperation(R.string.removing, R.string.hashmap, R.string.na.toLong(), setRunning)
    )

    override fun measureTime(item: CellOperation, number: Int): Long {

        val map: MutableMap<String, String> = when (item.type) {
            R.string.treemap -> TreeMap()
            R.string.hashmap -> HashMap()
            else -> throw RuntimeException("Unsupported map type")
        }

        for (i in 0 until number) {
            map["key$i"] = "value$i"
        }

        return when (item.action) {
            R.string.adding_new -> addNew(map)
            R.string.search_by_key -> searchByKey(map)
            R.string.removing -> removing(map)
            else -> throw RuntimeException("Unsupported action type")
        }
    }

    private fun addNew(map: MutableMap<String, String>): Long {
        val startTime: Long = System.nanoTime()
        map["key"] = "value"
        return System.nanoTime() - startTime
    }

    private fun searchByKey(map: MutableMap<String, String>): Long {
        val keys: List<String> = ArrayList(map.keys)
        val randomKey = keys[random.nextInt(keys.size)]
        map[randomKey] = SPECIFIC_NUMBER
        val startTime: Long = System.nanoTime()
        map.containsKey(SPECIFIC_NUMBER)
        return System.nanoTime() - startTime
    }

    private fun removing(map: MutableMap<String, String>): Long {
        val keys: List<String> = ArrayList(map.keys)
        val randomKey = keys[random.nextInt(keys.size)]
        val startTime: Long = System.nanoTime()
        map.remove(randomKey)
        return System.nanoTime() - startTime
    }
}
