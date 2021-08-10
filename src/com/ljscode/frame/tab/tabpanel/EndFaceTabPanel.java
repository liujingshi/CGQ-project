package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseUSBListener;
import com.ljscode.bean.LineChartInfo;
import com.ljscode.component.*;
import com.ljscode.data.*;
import com.ljscode.util.DatasetUtil;
import com.ljscode.util.DbUtil;

import java.util.*;

/**
 * 端面检测面板
 */
public class EndFaceTabPanel extends TabPanel {

    private final List<Double> defaultDeg;
    private final TextLabel currentDataNameLabel;
    private final LineChartInfo lineChartInfoCylinder;
    private final LineChartInfo lineChartInfoEndFace;
    private final DataTree tree;
    private final TipBox e1TipBox;
    private final TipBox c1TipBox;
    private final TipBox e2TipBox;
    private final TipBox c2TipBox;
    private final DataLabel degLabel;
    private final DataLabel eDataLabel;
    private final DataLabel cDataLabel;
    private Btn eNewBtn;
    private ResultModel data; // 数据
    private ItemModel selectedItemData;
    private DataModel selectedData;
    private String mode; // 模式 item arr
    private TestLineChart eLineChart;
    private TestLineChart cLineChart;

    public EndFaceTabPanel() {
        super();
        defaultDeg = new ArrayList<>();
        for (int cDeg = 0; cDeg < 36000; cDeg+=36) {
            defaultDeg.add((double) cDeg / 100d);
        }
        this.lineChartInfoCylinder = new LineChartInfo("柱面数据实时图", -0.3, 0.3, -10, 10, data, "Cylinder");
        this.lineChartInfoEndFace = new LineChartInfo("端面数据实时图", -0.3, 0.3, -10, 10, data, "EndFace");
        int rootX = 30;
        int rootY = 30;
        currentDataNameLabel = new TextLabel(rootX, rootY, "2021-05-09日测量数据", 16, BaseColor.Black);
        this.add(currentDataNameLabel);
        this.tree = new DataTree(rootX, rootY + 50, 300, 500, data, selectedItemModel -> {
            if (selectedItemModel == null) {
                this.eNewBtn.disabled();
            } else {
                this.selectedItemData = selectedItemModel;
                this.eNewBtn.unDisabled();
                this.eNewBtn.setText("保存数据(覆盖选中)");
            }
            mode = "item";
        }, selectedDataModel -> {
            this.selectedData = selectedDataModel;
            this.eNewBtn.unDisabled();
            this.eNewBtn.setText("新增数据");
            mode = "arr";
        });
        this.add(tree);
        tree.blur(e -> {
            eNewBtn.disabled();
        });
        this.e1TipBox = new TipBox(this.width - 1170, 652, 230, 80);
        this.add(e1TipBox);
        e1TipBox.setContent("旋钮1", false);
        this.c1TipBox = new TipBox(this.width - 554, 652, 230, 80);
        this.add(c1TipBox);
        c1TipBox.setContent("旋钮2", true);
        this.e2TipBox = new TipBox(this.width - 924, 652, 230, 80);
        this.add(e2TipBox);
        e2TipBox.setContent("旋钮3", false);
        this.c2TipBox = new TipBox(this.width - 308, 652, 230, 80);
        this.add(c2TipBox);
        c2TipBox.setContent("旋钮4", true);
        this.eNewBtn = new Btn(this.width - 1478, 250, 230, 60, "保存数据", Btn.BLUE, e -> {
            if (selectedItemData != null || selectedData != null) {
                if (mode.equals("item") && selectedItemData != null) {
                    selectedItemData.setRealDataCylinder(lineChartInfoCylinder.getRealData());
                    selectedItemData.setRealDataEndFace(lineChartInfoEndFace.getRealData());
                    selectedItemData.calcTheoryData();
                    tree.setResultModel(data);
                    selectedItemData = null;
                    DbUtil.SaveOrUpdate(data);
                } else if (mode.equals("arr") && selectedData != null) {
                    ItemModel currentItemModel = selectedData.getCurrentDataItem();
                    int nextIndex = 1;
                    if (currentItemModel != null) {
                        nextIndex = currentItemModel.getDataIndex() + 1;
                    }
                    ItemModel itemModel = new ItemModel(nextIndex);
                    itemModel.setRealDataCylinder(lineChartInfoCylinder.getRealData());
                    itemModel.setRealDataEndFace(lineChartInfoEndFace.getRealData());
                    itemModel.calcTheoryData();
                    selectedData.addDataItem(itemModel);
                    tree.setResultModel(data);
                    DbUtil.SaveOrUpdate(data);
                }
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
//        this.add(left1);
//        this.add(left2);
//        this.add(right1);
//        this.add(right2);
        this.degLabel = new DataLabel(this.width - 704, 596, 24, "角度", 36, 0, "°");
        this.add(degLabel);
        this.eDataLabel = new DataLabel(this.width - 396, 596, 24, "数据", 1.73F, 2, "");
        this.add(eDataLabel);
        this.cDataLabel = new DataLabel(this.width - 1012, 596, 24, "数据", 1.73F, 2, "");
        this.add(cDataLabel);
    }

    public void showEChart() {
        if (eLineChart == null) {
            lineChartInfoEndFace.setResultModel(data);
            eLineChart = new TestLineChart(this.width - 616, 30, 600, 500, lineChartInfoEndFace);
            this.add(eLineChart);
            new Thread(() -> {
                while (true) {
                    BaseUSBListener.ReadUSBData((deg, cylinder, endFace) -> {
                        if (!(deg < 0)) {
                            degLabel.setData(deg);
                            eDataLabel.setData(endFace);

                            Optional<Double> xDeg = defaultDeg.stream().filter(t -> Math.abs(t - deg) <= 0.000001).findFirst();
                            if (xDeg.isPresent()) {
                                Double mapDeg = xDeg.get();
                                lineChartInfoEndFace.getRealData().put(mapDeg, endFace);
                                if (lineChartInfoCylinder.getRealData().size() >= 600) {
                                    lineChartInfoEndFace.calcGoodData();
                                    eLineChart.reload(lineChartInfoEndFace);
                                }
                            }
                        }
                    });
                }
            }).start();
        }
    }

    public void showCChart() {
        if (cLineChart == null) {
            lineChartInfoCylinder.setResultModel(data);
            cLineChart = new TestLineChart(this.width - 1232, 30, 600, 500, lineChartInfoCylinder);
            this.add(cLineChart);
            new Thread(() -> {
                while (true) {
                    BaseUSBListener.ReadUSBData((deg, cylinder, endFace) -> {
                        if (!(deg < 0)) {
                            degLabel.setData(deg);
                            cDataLabel.setData(cylinder);

                            Optional<Double> xDeg = defaultDeg.stream().filter(t -> Math.abs(t - deg) <= 0.000001).findFirst();
                            if (xDeg.isPresent()) {
                                Double mapDeg = xDeg.get();
                                lineChartInfoCylinder.getRealData().put(mapDeg, endFace);
                                if (lineChartInfoCylinder.getRealData().size() >= 600) {
                                    lineChartInfoCylinder.calcGoodData();
                                    cLineChart.reload(lineChartInfoCylinder);
                                }
                            }
                        }
                    });
                }
            }).start();
        }
    }

    public void changeData() {
        currentDataNameLabel.setText(data.getDataName());
        tree.setResultModel(data);
        showEChart();
        showCChart();
    }

    public ResultModel getData() {
        return data;
    }

    public void setData(ResultModel data) {
        this.data = data;
        changeData();
    }

    @Override
    public void showMe() {
        super.showMe();
        eNewBtn.disabled();
    }

}
