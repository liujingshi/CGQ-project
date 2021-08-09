package com.ljscode.data;

import com.ljscode.util.DatasetUtil;
import com.ljscode.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 理论半径
     */
    private double r;
    /**
     * 第1级测量数据
     */
    private List<ItemData> data1;
    /**
     * 第2级测量数据
     */
    private List<ItemData> data2;
    /**
     * 第3级测量数据
     */
    private List<ItemData> data3;
    /**
     * 第4级测量数据
     */
    private List<ItemData> data4;
    /**
     * 第5级测量数据
     */
    private List<ItemData> data5;

    /**
     * 椎壁测量数据
     */
    private List<ItemData> insideData;

    /**
     * 构造方法
     *
     * @param name 数据名称
     */
    public TestData(String name, double r) {
        this();
        this.name = name;
        this.r = r;
    }

    /**
     * 构造方法
     */
    public TestData() {
        id = DatasetUtil.CreateNewId();
        time = new Date();
        data1 = new ArrayList<>();
        data2 = new ArrayList<>();
        data3 = new ArrayList<>();
        data4 = new ArrayList<>();
        data5 = new ArrayList<>();
        insideData = new ArrayList<>();
    }

    public TestData(String id, String name, Date time, double r,
                    ArrayList<ItemData> data1, ArrayList<ItemData> data2, ArrayList<ItemData> data3,
                    ArrayList<ItemData> data4, ArrayList<ItemData> data5, ArrayList<ItemData> insideData) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.r = r;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.data4 = data4;
        this.data5 = data5;
        this.insideData = insideData;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public ItemData getData1() {
        if (data1.size() == 0) {
            return null;
        } else {
            return data1.get(data1.size() - 1);
        }
    }

    public List<ItemData> getData1s() {
        return data1;
    }

    public void setData1(List<ItemData> data1) {
        this.data1 = data1;
    }

    public ItemData getData2() {
        if (data2.size() == 0) {
            return null;
        } else {
            return data2.get(data2.size() - 1);
        }
    }

    public List<ItemData> getData2s() {
        return data2;
    }

    public void setData2(List<ItemData> data2) {
        this.data2 = data2;
    }

    public ItemData getData3() {
        if (data3.size() == 0) {
            return null;
        } else {
            return data3.get(data3.size() - 1);
        }
    }

    public List<ItemData> getData3s() {
        return data3;
    }

    public void setData3(List<ItemData> data3) {
        this.data3 = data3;
    }

    public ItemData getData4() {
        if (data4.size() == 0) {
            return null;
        } else {
            return data4.get(data4.size() - 1);
        }
    }

    public List<ItemData> getData4s() {
        return data4;
    }

    public void setData4(List<ItemData> data4) {
        this.data4 = data4;
    }

    public ItemData getData5() {
        if (data5.size() == 0) {
            return null;
        } else {
            return data5.get(data5.size() - 1);
        }
    }

    public List<ItemData> getData5s() {
        return data5;
    }

    public void setData5(List<ItemData> data5) {
        this.data5 = data5;
    }

    public ItemData getInsideData() {
        if (insideData.size() == 0) {
            return null;
        } else {
            return insideData.get(insideData.size() - 1);
        }
    }

    public List<ItemData> getInsideDatas() {
        return insideData;
    }

    public void setInsideData(List<ItemData> insideData) {
        this.insideData = insideData;
    }

    @Override
    public String toString() {
        return String.format("测量日期：%s 名称：%s", DateUtil.ToString(this.time), this.name);
    }
}
