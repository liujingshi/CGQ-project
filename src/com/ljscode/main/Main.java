package com.ljscode.main;

import com.ljscode.base.BaseUSBListener;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

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
//        MainFrame mainFrame = new MainFrame(); // 创建主窗体对象
//        mainFrame.showMe(); // 显示主窗体
//        BaseUSBListener.TestRead();
        List<String> mCommList = BaseUSBListener.findPorts();
        // 检查是否有可用串口，有则加入选项中
        if (mCommList.size() < 1) {
            System.out.println("没有搜索到有效串口！");
        } else {
// 待发送数据
            String data = "v\r";
            try {
                System.out.println(mCommList.get(0));
                SerialPort port = BaseUSBListener.openPort(mCommList.get(0), 9600);
                if (port != null) {
                    BaseUSBListener.addListener(port, () -> {
                        byte[] data1 = null;
                        try {
                            data1 = BaseUSBListener.readFromPort(port);
                            for (byte d : data1) {
                                System.out.print((char) Byte.toUnsignedInt(d));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    BaseUSBListener.sendToPort(port, data.getBytes());
                }
            } catch (PortInUseException e) {
                e.printStackTrace();
            }

        }


    }
}
