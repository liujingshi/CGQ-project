package com.ljscode.data;

import com.ljscode.util.DatasetUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataModel {

    private String dataId; // 数据ID
    private String dataName; // 数据名称 第1级盘测量数据
    private Date createTime; // 创建时间
    private int dataIndex; // 数据排序
    private ArrayList<ItemModel> dataItems; // 数据项

    // 创建数据项
    public ItemModel createDataItem() {
        ItemModel itemModel = new ItemModel();
        dataItems.add(itemModel);
        return itemModel;
    }

    public DataModel() {
        dataId = DatasetUtil.CreateNewId();
        createTime = new Date();
        dataItems = new ArrayList<>();
    }

    public DataModel(String dataId, String dataName, Date createTime, int dataIndex, ArrayList<ItemModel> dataItems) {
        this.dataId = dataId;
        this.dataName = dataName;
        this.createTime = createTime;
        this.dataIndex = dataIndex;
        this.dataItems = dataItems;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(int dataIndex) {
        this.dataIndex = dataIndex;
    }

    public ArrayList<ItemModel> getDataItems() {
        return dataItems;
    }

    public void setDataItems(ArrayList<ItemModel> dataItems) {
        this.dataItems = dataItems;
    }

    public void addDataItem(ItemModel dataItem) {
        this.dataItems.add(dataItem);
    }

    @Override
    public String toString() {
        return dataName;
    }
}
