package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.component.Div;

/**
 * Tab面板
 */
public class TabPanel extends Div {

    protected int width;
    protected int height;
    // 是否显示
    private boolean isShow = false;

    /**
     * 构造方法
     */
    public TabPanel() {
        super(0, BaseConfig.TabBtnHeight, BaseConfig.FrameWidth - 10,
                BaseConfig.FrameHeight - 81 - 9 - BaseConfig.TabBtnHeight, BaseColor.Write);
        this.hideMe();
        this.width = this.getWidth();
        this.height = this.getHeight();
    }

    /**
     * 显示自己
     */
    public void showMe() {
        this.setVisible(true);
        this.isShow = true;
    }

    /**
     * 隐藏自己
     */
    public void hideMe() {
        this.setVisible(false);
        this.isShow = false;
    }

    /**
     * 显示/隐藏自己
     */
    public void toggleMe() {
        this.isShow = !this.isShow;
        this.setVisible(this.isShow);
    }

}
