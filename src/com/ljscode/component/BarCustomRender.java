package com.ljscode.component;

import org.jfree.chart.renderer.category.IntervalBarRenderer;

import java.awt.*;

public class BarCustomRender extends IntervalBarRenderer {

    private Paint[] colors;

    // 初始化柱子颜色
    private String[] colorValues = { "#1890ff", "#ffec3d", "#52c41a", "#f5222d" };

    public BarCustomRender() {
        colors = new Paint[colorValues.length];
        for (int i = 0; i < colorValues.length; i++) {
            colors[i] = Color.decode(colorValues[i]);
        }
    }

    // 每根柱子以初始化的颜色不断轮循
    public Paint getItemPaint(int i, int j) {
        return colors[j % colors.length];
    }

}
