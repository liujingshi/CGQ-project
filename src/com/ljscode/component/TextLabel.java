package com.ljscode.component;

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
        Font font = loadFont(text, fontSize);
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
        Font font = loadFont(text, fontSize);
        this.setFont(font);
        this.setForeground(color);
        int length = text.length();
        int width = (int) ((fontSize + 3) * length);
        int height = (int) (fontSize + 1);
        this.setBounds(left, top, width, height);
    }

    /**
     * 加载字体
     *
     * @param text     文本
     * @param fontSize 字体大小
     * @return 字体Font对象
     */
    private Font loadFont(String text, float fontSize) {
        Font font = null;
        try {
            File alibabaPuHUiTi = new File("res/font/Alibaba-PuHuiTi.ttf"); // 打开ttf字体文件(阿里巴巴普惠体)
            font = Font.createFont(Font.TRUETYPE_FONT, alibabaPuHUiTi); // 创建字体对象
//            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            graphicsEnvironment.registerFont(font);
            font = font.deriveFont(fontSize); // 设置字体大小
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        if (font == null) { // 如果字体加载出错则使用默认字体
            font = new Font("宋体", Font.PLAIN, (int) fontSize);
        }
        return font;
    }

}
