package com.ljscode.component;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 按钮
 */
public class Btn extends TextLabel {

    /**
     * 蓝色
     */
    public static final String BLUE = "Blue";
    /**
     * 绿色
     */
    public static final String GREEN = "Green";
    /**
     * 黄色
     */
    public static final String YELLOW = "YELLOW";
    /**
     * 红色
     */
    public static final String RED = "RED";

    /**
     * 字体大小
     */
    private static final float fontSize = 32L;

    private static final Color disabledColor = BaseColor.PlaceholderGray;
    private boolean disabled;
    /**
     * 颜色
     */
    private Color color;
    /**
     * Hover颜色
     */
    private Color hoverColor;

    /**
     * 构造方法
     *
     * @param left  距左
     * @param top   距顶
     * @param text  文本
     * @param color 颜色(只能使用Btn.预设颜色)
     */
    public Btn(int left, int top, int width, int height, String text, String color, BaseMouseListener<Btn> event) {
        super(text, fontSize, BaseColor.Write);
        this.disabled = false;
        if (width <= 0)
            width = (int) (text.length() * fontSize + 100);
        if (height <= 0)
            height = (int) (text.length() * fontSize + 10);
        this.setBounds(left, top, width, height);
        this.setHorizontalAlignment(JLabel.CENTER);
        switch (color) {
            case Btn.BLUE:
                this.color = BaseColor.Blue;
                this.hoverColor = BaseColor.HoverBlue;
            break;
            case Btn.GREEN:
                this.color = BaseColor.Green;
                this.hoverColor = BaseColor.HoverGreen;
                break;
            case Btn.YELLOW:
                this.color = BaseColor.Yellow;
                this.hoverColor = BaseColor.HoverYellow;
                break;
            case Btn.RED:
                this.color = BaseColor.Red;
                this.hoverColor = BaseColor.HoverRed;
                break;
        }
        this.setOpaque(true);
        this.setBackground(this.color);
        Btn that = this;
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (isUnDisabled())
                    that.setLocation(left, top + 3);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isUnDisabled()) {
                    that.setLocation(left, top);
                    event.mouseClicked(that);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (isUnDisabled()) {
                    that.setBackground(that.hoverColor);
                    that.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isUnDisabled())
                    that.setBackground(that.color);
                that.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    public boolean isUnDisabled() {
        return !disabled;
    }

    public void disabled() {
        this.disabled = true;
        this.setBackground(disabledColor);
    }

    public void unDisabled() {
        this.disabled = false;
        this.setBackground(this.color);
    }
}
