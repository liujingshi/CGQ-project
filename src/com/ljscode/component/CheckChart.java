package com.ljscode.component;

import com.ljscode.base.BaseChart;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 传感器检测图
 */
public class CheckChart extends ChartBox {

    /**
     * 构造方法
     *
     * @param left     距左
     * @param top      距顶
     * @param width    宽度
     * @param height   高度
     * @param cylinder 柱面数据
     * @param endFace  端面数据
     * @param deg      角度数据
     */
    public CheckChart(int left, int top, int width, int height, double cylinder, double endFace, double deg) {
        super(left, top, width, height, BaseChart.CreateCheckChart(cylinder, endFace, deg));
    }

    /**
     * 重载数据
     *
     * @param cylinder 柱面数据
     * @param endFace  端面数据
     * @param deg      角度数据
     */
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
