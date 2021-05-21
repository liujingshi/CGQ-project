package com.ljscode.component;

import com.ljscode.base.BaseColor;

import java.awt.*;

/**
 * 数据标签
 */
public class DataLabel extends TextLabel {

    private String name;
    private String unit;
    private double data;
    private int fix;
    private float fontSize;

    /**
     * 构造方法
     *
     * @param left     距左
     * @param top      距顶
     * @param fontSize 字体大小
     * @param name     名称
     * @param data     数据
     * @param fix      保留小数位数
     * @param unit     单位
     */
    public DataLabel(int left, int top, float fontSize, String name, double data, int fix, String unit) {
        super("", fontSize, BaseColor.Black);
        this.name = name;
        this.unit = unit;
        this.data = data;
        this.fontSize = fontSize;
        this.fix = fix;
        this.setLocation(left, top);
        setDisplayText();
    }

    /**
     * 构造方法
     *
     * @param left     距左
     * @param top      距顶
     * @param fontSize 字体大小
     * @param name     名称
     * @param data     数据
     * @param fix      保留小数位数
     * @param unit     单位
     * @param color    颜色
     */
    public DataLabel(int left, int top, float fontSize, String name, double data, int fix, String unit, Color color) {
        this(left, top, fontSize, name, data, fix, unit);
        this.setForeground(color);
    }

    /**
     * 设置展示的文本
     */
    public void setDisplayText() {
        String formatStr = "%s：%." + this.fix + "f%s";
        String displayText = String.format(formatStr, this.name, this.data, this.unit);
        int width = (int) ((displayText.length() + 2) * fontSize);
        int height = (int) fontSize;
        this.setSize(width, height);
        this.setText(displayText);
    }

    public void setDataName(String name) {
        this.name = name;
        setDisplayText();
    }

    public void setUnit(String unit) {
        this.unit = unit;
        setDisplayText();
    }

    public void setData(double data) {
        this.data = data;
        setDisplayText();
    }

    public void setFix(int fix) {
        this.fix = fix;
        setDisplayText();
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
        setDisplayText();
    }
}
