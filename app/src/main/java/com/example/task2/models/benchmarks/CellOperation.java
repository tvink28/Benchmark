package com.example.task2.models.benchmarks;

import java.util.Objects;

public class CellOperation {
    public final int action;
    public final int type;
    public final long time;
    public final boolean isRunning;

    public CellOperation(int action, int type, long time, boolean isRunning) {
        this.action = action;
        this.type = type;
        this.time = time;
        this.isRunning = isRunning;
    }

    public CellOperation withTime(long newTime) {
        return new CellOperation(action, type, newTime, false);
    }

    public CellOperation withIsRunning(boolean newIsRunning) {
        return new CellOperation(action, type, time, newIsRunning);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        CellOperation otherCellOperation = (CellOperation) other;

        return action == otherCellOperation.action &&
                type == otherCellOperation.type &&
                time == otherCellOperation.time &&
                isRunning == otherCellOperation.isRunning;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, type);
    }
}
