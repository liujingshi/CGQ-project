package com.ljscode.base;

import java.awt.*;

/**
 * 配置
 */
public class BaseConfig {

    /**
     * BPX
     */
    public static final String BPX = "BPX";
    /**
     * PLC
     */
    public static final String PLC = "PLC";
    /**
     * 柱面
     */
    public static final String Cylinder = "Cylinder";
    /**
     * 端面
     */
    public static final String EndFace = "EndFace";
    /**
     * 角度
     */
    public static final String Deg = "Deg";
    /**
     * 标题
     */
    public static String Title = "低压涡轮转静子装配工艺装备测试系统"; // 标题
    /**
     * 窗体宽度
     */
    public static int FrameWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(); // 窗体宽度
    /**
     * 窗体高度
     */
    public static int FrameHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(); // 窗体高度
    /**
     * tab按钮宽度
     */
    public static int TabBtnWidth = 100; // tab按钮宽度
    /**
     * tab按钮高度
     */
    public static int TabBtnHeight = 100; // tab按钮高度
    /**
     * tab按钮icon宽度
     */
    public static int TabBtnIconWidth = 50; // tab按钮icon宽度
    /**
     * tab按钮icon高度
     */
    public static int TabBtnIconHeight = 50; // tab按钮icon高度
    /**
     * tab按钮内边距
     */
    public static int[] TabBtnPadding = new int[]{20, 25, 0, 25}; // tab按钮内边距
    /**
     * tab按钮字体大小
     */
    public static float TabBtnFontSize = 14; // tab按钮字体大小
    /**
     * tab按钮正常字体颜色
     */
    public static Color TabBtnColor = BaseColor.Blue; // tab按钮正常字体颜色
    /**
     * tab按钮hover字体颜色
     */
    public static Color TabBtnHoverColor = BaseColor.Write; // tab按钮hover字体颜色
    /**
     * tab按钮正常背景颜色
     */
    public static Color TabBtnBackground = BaseColor.Write; // tab按钮正常背景颜色
    /**
     * tab按钮hover背景颜色
     */
    public static Color TabBtnHoverBackground = BaseColor.Blue; // tab按钮hover背景颜色

    /**
     * 文本框的高度
     */
    public static int InputHeight = 40; // 文本框的高度
    /**
     * 文本框组的宽度
     */
    public static int InputGroupWidth = 600; // 文本框组的宽度
    /**
     * 文本框组的宽度
     */
    public static int InputGroupWidthSm = 200; // 文本框组的宽度
    /**
     * 文本框组的宽度
     */
    public static int InputGroupWidthLg = 1000; // 文本框组的宽度
    /**
     * 文本框组间距(小)
     */
    public static int InputGroupSpaceSm = 32; // 文本框组间距
    /**
     * 文本框组间距(中)
     */
    public static int InputGroupSpaceMd = 40; // 文本框组间距
    /**
     * 文本框组间距(大)
     */
    public static int InputGroupSpaceLg = 48; // 文本框组间距

    /**
     * 根据窗体宽度进行计算
     *
     * @param scale 窗体宽度需要乘的值
     * @return 计算后的数据
     */
    public static int ScaleWidth(double scale) {
        return (int) (FrameWidth * scale);
    }

    /**
     * 根据窗体高度进行计算
     *
     * @param scale 窗体高度需要乘的值
     * @return 计算后的数据
     */
    public static int ScaleHeight(double scale) {
        return (int) (FrameHeight * scale);
    }
}
