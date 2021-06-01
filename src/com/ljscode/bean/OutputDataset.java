package com.ljscode.bean;

import com.ljscode.data.TestData;

public class OutputDataset extends BaseBean {

    private MainData mainData;
    private TestData testData;

    public OutputDataset() {
    }

    public OutputDataset(MainData mainData, TestData testData) {
        this.mainData = mainData;
        this.testData = testData;
    }

    public MainData getMainData() {
        return mainData;
    }

    public void setMainData(MainData mainData) {
        this.mainData = mainData;
    }

    public TestData getTestData() {
        return testData;
    }

    public void setTestData(TestData testData) {
        this.testData = testData;
    }
}
