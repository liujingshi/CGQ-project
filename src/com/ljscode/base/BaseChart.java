package com.ljscode.base;

import com.ljscode.bean.Adjust;
import com.ljscode.bean.LineChartInfo;
import com.ljscode.component.BarCustomRender;
import com.ljscode.data.*;
import com.ljscode.util.FontUtil;
import com.ljscode.util.MathUtil;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.DefaultPolarItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基础图表
 */
public abstract class BaseChart {

    /**
     * 字体
     */
    public static Font font = FontUtil.LoadFont(24);

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
        dataset.addValue(deg, "传感器校准", "角度编码器");
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
        plot.getRangeAxis().setRange(-300.00, 300.00);
        plot.setRenderer(0, barRenderer);
        plot.setRangeGridlinePaint(ChartColor.BLACK);

        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        plot.setRenderer(1, lineRenderer);
        lineRenderer.setBaseShapesVisible(false);
        lineRenderer.setSeriesPaint(0, ChartColor.RED);
        lineRenderer.setSeriesPaint(1, ChartColor.RED);

        DefaultCategoryDataset datasetLine = new DefaultCategoryDataset();
        datasetLine.addValue(-1, "标准区间-", "柱面传感器");
        datasetLine.addValue(-1, "标准区间-", "端面传感器");
        datasetLine.addValue(-1, "标准区间-", "角度编码器");
        datasetLine.addValue(1, "标准区间+", "柱面传感器");
        datasetLine.addValue(1, "标准区间+", "端面传感器");
        datasetLine.addValue(1, "标准区间+", "角度编码器");
        plot.setDataset(0, dataset);
        plot.setDataset(1, datasetLine);

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
    public static XYSeriesCollection CreateTestLineData(List<UnitData> rawData, List<UnitData> yData, String mode,
                                                        BaseMouseListener<Adjust> event, BaseMouseListener<Adjust> event1) {
        boolean hasYData = yData != null && yData.size() >= 360; // 判断yData是否存在
        List<Double> rawDataList = new ArrayList<>();
        List<Double> yDataList = new ArrayList<>();
        for (int i = 0; i < 360; i++) {
            AddListByMode(rawData, mode, rawDataList, i);
            if (hasYData) {
                AddListByMode(yData, mode, yDataList, i);
            }
        }
        LeastSquareMethod leastSquareMethod = MathUtil.GetLeastSquareMethod(rawDataList); // 创建最小二乘法数据
        double[] cs = leastSquareMethod.getCoefficient();
        if (cs[0] * 100000 > 0 && cs[0] * 100000 > 100000) {
            if (event != null)
                event.mouseClicked(new Adjust("旋钮2", false));
            if (event1 != null)
                event1.mouseClicked(new Adjust("旋钮2", false));
        } else if (cs[0] * 100000 < 0 && cs[0] * 100000 < -100000) {
            if (event != null)
                event.mouseClicked(new Adjust("旋钮2", true));
            if (event1 != null)
                event1.mouseClicked(new Adjust("旋钮2", true));
        } else if (cs[2] * 100000 > 0 && cs[3] * 100000 < 0 && cs[4] * 100000 > 0 && cs[5] * 100000 < 0) {
            if (event != null)
                event.mouseClicked(new Adjust("旋钮1", false));
            if (event1 != null)
                event1.mouseClicked(new Adjust("旋钮1", false));
        } else if (cs[2] * 100000 < 0 && cs[3] * 100000 > 0 && cs[4] * 100000 < 0 && cs[5] * 100000 > 0) {
            if (event != null)
                event.mouseClicked(new Adjust("旋钮1", true));
            if (event1 != null)
                event1.mouseClicked(new Adjust("旋钮1", true));
        }
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

    private static void AddListByMode(List<UnitData> yData, String mode, List<Double> yDataList, int i) {
        UnitData itemY = UnitData.FindByDeg(yData, i);
        if (itemY != null) {
            switch (mode) {
                case BaseConfig.Cylinder:
                    yDataList.add(itemY.getCylinder());
                    break;
                case BaseConfig.EndFace:
                    yDataList.add(itemY.getEndFace());
                    break;
            }
        }
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
    public static JFreeChart CreateTestLineChart(String title, List<UnitData> rawData, List<UnitData> yData, String mode,
                                                 BaseMouseListener<Adjust> event, BaseMouseListener<Adjust> event1) {
        XYSeriesCollection dataset = CreateTestLineData(rawData, yData, mode, event, event1);
        JFreeChart chart = ChartFactory.createXYLineChart(title, "角度", "数据", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = SetXYChartFont(chart);
        plot.getRangeAxis().setRange(-10, 10);
        return chart;
    }

    public static JFreeChart CreateLineChart(LineChartInfo lineChartInfo) {
        XYSeriesCollection dataset = lineChartInfo.CreateLineData();
        JFreeChart chart = ChartFactory.createXYLineChart(lineChartInfo.getTitle(), "角度", "数据", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = SetXYChartFont(chart);
        plot.getRangeAxis().setRange(lineChartInfo.getRangeLower(), lineChartInfo.getRangeUpper());
        plot.setRangeGridlinePaint(ChartColor.BLACK);
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, ChartColor.RED);
        renderer.setSeriesPaint(1, ChartColor.RED);
        renderer.setSeriesPaint(2, ChartColor.GRAY);
        renderer.setSeriesPaint(3, ChartColor.GREEN);
        renderer.setSeriesPaint(4, ChartColor.YELLOW);
        return chart;
    }

    /**
     * 创建综合雷达数据
     *
     * @param data 数据
     * @return XYSeriesCollection dataset
     */
    public static XYSeriesCollection CreateTestPolarData(TestData data) {
        List<LeastSquareMethod> leastSquareMethods = new ArrayList<>();
        List<XYSeries> goals = new ArrayList<>();
        List<ItemData> itemData = new ArrayList<>();
        if (data.getData1() != null && data.getData1().isCheckCylinder())
            itemData.add(data.getData1());
        if (data.getData2() != null && data.getData2().isCheckCylinder())
            itemData.add(data.getData2());
        if (data.getData3() != null && data.getData3().isCheckCylinder())
            itemData.add(data.getData3());
        if (data.getData4() != null && data.getData4().isCheckCylinder())
            itemData.add(data.getData4());
        if (data.getData5() != null && data.getData5().isCheckCylinder())
            itemData.add(data.getData5());
        for (int i = 0; i < itemData.size(); i++) {
            goals.add(new XYSeries(String.format("第%d级测量数据", i+1)));
            List<Double> rawData = new ArrayList<>();
            for (UnitData unit : itemData.get(i).getData()) {
                rawData.add(unit.getCylinder());
            }
            leastSquareMethods.add(MathUtil.GetLeastSquareMethod(rawData));
        }
        for (int i = 0; i < 360; i++) {
            for (int j = 0; j < itemData.size(); j++) {
                goals.get(j).add(i, data.getR() + leastSquareMethods.get(j).fit(i));
            }
        }
        if (data.getInsideData() != null && data.getInsideData().isCheckCylinder()) {
            List<Double> rawData = new ArrayList<>();
            for (UnitData unit : data.getInsideData().getData()) {
                rawData.add(unit.getCylinder());
            }
            LeastSquareMethod leastSquareMethod = MathUtil.GetLeastSquareMethod(rawData);
            XYSeries goal = new XYSeries("椎壁测量数据");
            for (int i = 0; i < 360; i++) {
                goal.add(i, data.getR() + leastSquareMethod.fit(i));
            }
            goals.add(goal);
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
     * @return JFreeChart
     */
    public static JFreeChart CreateTestPolarChart(String title, TestData data) {
        XYSeriesCollection dataset = CreateTestPolarData(data);
        JFreeChart chart = ChartFactory.createPolarChart(title, dataset, true, true, false);
        return setPolarFont(chart);
    }

    public static JFreeChart CreatePolarChart(ResultModel resultModel) {
//        XYSeriesCollection dataset = resultModel.CreatePolarData();
        XYSeriesCollection dataset = resultModel.CreatePolarDataCalc();
        JFreeChart chart = ChartFactory.createPolarChart("综合数据", dataset, true, true, false);
        return setPolarFont(chart);
    }

    private static JFreeChart setPolarFont(JFreeChart chart) {
        chart.getTitle().setFont(font);
        chart.getLegend().setItemFont(font);
        PolarPlot plot = (PolarPlot) chart.getPlot();
        plot.getAxis().setLabelFont(font);
        plot.getAxis().setRange(0, 100);
        DefaultPolarItemRenderer renderer = new DefaultPolarItemRenderer();
        renderer.setShapesVisible(false);
        plot.setRenderer(renderer);
        plot.setAngleGridlinePaint(ChartColor.BLACK);
        plot.setRadiusGridlinePaint(ChartColor.BLACK);
        plot.setAngleLabelFont(font);
        return chart;
    }
}
