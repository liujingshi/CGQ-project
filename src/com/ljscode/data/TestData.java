package com.ljscode.data;

import java.util.Date;
import java.util.List;

public class TestData {

    private String name;
    private Date time;
    private ItemData data1;
    private ItemData data2;
    private ItemData data3;
    private ItemData data4;

    public TestData(String name, Date time) {
        this();
        this.name = name;
        this.time = time;
    }

    public TestData() {
        data1 = new ItemData(1);
        data2 = new ItemData(2);
        data3 = new ItemData(3);
        data4 = new ItemData(4);
    }

    public TestData(String name, Date time, ItemData data1, ItemData data2, ItemData data3, ItemData data4) {
        this.name = name;
        this.time = time;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.data4 = data4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public ItemData getData1() {
        return data1;
    }

    public void setData1(ItemData data1) {
        this.data1 = data1;
    }

    public ItemData getData2() {
        return data2;
    }

    public void setData2(ItemData data2) {
        this.data2 = data2;
    }

    public ItemData getData3() {
        return data3;
    }

    public void setData3(ItemData data3) {
        this.data3 = data3;
    }

    public ItemData getData4() {
        return data4;
    }

    public void setData4(ItemData data4) {
        this.data4 = data4;
    }
}
