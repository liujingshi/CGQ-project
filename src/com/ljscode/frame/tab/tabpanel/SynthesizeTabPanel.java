package com.ljscode.frame.tab.tabpanel;

import com.ljscode.component.TestPolarChart;

import java.util.ArrayList;
import java.util.List;

/**
 * 综合数据面板
 */
public class SynthesizeTabPanel extends TabPanel {

    public SynthesizeTabPanel() {
        super();
        showChart();
    }

    public void showChart() {
        List<String> names = new ArrayList<>();
        names.add("拟合数据1");
        names.add("拟合数据2");
        List<List<Double>> rawDataset = new ArrayList<>();
        rawDataset.add(new ArrayList<>());
        rawDataset.add(new ArrayList<>());
        float scale = 3F;
        for (int i = 0; i <= 360; i++) {
            rawDataset.get(0).add(scale * Math.sin(Math.toRadians(i) + (Math.random() * scale / 8 - scale / 16)));
            rawDataset.get(1).add(scale * Math.sin(Math.toRadians(i) + (Math.random() * scale / 1 - scale / 2)));
        }
        float r = 20;
        TestPolarChart polarChart = new TestPolarChart(500, 30, 500, 500, "综合数据", names, r, rawDataset);
        this.add(polarChart);
    }
}
