package com.ljscode.component;

import com.ljscode.base.BaseColor;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;

/**
 * 图表容器
 */
public class ChartBox extends ChartPanel {

    /**
     * 构造方法
     *
     * @param chart 图表 JFreeChart对象
     */
    public ChartBox(JFreeChart chart) {
        super(chart);
//        chart.setBackgroundPaint(ChartColor.WHITE);
//        chart.setBackgroundImageAlpha(0);
        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(ChartColor.WHITE);
        plot.setBackgroundAlpha(0);
        this.setBackground(null);
        this.setOpaque(false);
    }

    /**
     * 构造方法
     *
     * @param left   距左
     * @param top    距顶
     * @param width  宽度
     * @param height 高度
     * @param chart  图表 JFreeChart对象
     */
    public ChartBox(int left, int top, int width, int height, JFreeChart chart) {
        this(chart);
        this.setBounds(left, top, width, height);
    }

}
