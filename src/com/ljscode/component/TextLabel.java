package com.ljscode.component;

import com.ljscode.util.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 文本
 */
public class TextLabel extends JLabel {

    /**
     * 构造方法
     *
     * @param text     文本
     * @param fontSize 字体大小
     * @param color    文字颜色
     */
    public TextLabel(String text, float fontSize, Color color) {
        super(text);
        Font font = FontUtil.LoadFont(fontSize);
        this.setFont(font); // 设置字体
        this.setForeground(color); // 设置文本颜色
    }

    /**
     * 构造方法
     *
     * @param left     距左
     * @param top      距顶
     * @param width    宽度
     * @param height   高度
     * @param text     文本
     * @param fontSize 字体大小
     * @param color    文字颜色
     */
    public TextLabel(int left, int top, int width, int height, String text, float fontSize, Color color) {
        this(text, fontSize, color);
        this.setBounds(left, top, width, height);
    }

    /**
     * 构造方法
     *
     * @param left     距左
     * @param top      距顶
     * @param text     文本
     * @param fontSize 字体大小
     * @param color    文字颜色
     */
    public TextLabel(int left, int top, String text, float fontSize, Color color) {
        super(text);
        Font font = FontUtil.LoadFont(fontSize);
        this.setFont(font);
        this.setForeground(color);
        int length = text.length();
        int width = (int) ((fontSize + 3) * length);
        int height = (int) (fontSize + 1);
        this.setBounds(left, top, width, height);
    }

}
