package com.ljscode.bean;

import com.ljscode.data.DataModel;
import com.ljscode.data.LeastSquareMethod;
import com.ljscode.data.ResultModel;
import com.ljscode.util.BeanUtil;
import com.ljscode.util.ConfigUtil;
import com.ljscode.util.DatasetUtil;
import com.ljscode.util.MathUtil;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.*;

public class LineChartInfo extends BaseBean {

    private String title; // 标题
    private double rangeStart; // 理论区间开始
    private double rangeEnd; // 理论区间结束
    private double rangeLower; // y轴范围低
    private double rangeUpper; // y轴范围高
    private HashMap<Integer, Double> realData; // 实时数据
    private ResultModel resultModel; // 当前数据
    private HashMap<Integer, Double> goodData; // 理想数据
    private ArrayList<Double> leastSquareMethodParam; // 最小二乘法参数
    private String mode; // 柱面 端面 EndFace

    public boolean[] getGuide() {
        if (leastSquareMethodParam.size() == 0) {
            calcGoodData();
        }
        boolean[] result = {false, false};
        if (leastSquareMethodParam.get(0) * 100000 > 0 && leastSquareMethodParam.get(0) * 100000 > 100000) {
            result[1] = false;
        } else if (leastSquareMethodParam.get(0) * 100000 < 0 && leastSquareMethodParam.get(0) * 100000 < -100000) {
            result[1] = true;
        } else if (leastSquareMethodParam.get(2) * 100000 > 0 && leastSquareMethodParam.get(3) * 100000 < 0 && leastSquareMethodParam.get(4) * 100000 > 0 && leastSquareMethodParam.get(5) * 100000 < 0) {
            result[0] = false;
        } else if (leastSquareMethodParam.get(2) * 100000 < 0 && leastSquareMethodParam.get(3) * 100000 > 0 && leastSquareMethodParam.get(4) * 100000 < 0 && leastSquareMethodParam.get(5) * 100000 > 0) {
            result[0] = true;
        }
        return result;
    }

