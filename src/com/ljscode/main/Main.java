package com.ljscode.main;

import com.ljscode.base.BaseReadUSBData;
import com.ljscode.base.BaseUSBListener;
import com.ljscode.frame.MainFrame;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

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
//        BaseUSBListener.AnalogReceivedData(); // 模拟读取USB数据
        BaseUSBListener.LinkBPX(); // 连接盒子
//        MainFrame mainFrame = new MainFrame(); // 创建主窗体对象
//        mainFrame.showMe(); // 显示主窗体
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200);
                    BaseUSBListener.ReadUSBData((deg, cylinder, endFace) -> {
                        System.out.printf("%f, %f, %f%n", deg, cylinder, endFace);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
