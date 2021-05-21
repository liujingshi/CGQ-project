package com.ljscode.frame.tab.tabpanel;

import com.ljscode.component.TestPolarChart;
import com.ljscode.data.TestData;

/**
 * 综合数据面板
 */
public class SynthesizeTabPanel extends TabPanel {

    private TestData data;
    private TestPolarChart polarChart;

    public SynthesizeTabPanel() {
        super();
    }

    public void showChart() {
        float r = 20;
        if (polarChart == null) {
            this.polarChart = new TestPolarChart(500, 30, 500, 500, "综合数据", data, r);
            this.add(polarChart);
        } else {
            polarChart.reload(data, r);
        }

    }

    public void changeData() {
        showChart();
    }

    public TestData getData() {
        return data;
    }

    public void setData(TestData data) {
        this.data = data;
        changeData();
    }
}
