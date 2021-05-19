package com.ljscode.data;

import java.util.ArrayList;
import java.util.List;

public class ItemData {

    private String name;
    private int times;
    private boolean isCheckCylinder;
    private boolean isCheckEndFace;
    private List<UnitData> data;

    public ItemData() {
        this.data = new ArrayList<>();
    }

    public ItemData(int times) {
        this.times = times;
        this.name = String.format("第%d次测量数据", this.times);
        this.isCheckCylinder = false;
        this.isCheckEndFace = false;
        this.data = new ArrayList<>();
    }

    public ItemData(String name, int times, boolean isCheckCylinder, boolean isCheckEndFace, List<UnitData> data) {
        this.name = name;
        this.times = times;
        this.isCheckCylinder = isCheckCylinder;
        this.isCheckEndFace = isCheckEndFace;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public boolean isCheckCylinder() {
        return isCheckCylinder;
    }

    public void setCheckCylinder(boolean checkCylinder) {
        isCheckCylinder = checkCylinder;
    }

    public boolean isCheckEndFace() {
        return isCheckEndFace;
    }

    public void setCheckEndFace(boolean checkEndFace) {
        isCheckEndFace = checkEndFace;
    }

    public List<UnitData> getData() {
        return data;
    }

    public void setData(List<UnitData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return name;
    }
}
