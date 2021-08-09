package com.ljscode.bean;

import java.util.Date;

public class MainDb extends BaseBean {

    private String dataId; // ID
    private String dataName; // 数据名称
    private Date createTime; // 创建时间
    private String measuringStand; // 测量台份
    private String operator; // 操作人员
    private int surveyTimes; // 测量次数

    public MainDb() {
    }

    public MainDb(String dataId, String dataName, Date createTime, String measuringStand, String operator, int surveyTimes) {
        this.dataId = dataId;
        this.dataName = dataName;
        this.createTime = createTime;
        this.measuringStand = measuringStand;
        this.operator = operator;
        this.surveyTimes = surveyTimes;
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
}
