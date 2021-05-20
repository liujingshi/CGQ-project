package com.ljscode.component;

import com.ljscode.base.BaseChart;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.data.xy.XYDataset;

import java.util.List;

/**
 * 综合数据极坐标图
 */
public class TestPolarChart extends ChartBox {

    /**
     * 构造方法
     *
     * @param left       距左
     * @param top        距顶
     * @param width      宽度
     * @param height     高度
     * @param title      标题
     * @param names      名称(s)
     * @param r          半径
     * @param rawDataset 真实数据集
     */
    public TestPolarChart(int left, int top, int width, int height, String title, List<String> names, double r, List<List<Double>> rawDataset) {
        super(left, top, width, height, BaseChart.CreateTestPolarChart(title, names, r, rawDataset));
    }

    /**
     * 重载数据
     *
     * @param names      名称(s)
     * @param r          半径
     * @param rawDataset 真实数据集
     */
    public void reload(List<String> names, double r, List<List<Double>> rawDataset) {
        XYDataset dataset = BaseChart.CreateTestPolarData(names, r, rawDataset);
        JFreeChart chart = this.getChart();
        PolarPlot plot = (PolarPlot) chart.getPlot();
        plot.setDataset(dataset);
    }

}
