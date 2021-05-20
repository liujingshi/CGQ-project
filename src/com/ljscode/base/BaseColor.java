package com.ljscode.base;

import java.awt.*;

/**
 * 颜色类
 */
public class BaseColor {

    /**
     * 蓝色
     */
    public static Color Blue = color(new int[]{24, 144, 255}); // 蓝色
    /**
     * 深蓝色
     */
    public static Color HoverBlue = color(new int[]{0, 80, 179}); // Hover蓝色
    /**
     * 绿色
     */
    public static Color Green = color(new int[]{82, 196, 26}); // 绿色
    /**
     * 深绿色
     */
    public static Color HoverGreen = color(new int[]{35, 120, 4}); // Hover绿色
    /**
     * 黄色
     */
    public static Color Yellow = color(new int[]{255, 223, 37}); // 黄色
    /**
     * 深黄色
     */
    public static Color HoverYellow = color(new int[]{209, 152, 46}); // Hover黄色
    /**
     * 红色
     */
    public static Color Red = color(new int[]{217, 0, 27}); // 红色
    /**
     * 深红色
     */
    public static Color HoverRed = color(new int[]{217, 0, 27}); // Hover红色
    /**
     * 白色
     */
    public static Color Write = color(new int[]{255, 255, 255}); // 白色
    /**
     * 黑色
     */
    public static Color Black = color(new int[]{0, 0, 0}); // 黑色
    /**
     * 灰色
     */
    public static Color Gray = color(new int[]{239, 239, 239}); // 灰色
    /**
     * 输入框文字提示灰色
     */
    public static Color PlaceholderGray = color(new int[]{204, 204, 204}); // 输入框文字提示灰色

    /**
     * 通过数组创建颜色rgba
     *
     * @param color 颜色值数组 rgb | rgba
     * @return Color对象
     */
    public static Color color(int[] color) {
        if (color.length == 3) // rgb
            return new Color(color[0], color[1], color[2]);
        else if (color.length == 4) // rgba
            return new Color(color[0], color[1], color[2], color[3]);
        else // 其它不支持
            return null;
    }

}
