package com.example.task2.models;

public class CellOperation {
    public final int action;
    public final int type;
    public final int time;


    public CellOperation(int action, int type, int time) {
        this.action = action;
        this.type = type;
        this.time = time;
    }

    public CellOperation withNewTime(int newTime) {
        return new CellOperation(action, type, newTime);
    }
}
