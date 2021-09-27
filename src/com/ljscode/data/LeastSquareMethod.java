package com.ljscode.data;

import java.util.List;

/**
 * 最小二乘法
 */
public class LeastSquareMethod {

    private final double[] x;
    private final double[] y;
    private final double[] weight;
    private final int n;
    private double[] coefficient;

    public LeastSquareMethod(List<Double> coefficient) {
        n= 6;
        x = new double[] {};
        y = new double[] {};
        weight = new double[] {};
        this.coefficient = new double[6];
        for (int i = 0; i < coefficient.size() && i < 6; i++) {
            this.coefficient[i] = coefficient.get(i);
        }
    }

    /**
     * 构造方法
     *
     * @param x x数据
     * @param y y数据
     * @param n 阶数
     */
    public LeastSquareMethod(double[] x, double[] y, int n) {
        if (x == null || y == null || x.length < 2 || x.length != y.length || n < 2) {
            throw new IllegalArgumentException("IllegalArgumentException occurred.");
        }
        this.x = x;
        this.y = y;
        this.n = n;
        weight = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            weight[i] = 1;
        }
        compute();
    }

    /**
     * 构造方法
     *
     * @param x      x数据
     * @param y      y数据
     * @param weight 权重
     * @param n      阶数
     */
    public LeastSquareMethod(double[] x, double[] y, double[] weight, int n) {
        if (x == null || y == null || weight == null || x.length < 2 || x.length != y.length
                || x.length != weight.length || n < 2) {
            throw new IllegalArgumentException("IllegalArgumentException occurred.");
        }
        this.x = x;
        this.y = y;
        this.n = n;
        this.weight = weight;
        compute();
    }

    /**
     * 获取参数
     *
     * @return 参数
     */
    public double[] getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double[] coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * 根据x求y
     *
     * @param x x
     * @return y
     */
    public double fit(double x) {
        if (coefficient == null) {
            return 0;
        }
        double sum = 0;
        for (int i = 0; i < coefficient.length; i++) {
            sum += Math.pow(x, i) * coefficient[i];
        }
        return sum;
    }

    /**
     * 根据y求x
     *
     * @param y y
     * @return x
     */
    public double solve(double y) {
        return solve(y, 1.0d);
    }

    /**
     * 根据y求x
     *
     * @param y      y
     * @param startX 起始x
     * @return x
     */
    public double solve(double y, double startX) {
        final double EPS = 0.0000001d;
        if (coefficient == null) {
            return 0;
        }
        double x1 = 0.0d;
        double x2 = startX;
        do {
            x1 = x2;
            x2 = x1 - (fit(x1) - y) / calcReciprocal(x1);
        } while (Math.abs((x1 - x2)) > EPS);
        return x2;
    }

    private double calcReciprocal(double x) {
        if (coefficient == null) {
            return 0;
        }
        double sum = 0;
        for (int i = 1; i < coefficient.length; i++) {
            sum += i * Math.pow(x, i - 1) * coefficient[i];
        }
        return sum;
    }

    /**
     * 计算
     */
    private void compute() {
        if (x == null || y == null || x.length <= 1 || x.length != y.length || x.length < n
                || n < 2) {
            return;
        }
        double[] s = new double[(n - 1) * 2 + 1];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < x.length; j++) {
                s[i] += Math.pow(x[j], i) * weight[j];
            }
        }
        double[] b = new double[n];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < x.length; j++) {
                b[i] += Math.pow(x[j], i) * y[j] * weight[j];
            }
        }
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = s[i + j];
            }
        }

        coefficient = calcLinearEquation(a, b);
    }

    /**
     * 最小二乘法计算参数
     */
    private double[] calcLinearEquation(double[][] a, double[] b) {
        if (a == null || b == null || a.length == 0 || a.length != b.length) {
            return null;
        }
        for (double[] x : a) {
            if (x == null || x.length != a.length)
                return null;
        }

        int len = a.length - 1;
        double[] result = new double[a.length];

        if (len == 0) {
            result[0] = b[0] / a[0][0];
            return result;
        }

        double[][] aa = new double[len][len];
        double[] bb = new double[len];
        int posx = -1, posy = -1;
        for (int i = 0; i <= len; i++) {
            for (int j = 0; j <= len; j++)
                if (a[i][j] != 0.0d) {
                    posy = j;
                    break;
                }
            if (posy != -1) {
                posx = i;
                break;
            }
        }
        if (posx == -1) {
            return null;
        }

        int count = 0;
        for (int i = 0; i <= len; i++) {
            if (i == posx) {
                continue;
            }
            bb[count] = b[i] * a[posx][posy] - b[posx] * a[i][posy];
            int count2 = 0;
            for (int j = 0; j <= len; j++) {
                if (j == posy) {
                    continue;
                }
                aa[count][count2] = a[i][j] * a[posx][posy] - a[posx][j] * a[i][posy];
                count2++;
            }
            count++;
        }

        double[] result2 = calcLinearEquation(aa, bb);

        double sum = b[posx];
        count = 0;
        for (int i = 0; i <= len; i++) {
            if (i == posy) {
                continue;
            }
            sum -= a[posx][i] * result2[count];
            result[i] = result2[count];
            count++;
        }
        result[posy] = sum / a[posx][posy];
        return result;
    }

}
