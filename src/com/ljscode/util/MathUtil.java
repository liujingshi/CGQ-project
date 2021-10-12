package com.ljscode.util;

import com.ljscode.data.DataModel;
import com.ljscode.data.ItemModel;
import com.ljscode.data.LeastSquareMethod;
import com.ljscode.data.ResultModel;
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
    public static final int rc = 50;

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
        
        List<double[]> points = GetPoints2(realData);
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
        
        List<double[]> points = GetPoints2(realData);
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(points, 0);
        double minBeat = 999999999;
        double maxBeat = -99999999;
        for (Map.Entry<Integer, Double> entry : realData1.entrySet()) {
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

    public static double calcAngleBeat(HashMap<Integer, Double> realData, Map<Integer, Double> realData1, boolean me) {
        
        List<double[]> points = GetPoints2(realData);
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(points, 0);
        List<double[]> points1 = GetPoints2((HashMap<Integer, Double>) realData1);
        LeastSquareMethod leastSquareMethod1 = new LeastSquareMethod(points1, 0);
        double beat = Math.sqrt(Math.pow(leastSquareMethod1.angleB - leastSquareMethod.angleB, 2) + Math.pow(leastSquareMethod1.angleA - leastSquareMethod.angleA, 2));
        return beat * 2;
    }

    public static double calcPlantBeat(HashMap<Integer, Double> realData) {
        
        List<double[]> points = GetPoints3(realData);
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
        
        List<double[]> points = GetPoints3(realData);
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(points, true);
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
        
        List<double[]> points = GetPoints3(realData);
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(points, true);
        List<double[]> points1 = GetPoints3((HashMap<Integer, Double>) realData1);
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
        return resultMax - resultMin;
//        return result;
    }
    
    public static double[] CalcMinMax(ResultModel data, ItemModel itemModel) {
        ItemModel oneData = BeanUtil.GetLevel1ItemData(data);
        if (oneData != null) {
                List<double[]> pointsCylinder = GetPoints2(oneData.getRealDataCylinder());
                List<double[]> pointsEndFace = GetPoints3(oneData.getRealDataEndFace());
                LeastSquareMethod angle = new LeastSquareMethod(pointsCylinder, 0);
                LeastSquareMethod plant = new LeastSquareMethod(pointsEndFace, true);
                List<double[]> itemPointsCylinder = new ArrayList<>();
                List<double[]> itemPointsEndFace = new ArrayList<>();
                for (Map.Entry<Integer, Double> entry : itemModel.getRealDataCylinder().entrySet()) {
                    double[] myXY = GetXY(entry.getKey(), entry.getValue());
                    itemPointsCylinder.add(new double[] {myXY[0] - angle.angleA, myXY[1] - angle.angleB});
                }
                for (Map.Entry<Integer, Double> entry : itemModel.getRealDataEndFace().entrySet()) {
                    double[] myXY = GetXY(entry.getKey());
                    double oneZ = GetZ(plant, entry.getKey());
                    itemPointsEndFace.add(new double[] {myXY[0] - angle.angleA, myXY[1] - angle.angleB, entry.getValue() - oneZ});
                }
                LeastSquareMethod angleItem = new LeastSquareMethod(itemPointsCylinder, 0);
                LeastSquareMethod plantItem = new LeastSquareMethod(itemPointsEndFace, true);
                double minBeatCylinder = 999999999;
                double maxBeatCylinder = -99999999;
                for (double[] myXY : itemPointsCylinder) {
                    double dr = Math.sqrt(myXY[0]*myXY[0] + myXY[1]*myXY[1]);
                    double ai = Math.atan2(myXY[1], myXY[0]);
                    double beat = dr-angleItem.angleR-angleItem.angleA*Math.cos(ai)-angleItem.angleB*Math.sin(ai);
                    if (beat > maxBeatCylinder) {
                        maxBeatCylinder = beat;
                    }
                    if (beat < minBeatCylinder) {
                        minBeatCylinder = beat;
                    }
                }
                double minBeatEndFace = 999999999;
                double maxBeatEndFace = -99999999;
                for (double[] myXYZ : itemPointsEndFace) {
                    double ai = Math.atan2(myXYZ[1], myXYZ[0]);
                    double beat = myXYZ[2]-plantItem.planeA*rc*Math.cos(ai)-plantItem.planeB*rc*Math.sin(ai)-plantItem.planeD;
                    if (beat > maxBeatEndFace) {
                        maxBeatEndFace = beat;
                    }
                    if (beat < minBeatEndFace) {
                        minBeatEndFace = beat;
                    }
                }
                return new double[] { minBeatCylinder, maxBeatCylinder, minBeatEndFace, maxBeatEndFace };
            }
        return null;
    }

    public static List<double[]> GetPoints2(HashMap<Integer, Double> realData) {
        List<double[]> points = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            points.add(new double[] { (rc+entry.getValue())*Math.cos(Math.PI*entry.getKey()/2048d), (rc+entry.getValue())*Math.sin(Math.PI*entry.getKey()/2048d) });
        }
        return points;
    }

    public static List<double[]> GetPoints3(HashMap<Integer, Double> realData) {
        List<double[]> points = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : realData.entrySet()) {
            points.add(new double[] { rc*Math.cos(Math.PI*entry.getKey()/2048d), rc*Math.sin(Math.PI*entry.getKey()/2048d), entry.getValue() });
        }
        return points;
    }

    public static double GetZ(LeastSquareMethod plant, double x, double y) {
        // Z = Ax + By + D
        return plant.planeA*x + plant.planeB*y + plant.planeD;
    }

    public static double GetZ(LeastSquareMethod plant, double deg) {
        double x = rc*Math.cos(Math.PI*deg/2048d);
        double y = rc*Math.sin(Math.PI*deg/2048d);
        return GetZ(plant, x, y);
    }

    public static double[] GetXY(LeastSquareMethod angle, double deg) {
        double x = angle.angleR*Math.cos(Math.PI*deg/2048d);
        double y = angle.angleR*Math.sin(Math.PI*deg/2048d);
        return new double[] {x, y};
    }

    public static double[] GetXY(double deg, double o) {
        double x = (rc+o)*Math.cos(Math.PI*deg/2048d);
        double y = (rc+o)*Math.sin(Math.PI*deg/2048d);
        return new double[] {x, y};
    }

    public static double[] GetXY(double deg) {
        double x = rc*Math.cos(Math.PI*deg/2048d);
        double y = rc*Math.sin(Math.PI*deg/2048d);
        return new double[] {x, y};
    }

//    public static int[] CalcRotate(LeastSquareMethod leastSquareMethod) {
//        double[] coefficient = leastSquareMethod.getCoefficient();

//    }
}
