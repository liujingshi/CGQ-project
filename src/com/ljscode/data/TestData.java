package com.ljscode.data;

import com.ljscode.util.DatasetUtil;
import com.ljscode.util.DateUtil;

import java.util.Date;

/**
 * 整个数据
 */
public class TestData {

    private String id;
    /**
     * 数据名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date time;
    /**
     * 第1次测量数据
     */
    private ItemData data1;
    /**
     * 第2次测量数据
     */
    private ItemData data2;
    /**
     * 第3次测量数据
     */
    private ItemData data3;
    /**
     * 第4次测量数据
     */
    private ItemData data4;

    /**
     * 构造方法
     *
     * @param name 数据名称
     */
    public TestData(String name) {
        this();
        this.name = name;
    }

    /**
     * 构造方法
     */
    public TestData() {
        id = DatasetUtil.CreateNewId();
        time = new Date();
        data1 = new ItemData(1);
        data2 = new ItemData(2);
        data3 = new ItemData(3);
        data4 = new ItemData(4);
    }

    /**
     * 构造方法
     *
     * @param name  数据名称
     * @param time  创建时间
     * @param data1 第1次测量数据
     * @param data2 第2次测量数据
     * @param data3 第3次测量数据
     * @param data4 第4次测量数据
     */
    public TestData(String id, String name, Date time, ItemData data1, ItemData data2, ItemData data3, ItemData data4) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("测量日期：%s 名称：%s", DateUtil.ToString(this.time), this.name);
    }
}
