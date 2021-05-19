package com.ljscode.data;

import java.util.List;

public abstract class MathUtil {

    public static final int ORDER = 6;

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
