package com.ljscode.data;

import com.ljscode.util.BeanUtil;
import com.ljscode.util.DatasetUtil;
import com.ljscode.util.MathUtil;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.*;

public class ResultModel {

    private String dataId; // ID
    private String dataName; // 数据名称
    private Date createTime; // 创建时间
    private double theoryRadius; // 理论半径
    private String measuringStand; // 测量台份
    private String operator; // 操作人员
    private int surveyTimes; // 测量次数
    private ArrayList<DataModel> data; // 数据

    public XYSeriesCollection CreatePolarData() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (DataModel dataModel : data) {
            ItemModel itemModel = BeanUtil.GetCurrentItemModel(dataModel);
            if (itemModel != null) {
                XYSeries goals = new XYSeries(dataModel.getDataName());
                for (Map.Entry<Integer, Double> entry : itemModel.getTheoryDataCylinder().entrySet()) {
                    goals.add(entry.getKey()  * 360d / 4096d, (Double) (entry.getValue() + theoryRadius));
                }
                dataset.addSeries(goals);
            }
        }
        return dataset;
    }

    public ResultModel() {
        dataId = DatasetUtil.CreateNewId();
        createTime = new Date();
        data = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            DataModel levelData = new DataModel();
            levelData.setDataIndex(i);
            levelData.setDataName(String.format("第%d级盘测量数据", i));
            data.add(levelData);
        }
        DataModel data0 = new DataModel();
        data0.setDataIndex(6);
        data0.setDataName("椎壁测量数据");
        data.add(data0);
    }

    public ResultModel(String dataId, String dataName, Date createTime, double theoryRadius, String measuringStand, String operator, int surveyTimes, ArrayList<DataModel> data) {
        this.dataId = dataId;
        this.dataName = dataName;
        this.createTime = createTime;
        this.theoryRadius = theoryRadius;
        this.measuringStand = measuringStand;
        this.operator = operator;
        this.surveyTimes = surveyTimes;
        this.data = data;
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

    public double getTheoryRadius() {
        return theoryRadius;
    }

    public void setTheoryRadius(double theoryRadius) {
        this.theoryRadius = theoryRadius;
    }

    public String getMeasuringStand() {
        return measuringStand;
    }

    public void setMeasuringStand(String measuringStand) {
        this.measuringStand = measuringStand;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getSurveyTimes() {
        return surveyTimes;
    }

    public void setSurveyTimes(int surveyTimes) {
        this.surveyTimes = surveyTimes;
    }

    public List<DataModel> getData() {
        return data;
    }

    public DataModel getLevel1Data() {
        Optional<DataModel> dataModelStream = data.stream().filter(t -> t.getDataIndex() == 1).findFirst();
        return dataModelStream.orElse(null);
    }

    public void setData(ArrayList<DataModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return dataName;
    }
}
