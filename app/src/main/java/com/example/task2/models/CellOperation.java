package com.example.task2.models;

public class CellOperation {
    public int action;
    public int type;

    public CellOperation(int action, int type) {
        this.action = action;
        this.type = type;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
