package com.ljscode.main;

import com.ljscode.base.BaseUSBListener;
import com.ljscode.frame.MainFrame;

/**
 * 程序入口
 */
public class Main {

    /**
     * 程序入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        BaseUSBListener.LinkBPX(); // 模拟读取USB数据
        MainFrame mainFrame = new MainFrame(); // 创建主窗体对象
        mainFrame.showMe(); // 显示主窗体

    }
}
