package com.ljscode.data;

import java.util.List;

/**
 * 最小二乘法
 */
public class LeastSquareMethod {

    private double[] x;
    private double[] y;
    private double[] weight;
    private int n;
    private double[] coefficient;
    public double angleA;
    public double angleB;
    public double angleR;
    public double planeA;
    public double planeB;
    public double planeC;
    public double planeD;

    public LeastSquareMethod(List<double[]> points, int angle) {
        angleA = 0d;
        angleB = 0d;
        angleR = 0d;

        double sum_x = 0d, sum_y = 0d;
        double sum_x2 = 0d, sum_y2 = 0d;
        double sum_x3 = 0d, sum_y3 = 0d;
        double sum_xy = 0d, sum_x1y2 = 0d, sum_x2y1 = 0d;

        int N = points.size();
        for (double[] point : points) {
            double x = point[0];
            double y = point[1];
            double x2 = x * x;
            double y2 = y * y;
            sum_x += x;
            sum_y += y;
            sum_x2 += x2;
            sum_y2 += y2;
            sum_x3 += x2 * x;
            sum_y3 += y2 * y;
            sum_xy += x * y;
            sum_x1y2 += x * y2;
            sum_x2y1 += x2 * y;
        }

        double C, D, E, G, H;
        double a, b, c;

        C = N * sum_x2 - sum_x * sum_x;
        D = N * sum_xy - sum_x * sum_y;
        E = N * sum_x3 + N * sum_x1y2 - (sum_x2 + sum_y2) * sum_x;
        G = N * sum_y2 - sum_y * sum_y;
        H = N * sum_x2y1 + N * sum_y3 - (sum_x2 + sum_y2) * sum_y;
        a = (H * D - E * G) / (C * G - D * D);
        b = (H * C - E * D) / (D * D - G * C);
        c = -(a * sum_x + b * sum_y + sum_x2 + sum_y2) / N;

        angleA = a / (-2);
        angleB = b / (-2);
        angleR = Math.sqrt(a * a + b * b - 4 * c) / 2;
    }

    public LeastSquareMethod(List<double[]> points, boolean plane) {
        double[] x = new double[points.size()];
        double[] y = new double[points.size()];
        double[] z = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            x[i] = points.get(i)[0];
            y[i] = points.get(i)[1];
            z[i] = points.get(i)[2];
        }
        int n = x.length;
        double[][] A = new double[n][3];
        double[][] E = new double[n][1];
        for (int i = 0; i < n; i++)
        {
            A[i][0] = x[i] - z[i];
            A[i][1] = y[i] - z[i];
            A[i][2] = 1;
            E[i][0] = -z[i];
        }
        double[][] AT = MatrixInver(A);
        double[][] ATxA = MatrixMultiply(AT, A);
        double[][] OPPAxTA = MatrixOpp(ATxA);
        double[][] OPPATAxAT = MatrixMultiply(OPPAxTA, AT);
        double[][] DP = MatrixMultiply(OPPATAxAT, E);
        planeA= DP[0][0];
        planeB = DP[1][0];
        planeC = 1 - planeA - planeB;
        planeD = DP[2][0];
    }

    /// <summary>
    /// 矩阵转置
    /// </summary>
    /// <param name="matrix"></param>
    /// <returns></returns>
    private static double[][] MatrixInver(double[][] matrix)
    {
        double[][] result = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix[0].length; i++)
            for (int j = 0; j < matrix.length; j++)
                result[i][j] = matrix[j][i];
        return result;
    }
    
    /// <summary>
    /// 矩阵相乘
    /// </summary>
    /// <param name="matrixA"></param>
    /// <param name="matrixB"></param>
    /// <returns></returns>
    private static double[][] MatrixMultiply(double[][] matrixA,double[][] matrixB)
    {
        double[][] result = new double[matrixA.length][matrixB[0].length];
        for (int i = 0; i < matrixA.length; i++)
        {
            for (int j = 0; j < matrixB[0].length; j++)
            {
                result[i][j] = 0;
                for (int k = 0; k < matrixB.length; k++)
                {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return result;
    }
    
    /// <summary>
    /// 矩阵的逆
    /// </summary>
    /// <param name="matrix"></param>
    /// <returns></returns>
    private static double[][] MatrixOpp(double[][]matrix)
    {
        double X = 1 / MatrixSurplus(matrix);
        double[][]matrixB = new double[matrix.length][matrix[0].length];
        double[][]matrixSP = new double[matrix.length][matrix[0].length];
        double[][]matrixAB = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
            {
                for (int m = 0; m < matrix.length; m++)
                    for (int n = 0; n < matrix[0].length; n++)
                        matrixB[m][n] = matrix[m][n];
                {
                    for (int x = 0; x < matrix[0].length; x++)
                        matrixB[i][x] = 0;
                    for (int y = 0; y < matrix.length; y++)
                        matrixB[y][j] = 0;
                    matrixB[i][j] = 1;
                    matrixSP[i][j] = MatrixSurplus(matrixB);
                    matrixAB[i][j] = X * matrixSP[i][j];
                }
            }
        return MatrixInver(matrixAB);
    }
    
    /// <summary>
    /// 矩阵的行列式的值  
    /// </summary>
    /// <param name="A"></param>
    /// <returns></returns>
    public static double MatrixSurplus(double[][] matrix)
    {
        double X = -1;
        double[][] a = matrix;
        int i, j, k, p, r, m, n;
        m = a.length;
        n = a[0].length;
        double temp = 1, temp1 = 1, s = 0, s1 = 0;

        if (n == 2)
        {
            for (i = 0; i < m; i++)
                for (j = 0; j < n; j++)
                    if ((i + j) % 2 > 0) temp1 *= a[i][j];
                        else temp *= a[i][j];
            X = temp - temp1;
        }
        else
        {
            for (k = 0; k < n; k++)
            {
                for (i = 0, j = k; i < m && j < n; i++, j++)
                    temp *= a[i][j];
                if (m - i > 0)
                {
                    for (p = m - i, r = m - 1; p > 0; p--, r--)
                        temp *= a[r][p - 1];
                }
                s += temp;
                temp = 1;
            }

            for (k = n - 1; k >= 0; k--)
            {
                for (i = 0, j = k; i < m && j >= 0; i++, j--)
                    temp1 *= a[i][j];
                if (m - i > 0)
                {
                    for (p = m - 1, r = i; r < m; p--, r++)
                        temp1 *= a[r][p];
                }
                s1 += temp1;
                temp1 = 1;
            }

            X = s - s1;
        }
        return X;
    }
    
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
