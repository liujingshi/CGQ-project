package com.ljscode.component;

import com.ljscode.base.BaseColor;

import java.awt.*;

public class DataLabel extends TextLabel {

    private String name;
    private String unit;
    private float data;
    private float fix;
    private float fontSize;

    public DataLabel(int left, int top, float fontSize, String name, float data, float fix, String unit) {
        super("", fontSize, BaseColor.Black);
        this.name = name;
        this.unit = unit;
        this.data = data;
        this.fontSize = fontSize;
        this.fix = fix;
        this.setLocation(left, top);
        setDisplayText();
    }

    public DataLabel(int left, int top, float fontSize, String name, float data, float fix, String unit, Color color) {
        this(left, top, fontSize, name, data, fix, unit);
        this.setForeground(color);
    }

    public void setDisplayText() {
        String displayText = String.format("%s：%.2f%s", this.name, this.data, this.unit);
        int width = (int)((displayText.length() + 2) *  fontSize);
        int height = (int)fontSize;
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

    public void setData(float data) {
        this.data = data;
        setDisplayText();
    }

    public void setFix(float fix) {
        this.fix = fix;
        setDisplayText();
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
        setDisplayText();
    }
}