    public int getErrorPointNumber() {
        RangeConfig rangeConfig = ConfigUtil.GetRangeConfig();
        double start = 0;
        double end = 0;
        if (mode.equals("EndFace")) {
            start = rangeConfig.getEndFaceStart();
            end = rangeConfig.getEndFaceEnd();
        } else {
            start = rangeConfig.getCylinderStart();
            end = rangeConfig.getCylinderEnd();
        }
        int result = 0;
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            if (entry.getValue() < start || entry.getValue() > end) {
                result++;
            }
        }
        return result;
    }

    public List<Object[]> getErrorPoint() {
        RangeConfig rangeConfig = ConfigUtil.GetRangeConfig();
        double start = 0;
        double end = 0;
        if (mode.equals("EndFace")) {
            start = rangeConfig.getEndFaceStart();
            end = rangeConfig.getEndFaceEnd();
        } else {
            start = rangeConfig.getCylinderStart();
            end = rangeConfig.getCylinderEnd();
        }
        List<Object[]> result = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            if (entry.getValue() < start || entry.getValue() > end) {
                result.add(new Object[]{entry.getKey() * 360d / 4096d, entry.getValue(), entry.getValue() < start ? start - entry.getValue() : entry.getValue() - end});
            }
        }
        return result;
    }

    public XYSeriesCollection CreateLineData() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries rangeStartGoals = new XYSeries("理论区间起");
        XYSeries rangeEndGoals = new XYSeries("理论区间止");
        XYSeries realGoals = new XYSeries("真实数据");
        XYSeries goodGoals = new XYSeries("拟合数据");
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            rangeStartGoals.add(entry.getKey() * 360d / 4096d, (Double) rangeStart);
            rangeEndGoals.add(entry.getKey() * 360d / 4096d, (Double) rangeEnd);
            realGoals.add(entry.getKey() * 360d / 4096d, entry.getValue());
            goodGoals.add(entry.getKey() * 360d / 4096d, goodData.get(entry.getKey()));
        }
        dataset.addSeries(rangeStartGoals);
        dataset.addSeries(rangeEndGoals);
        dataset.addSeries(realGoals);
        dataset.addSeries(goodGoals);
        DataModel level1Data = BeanUtil.GetLevel1Data(resultModel);
        if (level1Data != null && BeanUtil.GetCurrentItemModel(level1Data) != null) {
            Map<Integer, Double> level1GoodData = null;
            if (mode.equals("EndFace")) {
                level1GoodData = BeanUtil.GetCurrentItemModel(level1Data).getTheoryDataEndFace();
            } else {
                level1GoodData = BeanUtil.GetCurrentItemModel(level1Data).getTheoryDataCylinder();
            }
            if (level1GoodData != null && level1GoodData.size() > 0) {
                XYSeries level1Goals = new XYSeries(level1Data.getDataName());
                for (Map.Entry<Integer, Double> entry : level1GoodData.entrySet()) {
                    level1Goals.add(entry.getKey() * 360d / 4096d, entry.getValue());
                }
                dataset.addSeries(level1Goals);
            }
        }
        return dataset;
    }

    // 计算理论数据
    public void calcGoodData() {
        LeastSquareMethod leastSquareMethod = MathUtil.CreateLeastSquareMethod(realData); // 最小二乘法
        leastSquareMethodParam.clear(); // 清空最小二乘法参数
        for (double param : leastSquareMethod.getCoefficient()) { // 保存最小二乘法参数
            leastSquareMethodParam.add(param);
        }
        goodData.clear(); // 理论数据
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            goodData.put(entry.getKey(), leastSquareMethod.fit(entry.getKey())); // 保存理论数据
        }
    }

    public LineChartInfo() {
        realData = new HashMap<>();
        leastSquareMethodParam = new ArrayList<>();
        goodData = new HashMap<>();
    }

    public LineChartInfo(String title, double rangeStart, double rangeEnd, double rangeLower, double rangeUpper, ResultModel resultModel, String mode) {
        this();
        this.title = title;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.rangeLower = rangeLower;
        this.rangeUpper = rangeUpper;
        this.resultModel = resultModel;
        this.mode = mode;
    }

    public LineChartInfo(String title, double rangeStart, double rangeEnd, double rangeLower, double rangeUpper, HashMap<Integer, Double> realData, ResultModel resultModel, HashMap<Integer, Double> goodData, ArrayList<Double> leastSquareMethodParam, String mode) {
        this.title = title;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.rangeLower = rangeLower;
        this.rangeUpper = rangeUpper;
        this.realData = realData;
        this.resultModel = resultModel;
        this.goodData = goodData;
        this.leastSquareMethodParam = leastSquareMethodParam;
        this.mode = mode;
        calcGoodData();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(double rangeStart) {
        this.rangeStart = rangeStart;
    }

    public double getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(double rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public double getRangeLower() {
        return rangeLower;
    }

    public void setRangeLower(double rangeLower) {
        this.rangeLower = rangeLower;
    }

    public double getRangeUpper() {
        return rangeUpper;
    }

    public void setRangeUpper(double rangeUpper) {
        this.rangeUpper = rangeUpper;
    }

    public HashMap<Integer, Double> getRealData() {
        return realData;
    }

    public void setRealData(HashMap<Integer, Double> realData) {
        this.realData = realData;
        calcGoodData();
    }

    public ResultModel getResultModel() {
        return resultModel;
    }

    public void setResultModel(ResultModel resultModel) {
        this.resultModel = resultModel;
    }

    public HashMap<Integer, Double> getGoodData() {
        return goodData;
    }

    public void setGoodData(HashMap<Integer, Double> goodData) {
        this.goodData = goodData;
    }

    public ArrayList<Double> getLeastSquareMethodParam() {
        return leastSquareMethodParam;
    }

    public void setLeastSquareMethodParam(ArrayList<Double> leastSquareMethodParam) {
        this.leastSquareMethodParam = leastSquareMethodParam;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
