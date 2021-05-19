package com.ljscode.component;

import javax.swing.*;
import java.awt.*;

/**
 * 面板
 */
public class Div extends JPanel {

    /**
     * 默认构造方法
     */
    public Div() {
        super();
        // 使用绝对定位
        this.setLayout(null);
    }

    /**
     * 构造方法
     * @param left 距左
     * @param top 距顶
     * @param width 宽度
     * @param height 高度
     */
    public Div(int left, int top, int width, int height) {
        this();
        // 设置面板的位置和长度
        this.setBounds(left, top, width, height);
    }

    /**
     * 构造方法
     * @param left 距左
     * @param top 距顶
     * @param width 宽度
     * @param background 背景颜色
     */
    public Div(int left, int top, int width, int height, Color background) {
        this(left, top, width, height);
        // 设置背景颜色
        this.setBackground(background);
    }

    /**
     * 构造方法
     * @param left 距左
     * @param top 距顶
     * @param width 宽度
     * @param background 背景颜色
     * @param border 边框颜色
     */
    public Div(int left, int top, int width, int height, Color background, Color border) {
        this(left, top, width, height, background);
        // 设置边框颜色
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, border));
    }

}
