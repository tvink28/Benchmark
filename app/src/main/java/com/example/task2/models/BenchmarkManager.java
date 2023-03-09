package com.example.task2.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class BenchmarkManager {
    private final CollectionProvider collectionProvider;

    public BenchmarkManager(CollectionProvider collectionProvider) {
        this.collectionProvider = collectionProvider;
    }

    public Single<Result> benchmark(int count) {
        return Single.fromCallable(() -> {
            List<String> arrayList = collectionProvider.getArrayList();
            List<String> linkedList = collectionProvider.getLinkedList();
            List<String> copyOnWriteArrayList = collectionProvider.getCopyOnWriteArrayList();

            long addStartArrayListTime = addStart(arrayList, count);
            long addStartLinkedListTime = addStart(linkedList, count);
            long addStartCopyOnWriteArrayListTime = addStart(copyOnWriteArrayList, count);

            long addMiddleArrayListTime = addMiddle(arrayList, count);
            long addMiddleLinkedListTime = addMiddle(linkedList, count);
            long addMiddleCopyOnWriteArrayListTime = addMiddle(copyOnWriteArrayList, count);

            long addEndArrayListTime = addEnd(arrayList, count);
            long addEndLinkedListTime = addEnd(linkedList, count);
            long addEndCopyOnWriteArrayListTime = addEnd(copyOnWriteArrayList, count);

            return new Result(
                    addStartArrayListTime, addStartLinkedListTime, addStartCopyOnWriteArrayListTime,
                    addMiddleArrayListTime, addMiddleLinkedListTime, addMiddleCopyOnWriteArrayListTime,
                    addEndArrayListTime, addEndLinkedListTime, addEndCopyOnWriteArrayListTime);
        }).subscribeOn(Schedulers.io());


    }

    private long addStart(List<String> list, int count) {
        long totalTime = 0;
        long startTime, endTime;

        for (int i = 0; i < count; i++) {
            startTime = System.currentTimeMillis();
            list.add(0, "test");
            endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;
        }
        return totalTime / count;
    }

    private long addMiddle(List<String> list, int count) {
        long totalTime = 0;
        long startTime, endTime;

        for (int i = 0; i < count; i++) {
            startTime = System.currentTimeMillis();
            list.add(list.size() / 2, "test");
            endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;
        }
        return totalTime / count;
    }

    private long addEnd(List<String> list, int count) {
        long totalTime = 0;
        long startTime, endTime;

        for (int i = 0; i < count; i++) {
            startTime = System.currentTimeMillis();
            list.add("test");
            endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;
        }
        return totalTime / count;
    }


    public static class Result {
        public final long addStartArrayListTime;
        public final long addStartLinkedListTime;
        public final long addStartCopyOnWriteArrayListTime;

        public final long addMiddleArrayListTime;
        public final long addMiddleLinkedListTime;
        public final long addMiddleCopyOnWriteArrayListTime;

        public final long addEndArrayListTime;
        public final long addEndLinkedListTime;
        public final long addEndCopyOnWriteArrayListTime;

        public Result(long addStartArrayListTime, long addStartLinkedListTime, long addStartCopyOnWriteArrayListTime, long addMiddleArrayListTime, long addMiddleLinkedListTime, long addMiddleCopyOnWriteArrayListTime, long addEndArrayListTime, long addEndLinkedListTime, long addEndCopyOnWriteArrayListTime) {
            this.addStartArrayListTime = addStartArrayListTime;
            this.addStartLinkedListTime = addStartLinkedListTime;
            this.addStartCopyOnWriteArrayListTime = addStartCopyOnWriteArrayListTime;
            this.addMiddleArrayListTime = addMiddleArrayListTime;
            this.addMiddleLinkedListTime = addMiddleLinkedListTime;
            this.addMiddleCopyOnWriteArrayListTime = addMiddleCopyOnWriteArrayListTime;
            this.addEndArrayListTime = addEndArrayListTime;
            this.addEndLinkedListTime = addEndLinkedListTime;
            this.addEndCopyOnWriteArrayListTime = addEndCopyOnWriteArrayListTime;
        }

        public long getAddStartArrayListTime() {
            return addStartArrayListTime;
        }

        public long getAddStartLinkedListTime() {
            return addStartLinkedListTime;
        }

        public long getAddStartCopyOnWriteArrayListTime() {
            return addStartCopyOnWriteArrayListTime;
        }

        public long getAddMiddleArrayListTime() {
            return addMiddleArrayListTime;
        }

        public long getAddMiddleLinkedListTime() {
            return addMiddleLinkedListTime;
        }

        public long getAddMiddleCopyOnWriteArrayListTime() {
            return addMiddleCopyOnWriteArrayListTime;
        }

        public long getAddEndArrayListTime() {
            return addEndArrayListTime;
        }

        public long getAddEndLinkedListTime() {
            return addEndLinkedListTime;
        }

        public long getAddEndCopyOnWriteArrayListTime() {
            return addEndCopyOnWriteArrayListTime;
        }
    }

    public static class CollectionProvider {
        private final List<String> arrayList = new ArrayList<>();
        private final List<String> linkedList = new LinkedList<>();
        private final List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        public List<String> getArrayList() {
            return arrayList;
        }

        public List<String> getLinkedList() {
            return linkedList;
        }

        public List<String> getCopyOnWriteArrayList() {
            return copyOnWriteArrayList;
        }
    }
}
