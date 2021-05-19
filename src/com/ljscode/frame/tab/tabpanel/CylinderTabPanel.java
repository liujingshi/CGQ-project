package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.component.*;
import com.ljscode.data.TestData;

import java.util.ArrayList;
import java.util.List;

public class CylinderTabPanel extends TabPanel {

    private final TextLabel currentDataNameLabel;
    private TestData data;
    private final DataTree tree;

    public CylinderTabPanel() {
        super();
        int rootX = 30;
        int rootY = 30;
        currentDataNameLabel = new TextLabel(rootX, rootY, "2021-05-09日测量数据", 16, BaseColor.Black);
        this.add(currentDataNameLabel);
        this.tree = new DataTree(rootX, rootY + 50, 180, 500, data);
        this.add(tree);
        TipBox tipBox = new TipBox(rootX + 200, rootY + 60, 230, 80);
        this.add(tipBox);
        tipBox.setContent("旋钮1", true);
        Btn newBtn = new Btn(rootX + 200, rootY + 160, 230, 60, "记录当前数据为新数据", Btn.BLUE, e -> {
        });
        this.add(newBtn);
        Btn oldBtn = new Btn(rootX + 200, rootY + 240, 230, 60, "使用当前数据替换选中数据", Btn.BLUE, e -> {
        });
        this.add(oldBtn);
        DataLabel degLabel = new DataLabel(rootX + 200, rootY + 450, 24, "角度", 36, 0, "°");
        this.add(degLabel);
        DataLabel dataLabel = new DataLabel(rootX + 400, rootY + 450, 24, "数据", 1.73F, 2F, "");
        this.add(dataLabel);
        DataLabel pxdLabel = new DataLabel(rootX + 600, rootY + 450, 24, "平行度", -1.25F, 2F, "°");
        this.add(pxdLabel);
        showChart();
    }

    public void showChart() {
        List<Double> rawData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        TestLineChart lineChart = new TestLineChart(500, 30, 500, 400, "柱面数据实时图", rawData, yData);
        this.add(lineChart);
        new Thread(() -> {
            float scale = 1;
            while (true) {
                try {
                    for (int i = 0; i <= 360; i++) {
                        double data = scale * Math.sin(Math.toRadians(i) + (Math.random() * scale / 8 - scale / 16));
                        if (rawData.size() <= i)
                            rawData.add(data);
                        else
                            rawData.set(i, data);
                        if (i % 3 == 0) {
                            Thread.sleep(100);
                            lineChart.reload(rawData, yData);
                        }
                    }
                    scale -= 0.1;
                    if (scale <= 0)
                        scale = 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void changeData() {
        currentDataNameLabel.setText(data.getName());
        tree.setTestData(data);
    }

    public TestData getData() {
        return data;
    }

    public void setData(TestData data) {
        this.data = data;
        changeData();
    }
}
