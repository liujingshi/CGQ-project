package com.ljscode.data;

import com.ljscode.util.DatasetUtil;
import com.ljscode.util.MathUtil;

import java.util.*;

public class ItemModel {

    private String dataId; // 数据ID
    private String dataName; // 数据名称 第1次测量数据 椎壁测量数据
    private Date createTime; // 创建时间
    private int dataIndex; // 测量次数 数据排序
    private HashMap<Double, Double> realDataCylinder; // 真实数据 柱面 侧面
    private ArrayList<Double> leastSquareMethodParamCylinder; // 最小二乘法参数 柱面 侧面
    private HashMap<Double, Double> theoryDataCylinder; // 理论数据 柱面 侧面
    private HashMap<Double, Double> realDataEndFace; // 真实数据 端面 顶面
    private ArrayList<Double> leastSquareMethodParamEndFace; // 最小二乘法参数 端面 顶面
    private HashMap<Double, Double> theoryDataEndFace; // 理论数据 端面 顶面
    private double roundness; // 圆度
    private double flatness; // 平面度
    private double axisFrom; // 同心度
    private double parallelism; // 平行度

    // 计算理论数据
    public void calcTheoryData() {
        LeastSquareMethod leastSquareMethodCylinder = MathUtil.CreateLeastSquareMethod(realDataCylinder); // 最小二乘法
        LeastSquareMethod leastSquareMethodEndFace = MathUtil.CreateLeastSquareMethod(realDataEndFace); // 最小二乘法
        leastSquareMethodParamCylinder.clear(); // 清空最小二乘法参数
        leastSquareMethodParamEndFace.clear(); // 清空最小二乘法参数
        for (double param : leastSquareMethodCylinder.getCoefficient()) { // 保存最小二乘法参数
            leastSquareMethodParamCylinder.add(param);
        }
        for (double param : leastSquareMethodEndFace.getCoefficient()) { // 保存最小二乘法参数
            leastSquareMethodParamEndFace.add(param);
        }
        theoryDataCylinder.clear(); // 理论数据
        theoryDataEndFace.clear(); // 理论数据
        for (Map.Entry<Double, Double> entry : realDataCylinder.entrySet()) {
            theoryDataCylinder.put(entry.getKey(), leastSquareMethodCylinder.fit(entry.getKey())); // 保存理论数据
        }
        for (Map.Entry<Double, Double> entry : realDataEndFace.entrySet()) {
            theoryDataEndFace.put(entry.getKey(), leastSquareMethodEndFace.fit(entry.getKey())); // 保存理论数据
        }
    }

    // 计算形位误差
    public void calcFormError(Map<Double, Double> oneLevelRealDataCylinder, Map<Double, Double> oneLevelRealDataEndFace) {
        double roundnessBeat = MathUtil.calcBeat(realDataCylinder, theoryDataCylinder);
        roundness = roundnessBeat * 2;
        flatness = MathUtil.calcBeat(realDataEndFace, theoryDataEndFace);
        if (oneLevelRealDataCylinder != null && oneLevelRealDataCylinder.size() == realDataCylinder.size()) {
            double axisFromBeat = MathUtil.calcBeat(realDataCylinder, oneLevelRealDataCylinder);
            axisFrom = axisFromBeat * 2;
        }
        if (oneLevelRealDataEndFace != null && oneLevelRealDataEndFace.size() == realDataEndFace.size()) {
            parallelism = MathUtil.calcBeat(realDataEndFace, oneLevelRealDataEndFace);
        }
    }

    public ItemModel() {
        dataId = DatasetUtil.CreateNewId();
        createTime = new Date();
        realDataCylinder = new HashMap<>();
        leastSquareMethodParamCylinder = new ArrayList<>();
        theoryDataCylinder = new HashMap<>();
        realDataEndFace = new HashMap<>();
        leastSquareMethodParamEndFace = new ArrayList<>();
        theoryDataEndFace = new HashMap<>();
        roundness = 0;
        flatness = 0;
        axisFrom = 0;
        parallelism = 0;
    }

