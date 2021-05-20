package com.ljscode.component;

import com.ljscode.base.BaseColor;

import javax.swing.*;

/**
 * 旋钮提示框
 */
public class TipBox extends TextLabel {

    private String what;
    private boolean isRight;

    /**
     * 构造方法
     *
     * @param left   距左
     * @param top    距顶
     * @param width  宽度
     * @param height 高度
     */
    public TipBox(int left, int top, int width, int height) {
        super(left, top, width, height, "", 18, BaseColor.Blue);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, BaseColor.Blue));
    }

    /**
     * 获取旋转方向
     *
     * @return 旋转方向
     */
    public String getWhere() {
        if (this.isRight) {
            return "顺时针";
        } else {
            return "逆时针";
        }
    }

    /**
     * 设置展示文本
     */
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

    /**
     * 设置内容
     *
     * @param what    什么旋钮
     * @param isRight 是否顺时针旋转
     */
    public void setContent(String what, boolean isRight) {
        this.what = what;
        this.isRight = isRight;
        setDisplayText();
    }

}
