package com.ljscode.component;

import org.jfree.chart.renderer.category.IntervalBarRenderer;

import java.awt.*;

/**
 * 自定义BarRenderer
 */
public class BarCustomRender extends IntervalBarRenderer {

    private final Paint[] colors;

    /**
     * 构造方法
     */
    public BarCustomRender() {
        String[] colorValues = {"#1890ff", "#ffec3d", "#52c41a", "#f5222d"};
        colors = new Paint[colorValues.length];
        for (int i = 0; i < colorValues.length; i++) {
            colors[i] = Color.decode(colorValues[i]);
        }
    }

    /**
     * 每根柱子以初始化的颜色不断轮循
     *
     * @param i i
     * @param j j
     * @return Paint
     */
    public Paint getItemPaint(int i, int j) {
        return colors[j % colors.length];
    }

}
