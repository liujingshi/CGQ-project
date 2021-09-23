package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.*;
import com.ljscode.bean.LineChartInfo;
import com.ljscode.bean.RangeConfig;
import com.ljscode.bean.ZeroConfig;
import com.ljscode.component.*;
import com.ljscode.data.*;
import com.ljscode.frame.ErrorListFrame;
import com.ljscode.util.BeanUtil;
import com.ljscode.util.ConfigUtil;
import com.ljscode.util.DatasetUtil;
import com.ljscode.util.DbUtil;

import java.util.*;

/**
 * 端面检测面板
 */
public class EndFaceTabPanel extends TabPanel {

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
    private boolean isRead;
    private final TextLabel pointNum;
    private final TextLabel degNum;
    private final Btn errorNum1;
    private final Btn errorNum2;
    private double prevDeg;
    private double totalDeg;
    private Set<Integer> hasDef;

    public EndFaceTabPanel() {
        super();
        prevDeg = -1;
        totalDeg = 0;
        isRead = false;
        hasDef = new HashSet<>();
        RangeConfig rangeConfig = ConfigUtil.GetRangeConfig();
        this.lineChartInfoCylinder = new LineChartInfo("柱面数据实时图", rangeConfig.getCylinderStart(), rangeConfig.getCylinderEnd(), -100, 100, data, "Cylinder");
        this.lineChartInfoEndFace = new LineChartInfo("端面数据实时图", rangeConfig.getEndFaceStart(), rangeConfig.getEndFaceEnd(), -100, 100, data, "EndFace");
        int rootX = 30;
        int rootY = 30;
        currentDataNameLabel = new TextLabel(rootX, rootY, "2021-05-09日测量数据", 32, BaseColor.Black);
        this.add(currentDataNameLabel);
        degNum = new TextLabel(rootX, rootY + 620, "已经旋转的角度：360 / 360", 32, BaseColor.Black);
        this.add(degNum);
        pointNum = new TextLabel(rootX, rootY + 670, "已经采集的点位数量：1024 / 1024", 32, BaseColor.Black);
        this.add(pointNum);
        errorNum1 = new Btn(rootX, rootY + 720, 450, 60, "柱面超出标准范围数量：1024", Btn.RED, e -> {
            ErrorListFrame errorListFrame = new ErrorListFrame("柱面超出标准范围的点", lineChartInfoCylinder);
            errorListFrame.showMe();
        });
        this.add(errorNum1);
        errorNum2 = new Btn(rootX, rootY + 790, 450, 60, "端面超出标准范围数量：1024", Btn.RED, e -> {
            ErrorListFrame errorListFrame = new ErrorListFrame("端面超出标准范围的点", lineChartInfoEndFace);
            errorListFrame.showMe();
        });
        this.add(errorNum2);
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


        this.c1TipBox = new TipBox(this.width - 1370, 750, 230, 80);
        this.add(c1TipBox);
        c1TipBox.setContent("旋钮1", true);
        this.c2TipBox = new TipBox(this.width - 1124, 750, 230, 80);
        this.add(c2TipBox);
        c2TipBox.setContent("旋钮2", true);

        this.e1TipBox = new TipBox(this.width - 554, 750, 230, 80);
        this.add(e1TipBox);
        e1TipBox.setContent("旋钮3", false);
        this.e2TipBox = new TipBox(this.width - 308, 750, 230, 80);
        this.add(e2TipBox);
        e2TipBox.setContent("旋钮4", false);


        this.eNewBtn = new Btn(rootX, rootY + 550, 230, 60, "保存数据", Btn.BLUE, e -> {
            if (selectedItemData != null || selectedData != null) {
                if (mode.equals("item") && selectedItemData != null) {
                    selectedItemData.setRealDataCylinder(lineChartInfoCylinder.getRealData());
                    selectedItemData.setRealDataEndFace(lineChartInfoEndFace.getRealData());
                    selectedItemData.calcTheoryData();
                    tree.setResultModel(data);
                    selectedItemData = null;
                    DbUtil.SaveOrUpdate(data);
                } else if (mode.equals("arr") && selectedData != null) {
                    ItemModel currentItemModel = BeanUtil.GetCurrentItemModel(selectedData);
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

        Btn rBtn = new Btn(rootX + 230, rootY + 550, 230, 60, "转动圆盘", Btn.GREEN, e -> {
            BaseSimulate.isRun = !BaseSimulate.isRun;
        });
//        this.add(rBtn);

        this.degLabel = new DataLabel(this.width - 876, 700, 32, "角度", 36, 3, "°");
        this.add(degLabel);
        this.eDataLabel = new DataLabel(this.width - 456, 700, 32, "数据", 1.73F, 3, "μm");
        this.add(eDataLabel);
        this.cDataLabel = new DataLabel(this.width - 1312, 700, 32, "数据", 1.73F, 3, "μm");
        this.add(cDataLabel);
    }

    public void showEChart() {
        if (eLineChart == null) {
            lineChartInfoEndFace.setResultModel(data);
            eLineChart = new TestLineChart(this.width - 816, 30, 800, 600, lineChartInfoEndFace);
            startReadUsb(eLineChart, lineChartInfoEndFace, false);
        }
    }

    public void showCChart() {
        if (cLineChart == null) {
            lineChartInfoCylinder.setResultModel(data);
            cLineChart = new TestLineChart(this.width - 1632, 30, 800, 600, lineChartInfoCylinder);
            startReadUsb(cLineChart, lineChartInfoCylinder, true);
        }
    }

    public void startReadUsb(TestLineChart lineChart, LineChartInfo lineChartInfo, boolean isC) {
        this.add(lineChart);
        ZeroConfig zeroConfig = ConfigUtil.GetZeroConfig();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5);
                    if (isRead) {
                        BaseUSBReader.ReadUSBData((deg, cylinder, endFace) -> {
                            cylinder = cylinder - zeroConfig.getCylinder();
                            endFace = endFace - zeroConfig.getEndFace();

                            double trueReg = deg * 360d / 4096d;

                            degLabel.setData(trueReg);

                            if (prevDeg < 0) {
                                prevDeg = trueReg;
                            } else {
                                double xDeg = trueReg - prevDeg;
                                if (xDeg < 0) {
                                    xDeg = trueReg + 360 - prevDeg;
                                }
                                prevDeg = trueReg;
                                totalDeg += xDeg;
                            }
                            degNum.setText(String.format("已经旋转的角度：%d / 360", Math.min((int)totalDeg, 360)));

                            if (isC) {
                                cDataLabel.setData(cylinder);
                                errorNum1.setText(String.format("柱面超出标准范围数量：%d", lineChartInfo.getErrorPointNumber()));
                            } else {
                                eDataLabel.setData(endFace);
                                errorNum2.setText(String.format("端面超出标准范围数量：%d", lineChartInfo.getErrorPointNumber()));
                            }

                            if (!hasDef.contains(deg) && hasDef.size() < 1024) {
                                hasDef.add(deg);
                            }

                            lineChartInfo.getRealData().put(deg, isC ? cylinder : endFace);
                            if (lineChartInfo.getRealData().size() > 50) {
                                lineChartInfo.calcGoodData();
                                boolean[] guide = lineChartInfo.getGuide();
                                if (isC) {
                                    c1TipBox.setContent("旋钮1", guide[0]);
                                    c2TipBox.setContent("旋钮2", guide[1]);
                                } else {
                                    e1TipBox.setContent("旋钮3", guide[0]);
                                    e2TipBox.setContent("旋钮4", guide[1]);
                                }
                            }
                            lineChart.reload(lineChartInfo);
                            pointNum.setText(String.format("已经采集的点位数量：%d / 1024", Math.min(hasDef.size(), 1024)));

                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public void showMe() {
        super.showMe();
        eNewBtn.disabled();
    }

}
