package com.example.task2.models;

public class CellOperation {
    public final int action;
    public final int type;
    public String time;


    public CellOperation(int action, int type, String time) {
        this.action = action;
        this.type = type;
        this.time = time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
