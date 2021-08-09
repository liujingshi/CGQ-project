package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.component.DataLabel;
import com.ljscode.component.TestPolarChart;
import com.ljscode.component.TextLabel;
import com.ljscode.data.TestData;

import javax.swing.*;
import javax.swing.table.JTableHeader;

/**
 * 综合数据面板
 */
public class SynthesizeTabPanel extends TabPanel {

    private TestData data;
    private TestPolarChart polarChart;
    private final TextLabel currentDataNameLabel;

    public SynthesizeTabPanel() {
        super();
        int rootX = 30;
        int rootY = 30;
        currentDataNameLabel = new TextLabel(rootX, rootY, "2021-05-09日测量数据", 16, BaseColor.Black);
        this.add(currentDataNameLabel);

        TextLabel label1 = new TextLabel(30, 70, "第1级测量数据", 16, BaseColor.Black);
        this.add(label1);
        DataLabel yd1 = new DataLabel(30, 100, 16, "圆度", 0, 0, "");
        this.add(yd1);
        DataLabel pxd1 = new DataLabel(210, 100, 16, "平行度", 0, 0, "");
        this.add(pxd1);
        DataLabel pmd1 = new DataLabel(390, 100, 16, "平面度", 0, 0, "");
        this.add(pmd1);
        DataLabel txd1 = new DataLabel(570, 100, 16, "同心度", 0, 0, "");
        this.add(txd1);

        TextLabel label2 = new TextLabel(30, 140, "第2级测量数据", 16, BaseColor.Black);
        this.add(label2);
        DataLabel yd2 = new DataLabel(30, 170, 16, "圆度", 0, 0, "");
        this.add(yd2);
        DataLabel pxd2 = new DataLabel(210, 170, 16, "平行度", 0, 0, "");
        this.add(pxd2);
        DataLabel pmd2 = new DataLabel(390, 170, 16, "平面度", 0, 0, "");
        this.add(pmd2);
        DataLabel txd2 = new DataLabel(570, 170, 16, "同心度", 0, 0, "");
        this.add(txd2);

        TextLabel label3 = new TextLabel(30, 210, "第3级测量数据", 16, BaseColor.Black);
        this.add(label3);
        DataLabel yd3 = new DataLabel(30, 240, 16, "圆度", 0, 0, "");
        this.add(yd3);
        DataLabel pxd3 = new DataLabel(210, 240, 16, "平行度", 0, 0, "");
        this.add(pxd3);
        DataLabel pmd3 = new DataLabel(390, 240, 16, "平面度", 0, 0, "");
        this.add(pmd3);
        DataLabel txd3 = new DataLabel(570, 240, 16, "同心度", 0, 0, "");
        this.add(txd3);

        TextLabel label4 = new TextLabel(30, 280, "第4级测量数据", 16, BaseColor.Black);
        this.add(label4);
        DataLabel yd4 = new DataLabel(30, 310, 16, "圆度", 0, 0, "");
        this.add(yd4);
        DataLabel pxd4 = new DataLabel(210, 310, 16, "平行度", 0, 0, "");
        this.add(pxd4);
        DataLabel pmd4 = new DataLabel(390, 310, 16, "平面度", 0, 0, "");
        this.add(pmd4);
        DataLabel txd4 = new DataLabel(570, 310, 16, "同心度", 0, 0, "");
        this.add(txd4);

        TextLabel label5 = new TextLabel(30, 350, "第5级测量数据", 16, BaseColor.Black);
        this.add(label5);
        DataLabel yd5 = new DataLabel(30, 380, 16, "圆度", 0, 0, "");
        this.add(yd5);
        DataLabel pxd5 = new DataLabel(210, 380, 16, "平行度", 0, 0, "");
        this.add(pxd5);
        DataLabel pmd5 = new DataLabel(390, 380, 16, "平面度", 0, 0, "");
        this.add(pmd5);
        DataLabel txd5 = new DataLabel(570, 380, 16, "同心度", 0, 0, "");
        this.add(txd5);

        TextLabel label0 = new TextLabel(30, 420, "椎壁测量数据", 16, BaseColor.Black);
        this.add(label0);
        DataLabel yd0 = new DataLabel(30, 450, 16, "圆度", 0, 0, "");
        this.add(yd0);
        DataLabel pxd0 = new DataLabel(210, 450, 16, "平行度", 0, 0, "");
        this.add(pxd0);
        DataLabel pmd0 = new DataLabel(390, 450, 16, "平面度", 0, 0, "");
        this.add(pmd0);
        DataLabel txd0 = new DataLabel(570, 450, 16, "同心度", 0, 0, "");
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
            this.polarChart = new TestPolarChart(this.width - 16 - 500, 30, 500, 500, "综合数据", data);
            this.add(polarChart);
        } else {
            polarChart.reload(data);
        }

    }

    public void changeData() {
        currentDataNameLabel.setText(data.getName());
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
