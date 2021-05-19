package com.ljscode.component;

import com.ljscode.base.BaseColor;

import javax.swing.*;

public class TipBox extends TextLabel {

    private String what;
    private boolean isRight;

    public TipBox(int left, int top, int width, int height) {
        super(left, top, width, height, "", 18, BaseColor.Blue);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, BaseColor.Blue));
    }

    public String getWhere() {
        if (this.isRight) {
            return "顺时针";
        } else {
            return "逆时针";
        }
    }

    public void setDisplayText() {
        String displayText = String.format("请将%s%s旋转", this.what, getWhere());
        this.setText(displayText);
        if (this.isRight) {
            this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, BaseColor.HoverYellow));
            this.setForeground(BaseColor.HoverYellow);
        } else {
            this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, BaseColor.HoverRed));
            this.setForeground(BaseColor.HoverRed);
        }
    }

    public void setWhat(String what) {
        this.what = what;
        setDisplayText();
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
        setDisplayText();
    }

    public void setContent(String what, boolean isRight) {
        this.what = what;
        this.isRight = isRight;
        setDisplayText();
    }

}
