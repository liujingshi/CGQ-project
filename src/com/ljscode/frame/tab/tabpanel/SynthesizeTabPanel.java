package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
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
        this.add(jTableHeader);
        this.add(table);
    }

    public void showChart() {
        if (polarChart == null) {
            this.polarChart = new TestPolarChart(500, 30, 500, 500, "综合数据", data);
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
