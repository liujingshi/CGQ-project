package com.ljscode.component;

import com.ljscode.base.BaseChart;
import com.ljscode.data.TestData;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.data.xy.XYDataset;

/**
 * 综合数据极坐标图
 */
public class TestPolarChart extends ChartBox {

    /**
     * 构造方法
     *
     * @param left   距左
     * @param top    距顶
     * @param width  宽度
     * @param height 高度
     * @param title  标题
     * @param data   数据
     */
    public TestPolarChart(int left, int top, int width, int height, String title, TestData data) {
        super(left, top, width, height, BaseChart.CreateTestPolarChart(title, data));
    }

    /**
     * 重载数据
     *
     * @param data 数据
     */
    public void reload(TestData data) {
        XYDataset dataset = BaseChart.CreateTestPolarData(data);
        JFreeChart chart = this.getChart();
        PolarPlot plot = (PolarPlot) chart.getPlot();
        plot.setDataset(dataset);
    }

}
