package com.ljscode.bean;

public class SearchConfig extends BaseBean {

    private String name;
    private String startTimeStr;
    private String endTimeStr;

    public SearchConfig() {
    }

    public SearchConfig(String name, String startTimeStr, String endTimeStr) {
        this.name = name;
        this.startTimeStr = startTimeStr;
        this.endTimeStr = endTimeStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
}
