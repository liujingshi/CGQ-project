package com.ljscode.base;

import com.ljscode.component.BarCustomRender;
import com.ljscode.data.LeastSquareMethod;
import com.ljscode.data.MathUtil;
import com.ljscode.data.UnitData;
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
import org.jfree.data.xy.XYDataset;
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

    public static Font font;

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/Alibaba-PuHuiTi.ttf"));
            font = font.deriveFont(16L);
        } catch (FontFormatException | IOException e) {
            font = new Font("宋体", Font.BOLD, 16);
            e.printStackTrace();
        }
    }

    public static XYPlot SetXYChartFont(JFreeChart chart) {
        chart.getTitle().setFont(font);
        chart.getLegend().setItemFont(font);
        XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setLabelFont(font);
        plot.getDomainAxis().setTickLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);
        plot.getRangeAxis().setTickLabelFont(font);
        return plot;
    }

    public static CategoryPlot SetCategoryChartFont(JFreeChart chart) {
        chart.getTitle().setFont(font);
        chart.getLegend().setItemFont(font);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.getDomainAxis().setLabelFont(font);
        plot.getDomainAxis().setTickLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);
        plot.getRangeAxis().setTickLabelFont(font);
        return plot;
    }

    /**
     * 创建折线图
     *
     * @param title      标题
     * @param xAxisLabel x轴名称
     * @param yAxisLabel y轴名称
     * @param dataName   数据名称
     * @param xData      x轴数据
     * @param yData      y轴数据
     * @return 图表 JFreeChart对象
     */
    public static JFreeChart CreateXYLineChart(String title, String xAxisLabel, String yAxisLabel, String dataName,
                                               List<Double> xData, List<Double> yData) {
        XYSeries goals = new XYSeries(dataName);
        for (int i = 0; i < xData.size(); i++) {
            goals.add(xData.get(i), yData.get(i));
        }
        XYDataset dataset = new XYSeriesCollection(goals);
        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);
        SetXYChartFont(chart);
        return chart;
    }

    /**
     * 创建柱状图
     *
     * @param title      标题
     * @param xAxisLabel x轴名称
     * @param yAxisLabel y轴名称
     * @param dataName   数据名称
     * @param category   x轴数据
     * @param yData      y轴数据
     * @return 图表 JFreeChart对象
     */
    public static JFreeChart CreateBarChart(String title, String xAxisLabel, String yAxisLabel, String dataName,
                                            List<String> category, List<Double> yData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < category.size(); i++) {
            dataset.addValue(yData.get(i), dataName, category.get(i));
        }
        JFreeChart chart = ChartFactory.createBarChart(title, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);
        SetCategoryChartFont(chart);
        return chart;
    }

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
        LeastSquareMethod leastSquareMethod = MathUtil.GetLeastSquareMethod(rawDataList);
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

    public static JFreeChart CreateTestLineChart(String title, List<UnitData> rawData, List<UnitData> yData, String mode) {
        XYSeriesCollection dataset = CreateTestLineData(rawData, yData, mode);
        JFreeChart chart = ChartFactory.createXYLineChart(title, "角度", "数据", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = SetXYChartFont(chart);
        plot.getRangeAxis().setRange(-1, 1);
        return chart;
    }

    public static XYSeriesCollection CreateTestPolarData(List<String> names, double r, List<List<Double>> rawDataset) {
        List<LeastSquareMethod> leastSquareMethods = new ArrayList<>();
        List<XYSeries> goals = new ArrayList<>();
        for (int i = 0; i < rawDataset.size(); i++) {
            goals.add(new XYSeries(names.get(i)));
            leastSquareMethods.add(MathUtil.GetLeastSquareMethod(rawDataset.get(i)));
        }
        for (int i = 0; i < 360; i++) {
            for (int j = 0; j < rawDataset.size(); j++) {
                goals.get(j).add(i, r + leastSquareMethods.get(j).fit(i));
            }
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (XYSeries goal : goals) {
            dataset.addSeries(goal);
        }
        return dataset;
    }

    public static JFreeChart CreateTestPolarChart(String title, List<String> names, double r, List<List<Double>> rawDataset) {
        XYSeriesCollection dataset = CreateTestPolarData(names, r, rawDataset);
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
