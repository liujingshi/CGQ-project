package com.ljscode.base;

import javax.swing.*;

/**
 * 窗体基类
 */
public class BaseFrame extends JFrame {

    /**
     * 是否为主窗体
     */
    private boolean isMainFrame = false;

    /**
     * 默认构造方法
     */
    public BaseFrame() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置右上角关闭为销毁窗体
    }

    /**
     * 构造方法
     *
     * @param title  窗体标题
     * @param width  窗体宽度
     * @param height 窗体高度
     */
    public BaseFrame(String title, int width, int height) {
        super(title);
        this.setSize(width, height); // 设置窗体尺寸
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置右上角关闭为销毁窗体
    }

    /**
     * 设置该窗体为主窗体，只可以有一个，主窗体退出程序即退出
     */
    public void setAsMainFrame() {
        this.isMainFrame = true;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置右上角关闭为直接关闭应用程序
    }

    /**
     * 显示窗体
     */
    public void showMe() {
        setVisible(true);
    }

    /**
     * 隐藏窗体
     */
    public void hideMe() {
        setVisible(false);
    }

    /**
     * 关闭窗体
     */
    public void close() {
        if (this.isMainFrame) {  // 主窗体退出程序
            System.exit(0);
        } else {  // 不是主窗体只进行销毁
            dispose();
        }
    }

    /**
     * 设置窗体居中
     */
    public void setFrameMiddle() {
        setLocationRelativeTo(null);
    }

}
