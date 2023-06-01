package com.example.task2.models;

import java.util.List;
import java.util.Map;

public interface Benchmark {
    long addStart(List<String> list);

    long addMiddle(List<String> list);

    long addEnd(List<String> list);

    long searchByValue(List<String> list);

    long removeStart(List<String> list);

    long removeMiddle(List<String> list);

    long removeEnd(List<String> list);

    long addNew(Map<String, String> map);

    long searchByKey(Map<String, String> map);

    long removing(Map<String, String> map);
}
