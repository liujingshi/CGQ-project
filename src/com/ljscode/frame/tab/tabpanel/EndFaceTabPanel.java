package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseUSBListener;
import com.ljscode.component.*;
import com.ljscode.data.ItemData;
import com.ljscode.data.TestData;
import com.ljscode.data.UnitData;
import com.ljscode.util.DatasetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 端面检测面板
 */
public class EndFaceTabPanel extends TabPanel {

    private final TextLabel currentDataNameLabel;
    private final List<UnitData> rawData;
    private final DataTree tree;
    private final TipBox e1TipBox;
    private final TipBox c1TipBox;
    private final TipBox e2TipBox;
    private final TipBox c2TipBox;
    private final DataLabel degLabel;
    private final DataLabel eDataLabel;
    private final DataLabel cDataLabel;
    private Btn eNewBtn;
    private TestData data;
    private ItemData selectData;
    private TestLineChart eLineChart;
    private TestLineChart cLineChart;

    public EndFaceTabPanel(List<UnitData> rawData) {
        super();
        this.rawData = rawData;
        int rootX = 30;
        int rootY = 30;
        currentDataNameLabel = new TextLabel(rootX, rootY, "2021-05-09日测量数据", 16, BaseColor.Black);
        this.add(currentDataNameLabel);
        this.tree = new DataTree(rootX, rootY + 50, 180, 500, data, itemData -> {
            this.selectData = itemData;
            this.eNewBtn.unDisabled();
        });
        this.add(tree);
        tree.blur(e -> {
            eNewBtn.disabled();
        });
        this.e1TipBox = new TipBox(this.width - 600 - 16, rootY + 600, 230, 80);
        this.add(e1TipBox);
        e1TipBox.setContent("旋钮2", false);
        this.c1TipBox = new TipBox(this.width / 2 - 300, rootY + 600, 230, 80);
        this.add(c1TipBox);
        c1TipBox.setContent("旋钮1", false);
        this.e2TipBox = new TipBox(this.width - 600 - 16 + 235, rootY + 600, 230, 80);
        this.add(e2TipBox);
        e2TipBox.setContent("旋钮2", false);
        this.c2TipBox = new TipBox(this.width / 2 - 300 + 235, rootY + 600, 230, 80);
        this.add(c2TipBox);
        c2TipBox.setContent("旋钮1", false);
        this.eNewBtn = new Btn(rootX + 200, rootY + 160, 230, 60, "保存数据", Btn.BLUE, e -> {
            if (selectData != null) {
                selectData.setData(rawData);
                selectData.setCheckEndFace(true);
                tree.setTestData(data);
                selectData = null;
                DatasetUtil.SaveOrUpdate(data);
            }
        });
        this.add(eNewBtn);
        eNewBtn.disabled();
        Btn left1 = new Btn(rootX + 200, rootY + 230, 80, 80, "1Left", Btn.GREEN, e -> {
            BaseUSBListener.RotateEndFace(1, false);
        });
        Btn left2 = new Btn(rootX + 290, rootY + 230, 80, 80, "2Left", Btn.GREEN, e -> {
            BaseUSBListener.RotateEndFace(2, false);
        });
        Btn right1 = new Btn(rootX + 200, rootY + 320, 80, 80, "1Right", Btn.GREEN, e -> {
            BaseUSBListener.RotateEndFace(1, true);
        });
        Btn right2 = new Btn(rootX + 290, rootY + 320, 80, 80, "2Right", Btn.GREEN, e -> {
            BaseUSBListener.RotateEndFace(2, true);
        });
        this.add(left1);
        this.add(left2);
        this.add(right1);
        this.add(right2);
        this.degLabel = new DataLabel(rootX + 200, rootY + 450, 24, "角度", 36, 0, "°");
        this.add(degLabel);
        this.eDataLabel = new DataLabel(this.width - 300 + 90, rootY + 550, 24, "数据", 1.73F, 2, "");
        this.add(eDataLabel);
        this.cDataLabel = new DataLabel((this.width - 180) / 2 - 100, rootY + 550, 24, "数据", 1.73F, 2, "");
        this.add(cDataLabel);
    }

    public void showEChart() {
        if (eLineChart == null) {
            eLineChart = new TestLineChart(this.width - 600 - 16, 30, 600, 500, "端面数据实时图",
                    rawData, data.getData1().getData(), BaseConfig.EndFace, null, null);
            this.add(eLineChart);
            new Thread(() -> {
                while (true) {
                    BaseUSBListener.ReadUSBData((deg, cylinder, endFace) -> {
                        if (!(deg < 0)) {
                            degLabel.setData(deg);
                            eDataLabel.setData(endFace);

                            UnitData item = UnitData.FindByDeg(rawData, deg);
                            if (item == null)
                                rawData.add(new UnitData(deg, 0, endFace));
                            else
                                item.setEndFace(endFace);
                            eLineChart.reload(rawData, this.data.getData1().isCheckEndFace() ? this.data.getData1().getData() : null, BaseConfig.EndFace, null, adjust -> {
                                e1TipBox.setContent(adjust.getText(), adjust.isRight());
                            });
                        }
                    });
                }
            }).start();
        }
    }

    public void showCChart() {
        if (cLineChart == null) {
            cLineChart = new TestLineChart((this.width - 600) / 2 - 100, 30, 600, 500, "柱面数据实时图", rawData,
                    this.data.getData1().getData(), BaseConfig.Cylinder, null, null);
            this.add(cLineChart);
            new Thread(() -> {
                while (true) {
                    BaseUSBListener.ReadUSBData((deg, cylinder, endFace) -> {
                        if (!(deg < 0)) {
                            degLabel.setData(deg);
                            cDataLabel.setData(cylinder);

                            UnitData item = UnitData.FindByDeg(rawData, deg);
                            if (item == null)
                                rawData.add(new UnitData(deg, cylinder, 0));
                            else
                                item.setCylinder(cylinder);
                            cLineChart.reload(rawData, this.data.getData1().isCheckCylinder() ? this.data.getData1().getData() : null, BaseConfig.Cylinder, adjust -> {
                                c1TipBox.setContent(adjust.getText(), adjust.isRight());
                            }, null);
                        }
                    });
                }
            }).start();
        }
    }

    public void changeData() {
        currentDataNameLabel.setText(data.getName());
        tree.setTestData(data);
        showEChart();
        showCChart();
    }

    public TestData getData() {
        return data;
    }

    public void setData(TestData data) {
        this.data = data;
        changeData();
    }

    @Override
    public void showMe() {
        super.showMe();
        eNewBtn.disabled();
    }

}
