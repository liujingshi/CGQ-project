package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.component.DataLabel;
import com.ljscode.component.TestPolarChart;
import com.ljscode.component.TextLabel;
import com.ljscode.data.DataModel;
import com.ljscode.data.ItemModel;
import com.ljscode.data.ResultModel;
import com.ljscode.data.TestData;
import com.ljscode.util.BeanUtil;
import com.ljscode.util.MathUtil;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.util.List;

/**
 * 综合数据面板
 */
public class SynthesizeTabPanel extends TabPanel {

    private ResultModel data;
    private TestPolarChart polarChart;
    private final TextLabel currentDataNameLabel;
    private final DataLabel yd1;
    private final DataLabel pxd1;
    private final DataLabel pmd1;
    private final DataLabel txd1;
    private final DataLabel yd2;
    private final DataLabel pxd2;
    private final DataLabel pmd2;
    private final DataLabel txd2;
    private final DataLabel yd3;
    private final DataLabel pxd3;
    private final DataLabel pmd3;
    private final DataLabel txd3;
    private final DataLabel yd4;
    private final DataLabel pxd4;
    private final DataLabel pmd4;
    private final DataLabel txd4;
    private final DataLabel yd5;
    private final DataLabel pxd5;
    private final DataLabel pmd5;
    private final DataLabel txd5;
    private final DataLabel yd0;
    private final DataLabel pxd0;
    private final DataLabel pmd0;
    private final DataLabel txd0;

    private final DataLabel min2;
    private final DataLabel max2;
    private final DataLabel min3;
    private final DataLabel max3;
    private final DataLabel min4;
    private final DataLabel max4;
    private final DataLabel min5;
    private final DataLabel max5;

    private final DataLabel min2e;
    private final DataLabel max2e;
    private final DataLabel min3e;
    private final DataLabel max3e;
    private final DataLabel min4e;
    private final DataLabel max4e;
    private final DataLabel min5e;
    private final DataLabel max5e;

    private DataLabel[] zeros;

