package com.ljscode.component;

import com.ljscode.base.BaseChart;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

public class CheckChart extends ChartBox {

    public CheckChart(int left, int top, int width, int height, double cylinder, double endFace, double deg) {
        super(left, top, width, height, BaseChart.CreateCheckChart(cylinder, endFace, deg));
    }

    public void reload(double cylinder, double endFace, double deg) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(cylinder, "传感器校准", "柱面传感器");
        dataset.addValue(endFace, "传感器校准", "端面传感器");
        dataset.addValue(deg, "传感器校准", "角度传感器");
        JFreeChart chart = this.getChart();
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setDataset(dataset);
    }

}
