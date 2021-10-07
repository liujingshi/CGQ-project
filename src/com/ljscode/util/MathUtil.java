package com.ljscode.util;

import com.ljscode.data.LeastSquareMethod;
import com.sun.org.apache.xpath.internal.objects.XBoolean;

import java.util.ArrayList;
import java.util.HashMap;
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

    public static LeastSquareMethod CreateLeastSquareMethod(Map<Integer, Double> realData) {
        int size = realData.size();
        double[] angle = new double[size];
        double[] data = new double[size];
        int i = 0;
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            angle[i] = entry.getKey();
            data[i] = entry.getValue();
            i++;
        }
        return new LeastSquareMethod(angle, data, ORDER);
    }

    public static LeastSquareMethod CreateLeastSquareMethod(List<Double> c) {
        return new LeastSquareMethod(c);
    }


    public static double calcBeat(Map<Integer, Double> aData, Map<Integer, Double> bData) {
        double beat = -999999999;
        for (Map.Entry<Integer, Double> entry : aData.entrySet()) {
            double aValue = entry.getValue();
            double bValue = bData.get(entry.getKey());
            double iBeat = Math.abs(aValue - bValue);
            if (iBeat > beat) {
                beat = iBeat;
            }
        }
        return beat;
    }

    public static double calcAngleBeat(HashMap<Integer, Double> realData) {
        int rc = 50;
        List<double[]> points = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            points.add(new double[] { (rc+entry.getValue())*Math.cos(Math.PI*entry.getKey()/2048d), (rc+entry.getValue())*Math.sin(Math.PI*entry.getKey()/2048d) });
        }
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(points, 0);
        double minBeat = 999999999;
        double maxBeat = -99999999;
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            double beat = (rc+entry.getValue())-leastSquareMethod.angleR-leastSquareMethod.angleA*Math.cos(Math.PI*entry.getKey()/2048d)-leastSquareMethod.angleB*Math.sin(Math.PI*entry.getKey()/2048d);
            if (beat > maxBeat) {
                maxBeat = beat;
            }
            if (beat < minBeat) {
                minBeat = beat;
            }
        }
        return maxBeat - minBeat;
    }

    public static double calcAngleBeat(HashMap<Integer, Double> realData, Map<Integer, Double> realData1) {
        int rc = 50;
        List<double[]> points = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            points.add(new double[] { (rc+entry.getValue())*Math.cos(Math.PI*entry.getKey()/2048d), (rc+entry.getValue())*Math.sin(Math.PI*entry.getKey()/2048d) });
        }
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(points, 0);
        List<double[]> points1 = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData1.entrySet()) {
            points1.add(new double[] { (rc+entry.getValue())*Math.cos(Math.PI*entry.getKey()/2048d), (rc+entry.getValue())*Math.sin(Math.PI*entry.getKey()/2048d) });
        }
        LeastSquareMethod leastSquareMethod1 = new LeastSquareMethod(points1, 0);
        return Math.abs(leastSquareMethod.angleR - leastSquareMethod1.angleR);
    }

    public static double calcPlantBeat(HashMap<Integer, Double> realData) {
        int rc = 50;
        List<double[]> points = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            points.add(new double[] { rc*Math.cos(Math.PI*entry.getKey()/2048d), rc*Math.sin(Math.PI*entry.getKey()/2048d), entry.getValue() });
        }
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(points, true);
        double minBeat = 999999999;
        double maxBeat = -99999999;
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            double beat = entry.getValue()-leastSquareMethod.planeA*rc*Math.cos(Math.PI*entry.getKey()/2048d)-leastSquareMethod.planeB*rc*Math.sin(Math.PI*entry.getKey()/2048d)-leastSquareMethod.planeD;
            if (beat > maxBeat) {
                maxBeat = beat;
            }
            if (beat < minBeat) {
                minBeat = beat;
            }
        }
        return maxBeat - minBeat;
    }

    public static double calcPlantBeat(HashMap<Integer, Double> realData, Map<Integer, Double> realData1) {
        int rc = 50;
        List<double[]> points = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            points.add(new double[] { rc*Math.cos(Math.PI*entry.getKey()/2048d), rc*Math.sin(Math.PI*entry.getKey()/2048d), entry.getValue() });
        }
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(points, true);
        List<double[]> points1 = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData1.entrySet()) {
            points1.add(new double[] { rc*Math.cos(Math.PI*entry.getKey()/2048d), rc*Math.sin(Math.PI*entry.getKey()/2048d), entry.getValue() });
        }
        LeastSquareMethod leastSquareMethod1 = new LeastSquareMethod(points1, true);
        double minBeat = 999999999;
        double maxBeat = -99999999;
        for (Map.Entry<Integer, Double> entry : realData1.entrySet()) {
            double beat = entry.getValue()-leastSquareMethod.planeA*rc*Math.cos(Math.PI*entry.getKey()/2048d)-leastSquareMethod.planeB*rc*Math.sin(Math.PI*entry.getKey()/2048d)-leastSquareMethod.planeD;
            if (beat > maxBeat) {
                maxBeat = beat;
            }
            if (beat < minBeat) {
                minBeat = beat;
            }
        }
        return maxBeat - minBeat;
    }

    public static double calcPlantBeat(HashMap<Integer, Double> realData, Map<Integer, Double> realData1, boolean me) {
        int rc = 50;
        List<double[]> points = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            points.add(new double[] { rc*Math.cos(Math.PI*entry.getKey()/2048d), rc*Math.sin(Math.PI*entry.getKey()/2048d), entry.getValue() });
        }
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(points, true);
        List<double[]> points1 = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData1.entrySet()) {
            points1.add(new double[] { rc*Math.cos(Math.PI*entry.getKey()/2048d), rc*Math.sin(Math.PI*entry.getKey()/2048d), entry.getValue() });
        }
        LeastSquareMethod leastSquareMethod1 = new LeastSquareMethod(points1, true);
        double resultMin = 99999999;
        double resultMax = -99999999;
        double result = -99999999;
        for (int i = 0; i < 4096; i++) {
            double x = rc*Math.cos(Math.PI*i/2048);
            double y = rc*Math.sin(Math.PI*i/2048);
            double z = -(leastSquareMethod.planeA*x+leastSquareMethod.planeB*y+leastSquareMethod.planeD)/leastSquareMethod.planeC;
            double z1 = -(leastSquareMethod1.planeA*x+leastSquareMethod1.planeB*y+leastSquareMethod1.planeD)/leastSquareMethod1.planeC;
            double beat = z-z1;
            if (beat > resultMax) {
                resultMax = beat;
            }
            if (beat < resultMin) {
                resultMin = beat;
            }
            if (Math.abs(beat) > result) {
                result =  Math.abs(beat);
            }
        }
        return result;
    }

//    public static int[] CalcRotate(LeastSquareMethod leastSquareMethod) {
//        double[] coefficient = leastSquareMethod.getCoefficient();

//    }
}
