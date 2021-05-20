package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseUSBListener;
import com.ljscode.component.*;
import com.ljscode.data.TestData;
import com.ljscode.data.UnitData;

import java.util.ArrayList;
import java.util.List;

/**
 * 柱面检测面板
 */
public class CylinderTabPanel extends TabPanel {

    private final TextLabel currentDataNameLabel;
    private final List<UnitData> rawData;
    private final DataTree tree;
    private TestData data;
    private TestLineChart lineChart;

    public CylinderTabPanel(List<UnitData> rawData) {
        super();
        this.rawData = rawData;
        int rootX = 30;
        int rootY = 30;
        currentDataNameLabel = new TextLabel(rootX, rootY, "2021-05-09日测量数据", 16, BaseColor.Black);
        this.add(currentDataNameLabel);
        this.tree = new DataTree(rootX, rootY + 50, 180, 500, data);
        this.add(tree);
        TipBox tipBox = new TipBox(rootX + 200, rootY + 60, 230, 80);
        this.add(tipBox);
        tipBox.setContent("旋钮1", true);
        Btn newBtn = new Btn(rootX + 200, rootY + 160, 230, 60, "顺时针旋转", Btn.BLUE, e -> {
            BaseUSBListener.Rotate(true);
        });
        this.add(newBtn);
        Btn oldBtn = new Btn(rootX + 200, rootY + 240, 230, 60, "逆时针旋转", Btn.BLUE, e -> {
            BaseUSBListener.Rotate(false);
        });
        this.add(oldBtn);
        DataLabel degLabel = new DataLabel(rootX + 200, rootY + 450, 24, "角度", 36, 0, "°");
        this.add(degLabel);
        DataLabel dataLabel = new DataLabel(rootX + 400, rootY + 450, 24, "数据", 1.73F, 2F, "");
        this.add(dataLabel);
        DataLabel pxdLabel = new DataLabel(rootX + 600, rootY + 450, 24, "平行度", -1.25F, 2F, "°");
        this.add(pxdLabel);
        rawData = new ArrayList<>();
    }


    public void showChart() {
        if (lineChart == null) {
            lineChart = new TestLineChart(500, 30, 500, 400, "柱面数据实时图", rawData, null, BaseConfig.Cylinder);
            this.add(lineChart);
            new Thread(() -> {
                while (true) {
                    BaseUSBListener.ReadUSBData((deg, cylinder, endFace) -> {
                        if (!(deg < 0)) {
                            UnitData item = UnitData.FindByDeg(rawData, deg);
                            if (item == null)
                                rawData.add(new UnitData(deg, cylinder, 0));
                            else
                                item.setCylinder(cylinder);
                            lineChart.reload(rawData, null, BaseConfig.Cylinder);
                        }
                    });
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
