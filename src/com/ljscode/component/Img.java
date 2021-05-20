package com.ljscode.component;

import javax.swing.*;
import java.awt.*;

/**
 * 图片
 */
public class Img extends JLabel {

    /**
     * 默认构造方法
     */
    public Img() {
        super();
    }

    /**
     * 构造方法
     *
     * @param src 图片路径
     */
    public Img(String src) {
        super(new ImageIcon(src));
    }

    /**
     * 构造方法
     *
     * @param left   距左
     * @param top    距顶
     * @param width  宽度
     * @param height 高度
     * @param src    图片路径
     */
    public Img(int left, int top, int width, int height, String src) {
        super();
        Image image = Toolkit.getDefaultToolkit().getImage(src).getScaledInstance(width, height, Image.SCALE_DEFAULT); // 加载图片
        Icon icon = new ImageIcon(image); // 转成icon
        this.setIcon(icon); // 设置icon
        this.setBounds(left, top, width, height); // 设置位置和宽高
    }

}
