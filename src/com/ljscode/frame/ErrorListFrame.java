package com.ljscode.frame;

import com.ljscode.base.BaseFrame;
import com.ljscode.bean.LineChartInfo;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.util.List;

public class ErrorListFrame extends BaseFrame {

    public ErrorListFrame(String title, LineChartInfo lineChartInfo) {
        super(title, 800, 600);
        setFrameMiddle();  // 设置窗体居中
        this.setLayout(null); // 绝对定位
        setContent(lineChartInfo);
    }

    private void setContent(LineChartInfo lineChartInfo) {
        List<Object[]> errorList = lineChartInfo.getErrorPoint();

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setLayout(null);
        jScrollPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(jScrollPane);

        // 表头（列名）
        Object[] columnNames = {"角度", "数据(μm)", "距离标准(μm)"};

        // 表格所有行数据
        Object[][] rowData = new Object[errorList.size()][3];

        for (int i = 0; i < errorList.size(); i++) {
            rowData[i] = errorList.get(i);
        }

        JTable table = new JTable(rowData, columnNames);
        table.setBounds(0, 0, 800, 600);
        JTableHeader jTableHeader = table.getTableHeader();
        jTableHeader.setBounds(0, 0, 800, 30);
        jScrollPane.add(jTableHeader);
        jScrollPane.add(table);
    }

}
