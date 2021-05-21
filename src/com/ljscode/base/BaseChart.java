package com.ljscode.base;

import com.ljscode.component.BarCustomRender;
import com.ljscode.data.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.DefaultPolarItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础图表
 */
public abstract class BaseChart {

    /**
     * 字体
     */
    public static Font font;

    // 实体化字体
    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/Alibaba-PuHuiTi.ttf"));
            font = font.deriveFont(16L);
        } catch (FontFormatException | IOException e) {
            font = new Font("宋体", Font.BOLD, 16);
            e.printStackTrace();
        }
    }

    /**
     * 设置XYPlot字体
     *
     * @param chart JFreeChart
     * @return XYPlot
     */
    public static XYPlot SetXYChartFont(JFreeChart chart) {
        chart.getTitle().setFont(font); // 设置标题字体
        chart.getLegend().setItemFont(font); // 设置图例字体
        XYPlot plot = chart.getXYPlot(); // 获取Plot
        plot.getDomainAxis().setLabelFont(font); // 设置X轴标签字体
        plot.getDomainAxis().setTickLabelFont(font); // 设置X轴刻度字体
        plot.getRangeAxis().setLabelFont(font); // 设置Y轴标签字体
        plot.getRangeAxis().setTickLabelFont(font); // 设置Y轴刻度字体
        return plot;
    }

    /**
     * 创建传感器校准图
     *
     * @param cylinder 柱面传感器数值
     * @param endFace  端面传感器数值
     * @param deg      角度传感器数值
     * @return JFreeChart
     */
    public static JFreeChart CreateCheckChart(double cylinder, double endFace, double deg) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(cylinder, "传感器校准", "柱面传感器");
        dataset.addValue(endFace, "传感器校准", "端面传感器");
        dataset.addValue(deg, "传感器校准", "角度传感器");
        JFreeChart chart = ChartFactory.createBarChart("", "", "", dataset,
                PlotOrientation.VERTICAL, false, false, false);
        CategoryPlot plot = chart.getCategoryPlot();
        BarCustomRender barRenderer = new BarCustomRender();
        barRenderer.setMinimumBarLength(0.1);
        barRenderer.setMaximumBarWidth(0.1);
        barRenderer.setIncludeBaseInRange(true);
        barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer.setBaseItemLabelsVisible(true);
        barRenderer.setBarPainter(new StandardBarPainter());
        barRenderer.setBaseItemLabelFont(font);
        plot.getDomainAxis().setLabelFont(font);
        plot.getDomainAxis().setTickLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);
        plot.getRangeAxis().setTickLabelFont(font);
        plot.getRangeAxis().setRange(-5.00, 5.00);
        plot.setRenderer(barRenderer);
        return chart;
    }

    /**
     * 创建检测折线图数据
     *
     * @param rawData 真实数据
     * @param yData   第1次测量数据
     * @param mode    模式(柱面/端面)
     * @return XYSeriesCollection dataset
     */
    public static XYSeriesCollection CreateTestLineData(List<UnitData> rawData, List<UnitData> yData, String mode) {
        boolean hasYData = yData != null && yData.size() >= 360; // 判断yData是否存在
        List<Double> rawDataList = new ArrayList<>();
        List<Double> yDataList = new ArrayList<>();
        for (int i = 0; i < 360; i++) {
            UnitData item = UnitData.FindByDeg(rawData, i);
            if (item != null) {
                switch (mode) {
                    case BaseConfig.Cylinder:
                        rawDataList.add(item.getCylinder());
                        break;
                    case BaseConfig.EndFace:
                        rawDataList.add(item.getEndFace());
                        break;
                }
            }
            if (hasYData) {
                UnitData itemY = UnitData.FindByDeg(yData, i);
                if (itemY != null) {
                    yDataList.add(itemY.getEndFace());
                }
            }
        }
        LeastSquareMethod leastSquareMethod = MathUtil.GetLeastSquareMethod(rawDataList); // 创建最小二乘法数据
        LeastSquareMethod leastSquareMethodY = hasYData ? MathUtil.GetLeastSquareMethod(yDataList) : null;
        XYSeries rawGoals = new XYSeries("实时数据");
        XYSeries lsmGoals = new XYSeries("拟合数据");
        XYSeries yGoals = new XYSeries("第1次测量数据");
        for (int i = 0; i < 360; i++) {
            rawGoals.add(i, rawDataList.size() <= i ? 0 : rawDataList.get(i));
            lsmGoals.add(i, leastSquareMethod.fit(i));
            if (hasYData)
                yGoals.add(i, leastSquareMethodY.fit(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(rawGoals);
        dataset.addSeries(lsmGoals);
        if (hasYData)
            dataset.addSeries(yGoals);
        return dataset;
    }

    /**
     * 创建检测折线图
     *
     * @param title   标题
     * @param rawData 真实数据
     * @param yData   第1次测量数据
     * @param mode    模式(柱面/端面)
     * @return JFreeChart
     */
    public static JFreeChart CreateTestLineChart(String title, List<UnitData> rawData, List<UnitData> yData, String mode) {
        XYSeriesCollection dataset = CreateTestLineData(rawData, yData, mode);
        JFreeChart chart = ChartFactory.createXYLineChart(title, "角度", "数据", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = SetXYChartFont(chart);
        plot.getRangeAxis().setRange(-1, 1);
        return chart;
    }

    /**
     * 创建综合雷达数据
     *
     * @param data 数据
     * @param r    半径
     * @return XYSeriesCollection dataset
     */
    public static XYSeriesCollection CreateTestPolarData(TestData data, double r) {
        List<LeastSquareMethod> leastSquareMethods = new ArrayList<>();
        List<XYSeries> goals = new ArrayList<>();
        List<ItemData> itemData = new ArrayList<>();
        if (data.getData1().isCheckCylinder())
            itemData.add(data.getData1());
        if (data.getData2().isCheckCylinder())
            itemData.add(data.getData2());
        if (data.getData3().isCheckCylinder())
            itemData.add(data.getData3());
        if (data.getData4().isCheckCylinder())
            itemData.add(data.getData4());
        for (ItemData item : itemData) {
            goals.add(new XYSeries(item.getName()));
            List<Double> rawData = new ArrayList<>();
            for (UnitData unit : item.getData()) {
                rawData.add(unit.getCylinder());
            }
            leastSquareMethods.add(MathUtil.GetLeastSquareMethod(rawData));
        }
        for (int i = 0; i < 360; i++) {
            for (int j = 0; j < itemData.size(); j++) {
                goals.get(j).add(i, r + leastSquareMethods.get(j).fit(i));
            }
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (XYSeries goal : goals) {
            dataset.addSeries(goal);
        }
        return dataset;
    }

    /**
     * 创建综合雷达图
     *
     * @param title 标题
     * @param data  数据
     * @param r     半径
     * @return JFreeChart
     */
    public static JFreeChart CreateTestPolarChart(String title, TestData data, double r) {
        XYSeriesCollection dataset = CreateTestPolarData(data, r);
        JFreeChart chart = ChartFactory.createPolarChart(title, dataset, true, true, false);
        chart.getTitle().setFont(font);
        chart.getLegend().setItemFont(font);
        PolarPlot plot = (PolarPlot) chart.getPlot();
        plot.getAxis().setLabelFont(font);
        plot.getAxis().setRange(0, 30);
        DefaultPolarItemRenderer renderer = new DefaultPolarItemRenderer();
        renderer.setShapesVisible(false);
        plot.setRenderer(renderer);
        return chart;
    }
}
