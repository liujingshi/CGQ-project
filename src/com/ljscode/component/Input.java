package com.ljscode.component;

import com.ljscode.base.BaseColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

/**
 * 输入框
 */
public class Input extends JTextField {

    private String placeholder;

    /**
     * 构造方法
     */
    public Input() {
        super();
    }

    /**
     * 构造方法
     * @param left 距左
     * @param top 距顶
     * @param width 宽度
     * @param height 高度
     */
    public Input(int left, int top, int width, int height) {
        this();
        Font font = null;
        try {
            File alibabaPuHUiTi = new File("res/font/Alibaba-PuHuiTi.ttf"); // 打开ttf字体文件(阿里巴巴普惠体)
            font = Font.createFont(Font.TRUETYPE_FONT, alibabaPuHUiTi); // 创建字体对象
            font = font.deriveFont((float)(height - 8)); // 设置字体大小
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        if (font == null) { // 如果字体加载出错则使用默认字体
            font = new Font("宋体", Font.PLAIN, height - 8);
        }
        this.placeholder = "";
        this.setFont(font);
        this.setBounds(left, top, width, height);
    }

    /**
     * 构造方法
     * @param left 距左
     * @param top 距顶
     * @param width 宽度
     * @param height 高度
     * @param placeholder 默认提示文字
     */
    public Input(int left, int top, int width, int height, String placeholder) {
        this(left, top, width, height);
        this.setText(placeholder);
        this.setForeground(BaseColor.PlaceholderGray);
        this.placeholder = placeholder;
        Input that = this;
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (that.getText().equals(placeholder)) {
                    that.setText("");
                    that.setForeground(BaseColor.Black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (that.getText().length() == 0) {
                    that.setText(placeholder);
                    that.setForeground(BaseColor.PlaceholderGray);
                }
            }
        });
    }

    /**
     * 获取输入框的值
     * @return 值
     */
    public String getValue() {
        String text = this.getText();
        if (text.equals(placeholder)) {
            return "";
        } else {
            return text;
        }
    }

    /**
     * 设置输入框的值
     * @param text 文本
     */
    public void setValue(String text) {
        this.setText(text);
    }

}