    public ItemModel(int dataIndex) {
        this();
        this.dataIndex = dataIndex;
        this.dataName = String.format("第%d次测量数据", this.dataIndex);
    }

    public ItemModel(String dataId, String dataName, Date createTime, int dataIndex, HashMap<Double, Double> realDataCylinder, ArrayList<Double> leastSquareMethodParamCylinder, HashMap<Double, Double> theoryDataCylinder, HashMap<Double, Double> realDataEndFace, ArrayList<Double> leastSquareMethodParamEndFace, HashMap<Double, Double> theoryDataEndFace, double roundness, double flatness, double axisFrom, double parallelism) {
        this.dataId = dataId;
        this.dataName = dataName;
        this.createTime = createTime;
        this.dataIndex = dataIndex;
        this.realDataCylinder = realDataCylinder;
        this.leastSquareMethodParamCylinder = leastSquareMethodParamCylinder;
        this.theoryDataCylinder = theoryDataCylinder;
        this.realDataEndFace = realDataEndFace;
        this.leastSquareMethodParamEndFace = leastSquareMethodParamEndFace;
        this.theoryDataEndFace = theoryDataEndFace;
        this.roundness = roundness;
        this.flatness = flatness;
        this.axisFrom = axisFrom;
        this.parallelism = parallelism;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(int dataIndex) {
        this.dataIndex = dataIndex;
    }

    public HashMap<Double, Double> getRealDataCylinder() {
        return realDataCylinder;
    }

    public void setRealDataCylinder(HashMap<Double, Double> realDataCylinder) {
        this.realDataCylinder.clear();
        this.realDataCylinder.putAll(realDataCylinder);
    }

    public ArrayList<Double> getLeastSquareMethodParamCylinder() {
        return leastSquareMethodParamCylinder;
    }

    public void setLeastSquareMethodParamCylinder(ArrayList<Double> leastSquareMethodParamCylinder) {
        this.leastSquareMethodParamCylinder = leastSquareMethodParamCylinder;
    }

    public HashMap<Double, Double> getTheoryDataCylinder() {
        return theoryDataCylinder;
    }

    public void setTheoryDataCylinder(HashMap<Double, Double> theoryDataCylinder) {
        this.theoryDataCylinder = theoryDataCylinder;
    }

    public HashMap<Double, Double> getRealDataEndFace() {
        return realDataEndFace;
    }

    public void setRealDataEndFace(HashMap<Double, Double> realDataEndFace) {
        this.realDataEndFace.clear();
        this.realDataEndFace.putAll(realDataEndFace);
    }

    public ArrayList<Double> getLeastSquareMethodParamEndFace() {
        return leastSquareMethodParamEndFace;
    }

    public void setLeastSquareMethodParamEndFace(ArrayList<Double> leastSquareMethodParamEndFace) {
        this.leastSquareMethodParamEndFace = leastSquareMethodParamEndFace;
    }

    public HashMap<Double, Double> getTheoryDataEndFace() {
        return theoryDataEndFace;
    }

    public void setTheoryDataEndFace(HashMap<Double, Double> theoryDataEndFace) {
        this.theoryDataEndFace = theoryDataEndFace;
    }

    public double getRoundness() {
        return roundness;
    }

    public void setRoundness(double roundness) {
        this.roundness = roundness;
    }

    public double getFlatness() {
        return flatness;
    }

    public void setFlatness(double flatness) {
        this.flatness = flatness;
    }

    public double getAxisFrom() {
        return axisFrom;
    }

    public void setAxisFrom(double axisFrom) {
        this.axisFrom = axisFrom;
    }

    public double getParallelism() {
        return parallelism;
    }

    public void setParallelism(double parallelism) {
        this.parallelism = parallelism;
    }

    @Override
    public String toString() {
        return dataName;
    }
}
