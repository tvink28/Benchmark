package com.example.task2;

public class RecyclerData {
    public int action;
    public int type;


    public RecyclerData(int action, int type) {
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
