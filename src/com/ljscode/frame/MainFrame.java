package com.ljscode.frame;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseFrame;
import com.ljscode.frame.btn.CloseBtn;
import com.ljscode.component.Div;
import com.ljscode.component.Img;
import com.ljscode.component.TextLabel;
import com.ljscode.frame.tab.Tab;

/**
 * 主窗口
 */
public class MainFrame extends BaseFrame {

    /**
     * 构造方法
     */
    public MainFrame() {
        super(BaseConfig.Title, BaseConfig.FrameWidth, BaseConfig.FrameHeight);
        setAsMainFrame();  // 设为主窗口
        setFrameMiddle();  // 设置窗体居中
        this.setUndecorated(true); // 设置没有标题栏
        this.setLayout(null); // 绝对定位
        setContent();  // 设置内容
    }


    /**
     * 设置内容
     */
    private void setContent() {
        Div body = new Div(0, 0, BaseConfig.FrameWidth, BaseConfig.FrameHeight, BaseColor.Write, BaseColor.Blue);
        this.add(body);

        // 顶部标题框
        Div titleBox = new Div(0, 0, BaseConfig.FrameWidth, BaseConfig.ScaleHeight(0.104), BaseColor.Blue);
        body.add(titleBox);
        // logo
        Img logo = new Img(10, 10, 60, 60, "res/img/icon/logo.png");
        titleBox.add(logo);
        // 关闭按钮
        CloseBtn closeBtn = new CloseBtn(this);
        titleBox.add(closeBtn);
        // 标题文字
        TextLabel title = new TextLabel(90, 20, BaseConfig.Title, 33, BaseColor.Write);
        titleBox.add(title);

        // tab
        Tab tab = new Tab();
        body.add(tab);
    }

}
