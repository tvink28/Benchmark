package com.example.task2.models;

public class CellOperation {
    public final int action;
    public final int type;

    public final long operationTime;

    public CellOperation(int action, int type, long operationTime) {
        this.action = action;
        this.type = type;
        this.operationTime = operationTime;
    }
}
