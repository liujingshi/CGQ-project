package com.ljscode.util;

import com.ljscode.data.LeastSquareMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数学工具
 */
public abstract class MathUtil {

    /**
     * 最小二乘法阶数
     */
    public static final int ORDER = 6;

    /**
     * 创建最小二乘法对象
     *
     * @param rawData 真实数据
     * @return 最小二乘法对象
     */
    public static LeastSquareMethod GetLeastSquareMethod(List<Double> rawData) {
        double[] xData = new double[360];
        double[] yData = new double[360];
        for (int i = 0; i < 360; i++) {
            xData[i] = i;
            yData[i] = rawData.size() <= i ? 0 : rawData.get(i);
        }
        return new LeastSquareMethod(xData, yData, ORDER);
    }

    public static LeastSquareMethod CreateLeastSquareMethod(Map<Double, Double> realData) {
        int size = realData.size();
        double[] angle = new double[size];
        double[] data = new double[size];
        int i = 0;
        for (Map.Entry<Double, Double> entry : realData.entrySet()) {
            angle[i] = entry.getKey();
            data[i] = entry.getValue();
            i++;
        }
        return new LeastSquareMethod(angle, data, ORDER);
    }

    public static double calcBeat(Map<Double, Double> aData, Map<Double, Double> bData) {
        double beat = -999999999;
        for (Map.Entry<Double, Double> entry : aData.entrySet()) {
            double aValue = entry.getValue();
            double bValue = bData.get(entry.getKey());
            double iBeat = Math.abs(aValue - bValue);
            if (iBeat > beat) {
                beat = iBeat;
            }
        }
        return beat;
    }

//    public static int[] CalcRotate(LeastSquareMethod leastSquareMethod) {
//        double[] coefficient = leastSquareMethod.getCoefficient();
//    }
}
