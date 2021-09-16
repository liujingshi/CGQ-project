package com.ljscode.bean;

import com.ljscode.data.ResultModel;
import com.ljscode.data.ResultModel;

public class OutputDataset extends BaseBean {

    private MainData mainData;
    private ResultModel testData;

    public OutputDataset() {
    }

    public OutputDataset(MainData mainData, ResultModel testData) {
        this.mainData = mainData;
        this.testData = testData;
    }

    public MainData getMainData() {
        return mainData;
    }

    public void setMainData(MainData mainData) {
        this.mainData = mainData;
    }

    public ResultModel getResultModel() {
        return testData;
    }

    public void setResultModel(ResultModel testData) {
        this.testData = testData;
    }
}
