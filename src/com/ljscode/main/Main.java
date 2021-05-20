package com.ljscode.main;

import com.ljscode.base.BaseUSBListener;
import com.ljscode.frame.MainFrame;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;

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
//        MainFrame mainFrame = new MainFrame(); // 创建主窗体对象
//        mainFrame.showMe(); // 显示主窗体
        BaseUSBListener.TestRead();
//        SerialPort port = BaseUSBListener.openComPort("COM4", 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//        try {
//            InputStream inputStream = port.getInputStream();
//            byte[] result = BaseUSBListener.readFromPort(inputStream);
//            int length = result.length;
//            System.out.println(length);
//            for (byte b : result) {
//                System.out.print(Byte.toUnsignedInt(b));
//            }
//            System.out.println();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
