package com.ljscode.component;

import com.ljscode.base.BaseChart;
import com.ljscode.data.UnitData;
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
     * @param mode 模式 柱面/端面
     */
    public TestLineChart(int left, int top, int width, int height, String title, List<UnitData> rawData, List<UnitData> yData, String mode) {
        super(left, top, width, height, BaseChart.CreateTestLineChart(title, rawData, yData, mode));
    }

    public void reload(List<UnitData> rawData, List<UnitData> yData, String mode) {
        XYSeriesCollection dataset = BaseChart.CreateTestLineData(rawData, yData, mode);
        JFreeChart chart = this.getChart();
        XYPlot plot = chart.getXYPlot();
        plot.setDataset(dataset);
    }

}
