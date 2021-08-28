package com.ljscode.base;

import com.ljscode.bean.PortConfig;
import com.ljscode.util.ConfigUtil;
import com.ljscode.util.ParseSystemUtil;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.util.List;

public abstract class BaseUSBReader {

    private static final byte[] PLCOrder =  new byte[]{(byte)0x01, (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x04, (byte)0x44, (byte)0x09};
    private static final String[] BPXLinkOrder = new String[] { "v\r", "s,0\r", "c\r" };
    private static final String BPXOrder = "r,0,0x02,0x28,0xFF\r";

    private static double degData;
    private static double cylinderData;
    private static double endFaceData;

    private static SerialPort portBPX = null;
    private static SerialPort portPLC = null;

    public static void ReadUSBData(BaseReadUSBData event) {
        if (BaseUSBReader.portBPX != null && BaseUSBReader.portPLC != null && BaseUSBReader.degData != 0) {
            event.ReadUSBData(BaseUSBReader.degData, BaseUSBReader.cylinderData, BaseUSBReader.endFaceData);
        }
    }

    public static void Link() {
        List<String> mCommList = BaseUSBListener.findPorts();
        if (mCommList.size() < 2) {
            System.out.println("没有搜索到有效串口！");
        } else {
            try {
                BaseUSBReader.portPLC  = BaseUSBListener.openPort("COM3", 9600);
                BaseUSBReader.portBPX  = BaseUSBListener.openPort("COM4", 9600);
                InitBPXListener();
                InitPLCListener();
                for (String order : BaseUSBReader.BPXLinkOrder) {
                    BaseUSBListener.sendToPort(BaseUSBReader.portBPX, order.getBytes());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                StartRead();
            } catch (PortInUseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void StartRead() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200);
                    BaseUSBListener.sendToPort(BaseUSBReader.portBPX, BaseUSBReader.BPXOrder.getBytes());
                    BaseUSBListener.sendToPort(BaseUSBReader.portPLC, BaseUSBReader.PLCOrder);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void InitBPXListener() {
        BaseUSBListener.addListener(BaseUSBReader.portBPX, () -> {
            try {
                byte[] data1 = BaseUSBListener.readFromPort(BaseUSBReader.portBPX);
                StringBuffer rData = new StringBuffer();
                for (byte d : data1) {
                    if (Byte.toUnsignedInt(d) != 0)
                        rData.append((char) Byte.toUnsignedInt(d));
                }
                AnalyzeBPXData(rData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void InitPLCListener() {
        BaseUSBListener.addListener(BaseUSBReader.portPLC, () -> {
            try {
                byte[] data1 = BaseUSBListener.readFromPort(BaseUSBReader.portPLC);
                StringBuffer rData = new StringBuffer();
                for (byte d : data1) {
                    rData.append(String.format("%02x ", d));
                }
                AnalyzePLCData(rData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void AnalyzeBPXData(StringBuffer data) {
        List<PortConfig> portConfigs = ConfigUtil.GetPortConfig();
        String[] arr = data.toString().split("\r\n");
        boolean hasR = false;
        double deg = 0;
        double cylinder = 0;
        double endFace = 0;
        for (String nStr : arr) {
            String[] nArr = nStr.split(",");
            if (nArr[0].equals("r")) {
                hasR = true;
                String portStr = nArr[4];
                String data0x = nArr[5].substring(2) + nArr[6].substring(2) + nArr[7].substring(2); // 补码16进制
                String data2x = ParseSystemUtil.hexString2binaryString(data0x); // 补码
                String data2xY = ""; // 源码
                boolean isNegative = false;
                if (data2x.charAt(0) == '1') {
                    StringBuilder tmp = new StringBuilder();
                    tmp.append("0");
                    for (int i = 1; i < data2x.length(); i++) {
                        tmp.append(data2x.charAt(i) == '1' ? '0' : '1');
                    }
                    data2xY = tmp.toString();
                    isNegative = true;
                } else {
                    data2xY = data2x;
                }
                int data10x1 = Integer.parseInt(data2xY.substring(0, 4), 2);
                double data10x2 = ParseSystemUtil.bin2DecXiao(data2xY.substring(4));
                double data10x = data10x1 + data10x2;
                data10x = isNegative ? 0 - data10x : data10x;
                for (PortConfig portConfig : portConfigs) {
                    if (portConfig.getPort().equals(portStr)) {
                        switch (portConfig.getDevice()) {
                            case BaseConfig.Deg:
                                deg = data10x;
                                break;
                            case BaseConfig.Cylinder:
                                cylinder = data10x * 1000;
                                break;
                            case BaseConfig.EndFace:
                                endFace = data10x * 1000;
                                break;
                        }
                        break;
                    }
                }
            }
        }
        if (hasR) {
            BaseUSBReader.cylinderData = cylinder;
            BaseUSBReader.endFaceData = endFace;
        }
    }

    public static void AnalyzePLCData(StringBuffer data) {
        String[] nArr = data.toString().split(" ");
        String data0x = nArr[3] + nArr[4] + nArr[5] + nArr[6];
        String data2x = ParseSystemUtil.hexString2binaryString(data0x); // 补码
        String data2xY = ""; // 源码
        boolean isNegative = false;
        if (data2x.charAt(0) == '1') {
            StringBuilder tmp = new StringBuilder();
            tmp.append("0");
            for (int i = 1; i < data2x.length(); i++) {
                tmp.append(data2x.charAt(i) == '1' ? '0' : '1');
            }
            data2xY = tmp.toString();
            isNegative = true;
        } else {
            data2xY = data2x;
        }
        int data10x1 = Integer.parseInt(data2xY, 2);
        double tDeg = (double)data10x1 * 360d / 4096d;
        if (data10x1 <= 4096 && data10x1 >= -0.96) {
            BaseUSBReader.degData = isNegative ? -tDeg : tDeg;
        }
    }

    public static void Link(boolean adc) {
        if (adc) {
            float cylinder1 = 5;
            float cylinder2 = 0;
            float endFace1 = 3;
            float endFace2 = 0;
            new Thread(() -> {
                while (true) {
                    for (double i = 0; i <= 360; i += 0.01) {
                        try {
                            Thread.sleep(200);
                            double data1 = (cylinder2 + cylinder1 * Math.sin(Math.toRadians(i) + (Math.random() * cylinder1 / 8 - cylinder1 / 16)));
                            double data2 = (endFace2 + endFace1 * Math.sin(Math.toRadians(i) + (Math.random() * endFace1 / 8 - endFace1 / 16)));
                            degData = i;
                            cylinderData = data1;
                            endFaceData = data2;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else {
            Link();
        }
    }
}
