package com.ljscode.component;

import com.ljscode.base.BaseChart;

import java.util.List;

public class BarChart extends ChartBox {

    /**
     * 构造方法
     *
     * @param left       距左
     * @param top        距顶
     * @param width      宽度
     * @param height     高度
     * @param title      标题
     * @param xAxisLabel x轴名称
     * @param yAxisLabel y轴名称
     * @param dataName   数据名称
     * @param category      x轴数据
     * @param yData      y轴数据
     */
    public BarChart(int left, int top, int width, int height,
                    String title, String xAxisLabel, String yAxisLabel, String dataName,
                    List<String> category, List<Double> yData) {
        super(left, top, width, height, BaseChart.CreateBarChart(title, xAxisLabel, yAxisLabel, dataName, category, yData));
    }

}
