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

    public SynthesizeTabPanel() {
        super();
        int rootX = 30;
        int rootY = 30;
        currentDataNameLabel = new TextLabel(rootX, rootY, "2021-05-09日测量数据", 16, BaseColor.Black);
        this.add(currentDataNameLabel);

        TextLabel label1 = new TextLabel(30, 70, "第1级测量数据", 16, BaseColor.Black);
        this.add(label1);
        yd1 = new DataLabel(30, 100, 16, "圆度", 0, 3, "μm");
        this.add(yd1);
        pxd1 = new DataLabel(210, 100, 16, "平行度", 0, 3, "μm");
        this.add(pxd1);
        pmd1 = new DataLabel(390, 100, 16, "平面度", 0, 3, "μm");
        this.add(pmd1);
        txd1 = new DataLabel(570, 100, 16, "同心度", 0, 3, "μm");
        this.add(txd1);

        TextLabel label2 = new TextLabel(30, 140, "第2级测量数据", 16, BaseColor.Black);
        this.add(label2);
        yd2 = new DataLabel(30, 170, 16, "圆度", 0, 3, "μm");
        this.add(yd2);
        pxd2 = new DataLabel(210, 170, 16, "平行度", 0, 3, "μm");
        this.add(pxd2);
        pmd2 = new DataLabel(390, 170, 16, "平面度", 0, 3, "μm");
        this.add(pmd2);
        txd2 = new DataLabel(570, 170, 16, "同心度", 0, 3, "μm");
        this.add(txd2);

        TextLabel label3 = new TextLabel(30, 210, "第3级测量数据", 16, BaseColor.Black);
        this.add(label3);
        yd3 = new DataLabel(30, 240, 16, "圆度", 0, 3, "μm");
        this.add(yd3);
        pxd3 = new DataLabel(210, 240, 16, "平行度", 0, 3, "μm");
        this.add(pxd3);
        pmd3 = new DataLabel(390, 240, 16, "平面度", 0, 3, "μm");
        this.add(pmd3);
        txd3 = new DataLabel(570, 240, 16, "同心度", 0, 3, "μm");
        this.add(txd3);

        TextLabel label4 = new TextLabel(30, 280, "第4级测量数据", 16, BaseColor.Black);
        this.add(label4);
        yd4 = new DataLabel(30, 310, 16, "圆度", 0, 3, "μm");
        this.add(yd4);
        pxd4 = new DataLabel(210, 310, 16, "平行度", 0, 3, "μm");
        this.add(pxd4);
        pmd4 = new DataLabel(390, 310, 16, "平面度", 0, 3, "μm");
        this.add(pmd4);
        txd4 = new DataLabel(570, 310, 16, "同心度", 0, 3, "μm");
        this.add(txd4);

        TextLabel label5 = new TextLabel(30, 350, "第5级测量数据", 16, BaseColor.Black);
        this.add(label5);
        yd5 = new DataLabel(30, 380, 16, "圆度", 0, 3, "μm");
        this.add(yd5);
        pxd5 = new DataLabel(210, 380, 16, "平行度", 0, 3, "μm");
        this.add(pxd5);
        pmd5 = new DataLabel(390, 380, 16, "平面度", 0, 3, "μm");
        this.add(pmd5);
        txd5 = new DataLabel(570, 380, 16, "同心度", 0, 3, "μm");
        this.add(txd5);

        TextLabel label0 = new TextLabel(30, 420, "椎壁测量数据", 16, BaseColor.Black);
        this.add(label0);
        yd0 = new DataLabel(30, 450, 16, "圆度", 0, 3, "μm");
        this.add(yd0);
        pxd0 = new DataLabel(210, 450, 16, "平行度", 0, 3, "μm");
        this.add(pxd0);
        pmd0 = new DataLabel(390, 450, 16, "平面度", 0, 3, "μm");
        this.add(pmd0);
        txd0 = new DataLabel(570, 450, 16, "同心度", 0, 3, "μm");
        this.add(txd0);

        // 表头（列名）
        Object[] columnNames = {"数据名称", "轴心距(中心)", "轴心距(首次)", "平行度(地面)", "平行度(首次)"};

        // 表格所有行数据
        Object[][] rowData = {
                {"第1次测量数据", 0, 0, 0, 0},
                {"第2次测量数据", 0, 0, 0, 0},
                {"第3次测量数据", 0, 0, 0, 0},
                {"第4次测量数据", 0, 0, 0, 0},
                {"内部测量数据", 0, 0, 0, 0}
        };
        JTable table = new JTable(rowData, columnNames);
        table.setBounds(rootX, rootY + 90, 400, 400);
        JTableHeader jTableHeader = table.getTableHeader();
        jTableHeader.setBounds(rootX, rootY + 60, 400, 30);
//        this.add(jTableHeader);
//        this.add(table);
    }

    public void showChart() {
        if (polarChart == null) {
            this.polarChart = new TestPolarChart(this.width - 16 - 500, 30, 500, 500, data);
            this.add(polarChart);
        } else {
            polarChart.reload(data);
        }

    }

    public void setAllData() {
        DataModel level1Data = data.getLevel1Data();
        if (level1Data != null) {
            ItemModel level1ItemData = BeanUtil.GetCurrentItemModel(level1Data);
            if (level1ItemData != null) {
                for (DataModel dataItem : data.getData()) {
                    if (dataItem.getDataIndex() != 1) {
                        ItemModel itemModel = BeanUtil.GetCurrentItemModel(dataItem);
                        if (itemModel != null) {
                            itemModel.calcFormError(level1ItemData.getRealDataCylinder(), level1ItemData.getRealDataEndFace());
                        }
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
        for (DataModel dataItem : data.getData()) {
            ItemModel itemModel = BeanUtil.GetCurrentItemModel(dataItem);
            if (itemModel != null) {
                switch (dataItem.getDataIndex()) {
                    case 1:
                        yd1.setData(itemModel.getRoundness());
                        pmd1.setData(itemModel.getFlatness());
                        txd1.setData(itemModel.getAxisFrom());
                        pxd1.setData(itemModel.getParallelism());
                    case 2:
                        yd2.setData(itemModel.getRoundness());
                        pmd2.setData(itemModel.getFlatness());
                        txd2.setData(itemModel.getAxisFrom());
                        pxd2.setData(itemModel.getParallelism());
                    case 3:
                        yd3.setData(itemModel.getRoundness());
                        pmd3.setData(itemModel.getFlatness());
                        txd3.setData(itemModel.getAxisFrom());
                        pxd3.setData(itemModel.getParallelism());
                    case 4:
                        yd4.setData(itemModel.getRoundness());
                        pmd4.setData(itemModel.getFlatness());
                        txd4.setData(itemModel.getAxisFrom());
                        pxd4.setData(itemModel.getParallelism());
                    case 5:
                        yd5.setData(itemModel.getRoundness());
                        pmd5.setData(itemModel.getFlatness());
                        txd5.setData(itemModel.getAxisFrom());
                        pxd5.setData(itemModel.getParallelism());
                    case 6:
                        yd0.setData(itemModel.getRoundness());
                        pmd0.setData(itemModel.getFlatness());
                        txd0.setData(itemModel.getAxisFrom());
                        pxd0.setData(itemModel.getParallelism());
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
