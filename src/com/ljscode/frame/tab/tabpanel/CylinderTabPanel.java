package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseUSBListener;
import com.ljscode.component.*;
import com.ljscode.data.ItemData;
import com.ljscode.data.TestData;
import com.ljscode.data.UnitData;

import java.util.List;

/**
 * 柱面检测面板
 */
public class CylinderTabPanel extends TabPanel {

    private final TextLabel currentDataNameLabel;
    private final List<UnitData> rawData;
    private final DataTree tree;
    private final TipBox tipBox;
    private final DataLabel degLabel;
    private final DataLabel dataLabel;
    private final DataLabel pxdLabel;
    private TestData data;
    private ItemData selectData;
    private TestLineChart lineChart;

    public CylinderTabPanel(List<UnitData> rawData) {
        super();
        this.rawData = rawData;
        int rootX = 30;
        int rootY = 30;
        currentDataNameLabel = new TextLabel(rootX, rootY, "2021-05-09日测量数据", 16, BaseColor.Black);
        this.add(currentDataNameLabel);
        this.tree = new DataTree(rootX, rootY + 50, 180, 500, data, itemData -> {
            this.selectData = itemData;
        });
        this.add(tree);
        this.tipBox = new TipBox(rootX + 200, rootY + 60, 230, 80);
        this.add(tipBox);
        Btn newBtn = new Btn(rootX + 200, rootY + 160, 230, 60, "保存数据", Btn.BLUE, e -> {
            if (selectData != null) {
                selectData.setData(rawData);
                selectData.setCheckCylinder(true);
                tree.setTestData(data);
            }
        });
        this.add(newBtn);
        this.degLabel = new DataLabel(rootX + 200, rootY + 450, 24, "角度", 36, 0, "°");
        this.add(degLabel);
        this.dataLabel = new DataLabel(rootX + 400, rootY + 450, 24, "数据", 1.73F, 2F, "");
        this.add(dataLabel);
        this.pxdLabel = new DataLabel(rootX + 600, rootY + 450, 24, "平行度", -1.25F, 2F, "°");
        this.add(pxdLabel);
    }


    public void showChart() {
        if (lineChart == null) {
            lineChart = new TestLineChart(500, 30, 500, 400, "柱面数据实时图", rawData, null, BaseConfig.Cylinder);
            this.add(lineChart);
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(50);
                        BaseUSBListener.ReadUSBData((deg, cylinder, endFace) -> {
                            if (!(deg < 0)) {
                                degLabel.setData(deg);
                                dataLabel.setData(cylinder);
                                pxdLabel.setData(0);
                                UnitData item = UnitData.FindByDeg(rawData, deg);
                                if (item == null)
                                    rawData.add(new UnitData(deg, cylinder, 0));
                                else
                                    item.setCylinder(cylinder);
                                lineChart.reload(rawData, null, BaseConfig.Cylinder);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void changeData() {
        currentDataNameLabel.setText(data.getName());
        tree.setTestData(data);
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
