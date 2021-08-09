package com.ljscode.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 单次测量数据
 */
public class ItemData {

    /**
     * 数据名称
     */
    private String name;
    /**
     * 第几次测量
     */
    private int times;
    /**
     * 是否已经测量柱面数据
     */
    private boolean isCheckCylinder;
    /**
     * 是否已经测量端面数据
     */
    private boolean isCheckEndFace;
    /**
     * 数据
     */
    private List<UnitData> data;

    /**
     * 构造方法
     */
    public ItemData() {
        this.data = new ArrayList<>();
    }

    /**
     * 构造方法
     *
     * @param times 第几次测量
     */
    public ItemData(int times) {
        this.times = times;
        this.name = String.format("第%d次测量数据", this.times);
        this.isCheckCylinder = false;
        this.isCheckEndFace = false;
        this.data = new ArrayList<>();
    }

    /**
     * 构造方法
     *
     * @param name            数据名称
     * @param times           第几次测量
     * @param isCheckCylinder 是否已经测量柱面数据
     * @param isCheckEndFace  是否已经测量端面数据
     * @param data            数据
     */
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
        this.data = new ArrayList<>();
        for (UnitData item : data) {
            this.data.add(new UnitData(item.getDeg(), item.getCylinder(), item.getEndFace()));
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public ItemData cloneMe() throws CloneNotSupportedException {
        ItemData itemData = (ItemData) super.clone();
        itemData.data = new ArrayList<>();
        for (UnitData item : this.data) {
            itemData.data.add(item.cloneMe());
        }
        return itemData;
    }
}
