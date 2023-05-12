package com.example.task2.models;

public class CellOperation {
    public final int action;
    public final int type;
    public final long time;
    public boolean isRunning;


    public CellOperation(int action, int type, long time) {
        this.action = action;
        this.type = type;
        this.time = time;
    }

    public CellOperation withTime(long newTime) {
        CellOperation cellOperation = new CellOperation(action, type, newTime);
        cellOperation.isRunning = this.isRunning;
        return cellOperation;
    }
}
