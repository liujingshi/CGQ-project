package com.ljscode.component;

import com.ljscode.base.BaseChart;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.bean.Adjust;
import com.ljscode.bean.LineChartInfo;
import com.ljscode.data.UnitData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

/**
 * 检测折线图
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
     * @param rawData 真实数据
     * @param yData   第1次测量数据
     * @param mode    模式 柱面/端面
     */
    public TestLineChart(int left, int top, int width, int height, String title, List<UnitData> rawData, List<UnitData> yData,
                         String mode, BaseMouseListener<Adjust> event, BaseMouseListener<Adjust> event1) {
        super(left, top, width, height, BaseChart.CreateTestLineChart(title, rawData, yData, mode, event, event1));
    }

    public TestLineChart(int left, int top, int width, int height, LineChartInfo lineChartInfo) {
        super(left, top, width, height, BaseChart.CreateLineChart(lineChartInfo));
    }

    /**
     * 构造方法
     *
     * @param rawData 真实数据
     * @param yData   第1次测量数据
     * @param mode    模式 柱面/端面
     */
    public void reload(List<UnitData> rawData, List<UnitData> yData, String mode, BaseMouseListener<Adjust> event, BaseMouseListener<Adjust> event1) {
        XYSeriesCollection dataset = BaseChart.CreateTestLineData(rawData, yData, mode, event, event1);
        JFreeChart chart = this.getChart();
        XYPlot plot = chart.getXYPlot();
        plot.setDataset(dataset);
    }

    public void reload(LineChartInfo lineChartInfo) {
        XYSeriesCollection dataset = lineChartInfo.CreateLineData();
        JFreeChart chart = this.getChart();
        XYPlot plot = chart.getXYPlot();
        plot.setDataset(dataset);
    }

}