    public SynthesizeTabPanel() {
        super();
        int rootX = 30;
        int rootY = 30;
        currentDataNameLabel = new TextLabel(rootX, rootY, "2021-05-09日测量数据", 32, BaseColor.Black);
        this.add(currentDataNameLabel);

        float fontsize = 24;
        int space = 130;
        int fSpace = 40;
        int fSpaceE = 30;
        int left1 = 30;
        int left2 = 250;
        int left3 = 470;
        int left4 = 690;
        int left5 = 910;
        int left6 = 1130;

        int leftSpace = 120;
        int leftSpace5 = 40;

        TextLabel label1 = new TextLabel(left1, space - fSpace, "第1级测量数据", fontsize, BaseColor.Black);
        this.add(label1);
        yd1 = new DataLabel(left1, space, fontsize, "圆度", 0, 3, "μm");
        this.add(yd1);
        pxd1 = new DataLabel(left2, space, fontsize, "平行度", 0, 3, "μm");
        this.add(pxd1);
        pmd1 = new DataLabel(left3, space, fontsize, "平面度", 0, 3, "μm");
        this.add(pmd1);
        txd1 = new DataLabel(left4, space, fontsize, "同心度", 0, 3, "μm");
        this.add(txd1);


        TextLabel label2 = new TextLabel(left1, space*2-fSpace, "第2级测量数据", fontsize, BaseColor.Black);
        this.add(label2);
        yd2 = new DataLabel(left1, space*2, fontsize, "圆度", 0, 3, "μm");
        this.add(yd2);
        pxd2 = new DataLabel(left2, space*2, fontsize, "平行度", 0, 3, "μm");
        this.add(pxd2);
        pmd2 = new DataLabel(left3, space*2, fontsize, "平面度", 0, 3, "μm");
        this.add(pmd2);
        txd2 = new DataLabel(left4, space*2, fontsize, "同心度", 0, 3, "μm");
        this.add(txd2);

        TextLabel label2e = new TextLabel(left1, space*2+fSpaceE, "相对1级：", fontsize, BaseColor.Black);
        this.add(label2e);
        min2 = new DataLabel(left2-leftSpace, space*2+fSpaceE, fontsize, "最小柱跳", 0, 3, "μm");
        this.add(min2);
        max2 = new DataLabel(left3-leftSpace+leftSpace5, space*2+fSpaceE, fontsize, "最大柱跳", 0, 3, "μm");
        this.add(max2);
        min2e = new DataLabel(left4-leftSpace+leftSpace5*2, space*2+fSpaceE, fontsize, "最小端跳", 0, 3, "μm");
        this.add(min2e);
        max2e = new DataLabel(left5-leftSpace+leftSpace5*3, space*2+fSpaceE, fontsize, "最大端跳", 0, 3, "μm");
        this.add(max2e);


        TextLabel label3 = new TextLabel(left1, space*3-fSpace, "第3级测量数据", fontsize, BaseColor.Black);
        this.add(label3);
        yd3 = new DataLabel(left1, space*3, fontsize, "圆度", 0, 3, "μm");
        this.add(yd3);
        pxd3 = new DataLabel(left2, space*3, fontsize, "平行度", 0, 3, "μm");
        this.add(pxd3);
        pmd3 = new DataLabel(left3, space*3, fontsize, "平面度", 0, 3, "μm");
        this.add(pmd3);
        txd3 = new DataLabel(left4, space*3, fontsize, "同心度", 0, 3, "μm");
        this.add(txd3);

        TextLabel label3e = new TextLabel(left1, space*3+fSpaceE, "相对1级：", fontsize, BaseColor.Black);
        this.add(label3e);
        min3 = new DataLabel(left2-leftSpace, space*3+fSpaceE, fontsize, "最小柱跳", 0, 3, "μm");
        this.add(min3);
        max3 = new DataLabel(left3-leftSpace+leftSpace5, space*3+fSpaceE, fontsize, "最大柱跳", 0, 3, "μm");
        this.add(max3);
        min3e = new DataLabel(left4-leftSpace+leftSpace5*2, space*3+fSpaceE, fontsize, "最小端跳", 0, 3, "μm");
        this.add(min3e);
        max3e = new DataLabel(left5-leftSpace+leftSpace5*3, space*3+fSpaceE, fontsize, "最大端跳", 0, 3, "μm");
        this.add(max3e);


        TextLabel label4 = new TextLabel(left1, space*4-fSpace, "第4级测量数据", fontsize, BaseColor.Black);
        this.add(label4);
        yd4 = new DataLabel(left1, space*4, fontsize, "圆度", 0, 3, "μm");
        this.add(yd4);
        pxd4 = new DataLabel(left2, space*4, fontsize, "平行度", 0, 3, "μm");
        this.add(pxd4);
        pmd4 = new DataLabel(left3, space*4, fontsize, "平面度", 0, 3, "μm");
        this.add(pmd4);
        txd4 = new DataLabel(left4, space*4, fontsize, "同心度", 0, 3, "μm");
        this.add(txd4);

        TextLabel label4e = new TextLabel(left1, space*4+fSpaceE, "相对1级：", fontsize, BaseColor.Black);
        this.add(label4e);
        min4 = new DataLabel(left2-leftSpace, space*4+fSpaceE, fontsize, "最小柱跳", 0, 3, "μm");
        this.add(min4);
        max4 = new DataLabel(left3-leftSpace+leftSpace5, space*4+fSpaceE, fontsize, "最大柱跳", 0, 3, "μm");
        this.add(max4);
        min4e = new DataLabel(left4-leftSpace+leftSpace5*2, space*4+fSpaceE, fontsize, "最小端跳", 0, 3, "μm");
        this.add(min4e);
        max4e = new DataLabel(left5-leftSpace+leftSpace5*3, space*4+fSpaceE, fontsize, "最大端跳", 0, 3, "μm");
        this.add(max4e);


        TextLabel label5 = new TextLabel(left1, space*5-fSpace, "第5级测量数据", fontsize, BaseColor.Black);
        this.add(label5);
        yd5 = new DataLabel(left1, space*5, fontsize, "圆度", 0, 3, "μm");
        this.add(yd5);
        pxd5 = new DataLabel(left2, space*5, fontsize, "平行度", 0, 3, "μm");
        this.add(pxd5);
        pmd5 = new DataLabel(left3, space*5, fontsize, "平面度", 0, 3, "μm");
        this.add(pmd5);
        txd5 = new DataLabel(left4, space*5, fontsize, "同心度", 0, 3, "μm");
        this.add(txd5);

        TextLabel label5e = new TextLabel(left1, space*5+fSpaceE, "相对1级：", fontsize, BaseColor.Black);
        this.add(label5e);
        min5 = new DataLabel(left2-leftSpace, space*5+fSpaceE, fontsize, "最小柱跳", 0, 3, "μm");
        this.add(min5);
        max5 = new DataLabel(left3-leftSpace+leftSpace5, space*5+fSpaceE, fontsize, "最大柱跳", 0, 3, "μm");
        this.add(max5);
        min5e = new DataLabel(left4-leftSpace+leftSpace5*2, space*5+fSpaceE, fontsize, "最小端跳", 0, 3, "μm");
        this.add(min5e);
        max5e = new DataLabel(left5-leftSpace+leftSpace5*3, space*5+fSpaceE, fontsize, "最大端跳", 0, 3, "μm");
        this.add(max5e);


        TextLabel label0 = new TextLabel(left1, space*6-fSpace, "椎壁测量数据", fontsize, BaseColor.Black);
        this.add(label0);
        yd0 = new DataLabel(left1, space*6, fontsize, "圆度", 0, 3, "μm");
        this.add(yd0);
        pxd0 = new DataLabel(left2, space*6, fontsize, "平行度", 0, 3, "μm");
        this.add(pxd0);
        pmd0 = new DataLabel(left3, space*6, fontsize, "平面度", 0, 3, "μm");
        this.add(pmd0);
        txd0 = new DataLabel(left4, space*6, fontsize, "同心度", 0, 3, "μm");
        this.add(txd0);

        zeros = new DataLabel[] {
                yd1, pxd1, pmd1, txd1,
                yd2, pxd2, pmd2, txd2,
                yd3, pxd3, pmd3, txd3,
                yd4, pxd4, pmd4, txd4,
                yd5, pxd5, pmd5, txd5,
                yd0, pxd0, pmd0, txd0,
                min2, max2, min2e, max2e,
                min3, max3, min3e, max3e,
                min4, max4, min4e, max4e,
                min5, max5, min5e, max5e,
        };
    }

