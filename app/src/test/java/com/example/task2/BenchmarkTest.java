package com.example.task2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.task2.models.benchmarks.CellOperation;
import com.example.task2.models.benchmarks.CollectionBenchmark;
import com.example.task2.models.benchmarks.MapBenchmark;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenchmarkTest {
    private static final String SPECIFIC_NUMBER = "28";
    private CollectionBenchmark collectionBenchmark;
    private MapBenchmark mapBenchmark;
    private List<String> list;
    private Map<String, String> map;
    private int number;

    @Before
    public void setup() {
        collectionBenchmark = new CollectionBenchmark();
        mapBenchmark = new MapBenchmark();
        number = 10;
        list = new ArrayList<>(Collections.nCopies(number, "hi"));
        map = new HashMap<>();
        for (int i = 0; i < number; i++) {
            map.put("key" + i, "value" + i);
        }
    }

    @Test
    public void testCollectionGetNumberOfColumns() {
        assertEquals(3, collectionBenchmark.getNumberOfColumns());
    }

    @Test
    public void testMapGetNumberOfColumns() {
        assertEquals(2, mapBenchmark.getNumberOfColumns());
    }

    @Test
    public void testCollectionCreateItemsList() {
        List<CellOperation> cellOperations = collectionBenchmark.createItemsList(true);
        assertEquals(21, cellOperations.size());
    }

    @Test
    public void testMapCreateItemsList() {
        List<CellOperation> cellOperations = mapBenchmark.createItemsList(true);
        assertEquals(6, cellOperations.size());
    }

    @Test
    public void testCollectionMeasureTime() {
        final List<CellOperation> cellOperations = collectionBenchmark.createItemsList(true);
        for (CellOperation cellOperation : cellOperations) {
            assertTrue(collectionBenchmark.measureTime(cellOperation, 1000) > 0);
        }
    }

    @Test
    public void testMapMeasureTime() {
        final List<CellOperation> cellOperations = mapBenchmark.createItemsList(true);
        for (CellOperation cellOperation : cellOperations) {
            assertTrue(mapBenchmark.measureTime(cellOperation, 1000) > 0);
        }
    }

    @Test
    public void testAddStart() {
        final long time = collectionBenchmark.addStart(list);
        assertTrue(time >= 0);
        assertEquals("test", list.get(0));
    }

    @Test
    public void testAddMiddle() {
        final long time = collectionBenchmark.addMiddle(list);
        assertTrue(time >= 0);
        assertEquals("test", list.get(number / 2));
    }

    @Test
    public void testAddEnd() {
        final long time = collectionBenchmark.addEnd(list);
        assertTrue(time >= 0);
        assertEquals("test", list.get(number));
    }

    @Test
    public void testSearchByValue() {
        final long time = collectionBenchmark.searchByValue(list);
        assertTrue(time >= 0);
        assertTrue(list.contains(SPECIFIC_NUMBER));
    }

    @Test
    public void testRemoveStart() {
        final long time = collectionBenchmark.removeStart(list);
        assertTrue(time >= 0);
        assertEquals(number - 1, list.size());
    }

    @Test
    public void testRemoveMiddle() {
        final long time = collectionBenchmark.removeMiddle(list);
        assertTrue(time >= 0);
        assertEquals(number - 1, list.size());
    }

    @Test
    public void testRemoveEnd() {
        final long time = collectionBenchmark.removeEnd(list);
        assertTrue(time >= 0);
        assertEquals(number - 1, list.size());
    }


    @Test
    public void testAddNew() {
        final long time = mapBenchmark.addNew(map);
        assertTrue(time >= 0);
        assertEquals("value", map.get("key"));
    }

    @Test
    public void testSearchByKey() {
        final long time = mapBenchmark.searchByKey(map);
        assertTrue(time >= 0);
        assertTrue(map.containsValue(SPECIFIC_NUMBER));
    }

    @Test
    public void testRemoving() {
        final long time = mapBenchmark.removing(map);
        assertTrue(time >= 0);
        assertEquals(number - 1, map.size());
    }
}
