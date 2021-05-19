package com.ljscode.component;

import com.ljscode.base.BaseChart;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

/**
 * XY轴都是数的折线图
 */
public class TestLineChart extends ChartBox {

    /**
     * 构造方法
     *
     * @param left    距左
     * @param top     距顶
     * @param width   宽度
     * @param height  高度
     * @param title   标题
     * @param rawData y轴数据
     */
    public TestLineChart(int left, int top, int width, int height, String title, List<Double> rawData, List<Double> yData) {
        super(left, top, width, height, BaseChart.CreateTestLineChart(title, rawData, yData));
    }

    public void reload(List<Double> rawData, List<Double> yData) {
        XYSeriesCollection dataset = BaseChart.CreateTestLineData(rawData, yData);
        JFreeChart chart = this.getChart();
        XYPlot plot = chart.getXYPlot();
        plot.setDataset(dataset);
    }

}