    public void showChart() {
        if (polarChart == null) {
            this.polarChart = new TestPolarChart(this.width - 16 - 700, 30, 700, 700, data);
            this.add(polarChart);
        } else {
            polarChart.reload(data);
        }

    }

    public void setAllData() {
        DataModel level1Data = BeanUtil.GetLevel1Data(data);
        if (level1Data != null) {
            ItemModel level1ItemData = BeanUtil.GetCurrentItemModel(level1Data);
            if (level1ItemData != null) {
                for (DataModel dataItem : data.getData()) {
                    if (dataItem.getDataIndex() != 1) {
                        ItemModel itemModel = BeanUtil.GetCurrentItemModel(dataItem);
                        if (itemModel != null) {
                            itemModel.calcFormError(level1ItemData.getRealDataCylinder(), level1ItemData.getRealDataEndFace());
                        }
                    } else {
                        level1ItemData.calcFormError(null, null);
                    }
                }
            }
        } else {
            for (DataModel dataItem : data.getData()) {
                ItemModel itemModel = BeanUtil.GetCurrentItemModel(dataItem);
                if (itemModel != null) {
                    itemModel.calcFormError(null, null);
                }
            }
        }
        for (DataLabel label : zeros) {
            label.setData(0);
        }
        for (DataModel dataItem : data.getData()) {
            ItemModel itemModel = BeanUtil.GetCurrentItemModel(dataItem);
            if (itemModel != null) {
                double[] minMax = MathUtil.CalcMinMax(data, itemModel);
                switch (dataItem.getDataIndex()) {
                    case 1:
                        yd1.setData(itemModel.getRoundness());
                        pmd1.setData(itemModel.getFlatness());
                        txd1.setData(itemModel.getAxisFrom());
                        pxd1.setData(itemModel.getParallelism());
                        break;
                    case 2:
                        yd2.setData(itemModel.getRoundness());
                        pmd2.setData(itemModel.getFlatness());
                        txd2.setData(itemModel.getAxisFrom());
                        pxd2.setData(itemModel.getParallelism());
                        if (minMax != null) {
                            min2.setData(minMax[0]);
                            max2.setData(minMax[1]);
                            min2e.setData(minMax[2]);
                            max2e.setData(minMax[3]);
                        }
                        break;
                    case 3:
                        yd3.setData(itemModel.getRoundness());
                        pmd3.setData(itemModel.getFlatness());
                        txd3.setData(itemModel.getAxisFrom());
                        pxd3.setData(itemModel.getParallelism());
                        if (minMax != null) {
                            min3.setData(minMax[0]);
                            max3.setData(minMax[1]);
                            min3e.setData(minMax[2]);
                            max3e.setData(minMax[3]);
                        }
                        break;
                    case 4:
                        yd4.setData(itemModel.getRoundness());
                        pmd4.setData(itemModel.getFlatness());
                        txd4.setData(itemModel.getAxisFrom());
                        pxd4.setData(itemModel.getParallelism());
                        if (minMax != null) {
                            min4.setData(minMax[0]);
                            max4.setData(minMax[1]);
                            min4e.setData(minMax[2]);
                            max4e.setData(minMax[3]);
                        }
                        break;
                    case 5:
                        yd5.setData(itemModel.getRoundness());
                        pmd5.setData(itemModel.getFlatness());
                        txd5.setData(itemModel.getAxisFrom());
                        pxd5.setData(itemModel.getParallelism());
                        if (minMax != null) {
                            min5.setData(minMax[0]);
                            max5.setData(minMax[1]);
                            min5e.setData(minMax[2]);
                            max5e.setData(minMax[3]);
                        }
                        break;
                    case 6:
                        yd0.setData(itemModel.getRoundness());
                        pmd0.setData(itemModel.getFlatness());
                        txd0.setData(itemModel.getAxisFrom());
                        pxd0.setData(itemModel.getParallelism());
                        break;
                }
            }
        }
    }

    public void changeData() {
        currentDataNameLabel.setText(data.getDataName());
        showChart();
    }

    public ResultModel getData() {
        return data;
    }

    public void setData(ResultModel data) {
        this.data = data;
        changeData();
        setAllData();
    }
}
