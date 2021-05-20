package com.ljscode.data;

import java.util.List;

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


}
