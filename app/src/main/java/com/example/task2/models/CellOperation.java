package com.example.task2.models;

public class CellOperation {
    public final int action;
    public final int type;
    public final long time;
    public final boolean isRunning;
    public final boolean runAnimation;


    public CellOperation(int action, int type, long time, boolean isRunning, boolean runAnimation) {
        this.action = action;
        this.type = type;
        this.time = time;
        this.isRunning = isRunning;
        this.runAnimation = runAnimation;
    }

    public CellOperation withTime(long newTime) {
        return new CellOperation(action, type, newTime, isRunning, runAnimation);
    }

    public CellOperation withIsRunning(boolean newIsRunning) {
        return new CellOperation(action, type, time, newIsRunning, runAnimation);
    }

    public CellOperation withRunAnimation(boolean newRunAnimation) {
        return new CellOperation(action, type, time, isRunning, newRunAnimation);
    }
}
