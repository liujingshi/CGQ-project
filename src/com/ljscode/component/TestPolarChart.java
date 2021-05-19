package com.ljscode.component;

import com.ljscode.base.BaseChart;
import com.ljscode.data.LeastSquareMethod;
import com.ljscode.data.MathUtil;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

/**
 * 极坐标
 */
public class TestPolarChart extends ChartBox {

    private String title;

    public TestPolarChart(int left, int top, int width, int height, String title, List<String> names, double r, List<List<Double>> rawDataset) {
        super(left, top, width, height, BaseChart.CreateTestPolarChart(title, names, r, rawDataset));
        this.title = title;
    }

    public void reload(List<String> names, double r, List<List<Double>> rawDataset) {
        XYDataset dataset = BaseChart.CreateTestPolarData(names, r, rawDataset);
        JFreeChart chart = this.getChart();
        PolarPlot plot = (PolarPlot) chart.getPlot();
        plot.setDataset(dataset);
    }

}
