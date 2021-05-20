package com.ljscode.component;

import com.ljscode.base.BaseColor;

/**
 * 输入框组(带标签的)
 */
public class InputGroup extends Div {

    private final TextLabel textLabel;
    private final Input input;

    /**
     * 构造方法
     *
     * @param left   距左
     * @param top    距顶
     * @param width  宽度
     * @param height 高度
     * @param label  标签文本
     */
    public InputGroup(int left, int top, int width, int height, String label) {
        super(left, top, width, height, BaseColor.Write);
        this.textLabel = new TextLabel(0, 4, label, height - 8, BaseColor.Black);
        this.add(this.textLabel);
        int labelWidth = this.textLabel.getWidth();
        this.input = new Input(labelWidth, 0, width - labelWidth, height);
        this.add(this.input);
    }

    /**
     * 构造方法
     *
     * @param left        距左
     * @param top         距顶
     * @param width       宽度
     * @param height      高度
     * @param label       标签文本
     * @param placeholder 默认提示文字
     */
    public InputGroup(int left, int top, int width, int height, String label, String placeholder) {
        super(left, top, width, height, BaseColor.Write);
        this.textLabel = new TextLabel(0, 4, label, height - 8, BaseColor.Black);
        this.add(this.textLabel);
        int labelWidth = this.textLabel.getWidth();
        this.input = new Input(labelWidth, 0, width - labelWidth, height, placeholder);
        this.add(this.input);
    }

    /**
     * 获取文本框的值
     *
     * @return 文本框的值
     */
    public String getValue() {
        return this.input.getValue();
    }

    /**
     * 设置文本框的值
     *
     * @param value 值
     */
    public void setValue(String value) {
        this.input.setText(value);
    }

    /**
     * 设置标签的文本
     *
     * @param text 文本
     */
    public void setLabel(String text) {
        this.textLabel.setText(text);
    }
}
