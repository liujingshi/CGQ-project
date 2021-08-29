package com.ljscode.base;

import com.ljscode.bean.PortConfig;
import com.ljscode.bean.UsbConfig;
import com.ljscode.util.ConfigUtil;
import com.ljscode.util.ParseSystemUtil;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseUSBReader {

    private static final byte[] PLCOrder =  new byte[]{(byte)0x01, (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x04, (byte)0x44, (byte)0x09};
    private static final String[] BPXLinkOrder = new String[] { "h\r", "s,0\r", "c\r" };
    private static final String BPXOrder = "r,0,0x02,0x28,0xFF\r";
    private static final String BPXOrders = "r,0x2000,5,0x24,0xFF,0xFF,0x00,0x05\r";

    private static double degData;
    private static double cylinderData;
    private static double endFaceData;

    private static SerialPort portBPX = null;
    private static SerialPort portPLC = null;

    public static void ReadUSBData(BaseReadUSBData event) {
//        if (BaseUSBReader.portBPX != null && BaseUSBReader.portPLC != null && BaseUSBReader.degData != 0) {
            event.ReadUSBData(BaseUSBReader.degData, BaseUSBReader.cylinderData, BaseUSBReader.endFaceData);
//        }
    }

    public static boolean Link() {
        UsbConfig usbConfig = ConfigUtil.GetUsbConfig();
        String plcPortStr = "";
        String bpxPortStr = "";
        switch (usbConfig.getDeg()) {
            case "":
                break;
            case "USB1":
                plcPortStr = "COM1";
                break;
            case "USB2":
                plcPortStr = "COM2";
                break;
            case "USB3":
                plcPortStr = "COM3";
                break;
            case "USB4":
                plcPortStr = "COM4";
                break;
        }
        switch (usbConfig.getBpx()) {
            case "":
                break;
            case "USB1":
                bpxPortStr = "COM1";
                break;
            case "USB2":
                bpxPortStr = "COM2";
                break;
            case "USB3":
                bpxPortStr = "COM3";
                break;
            case "USB4":
                bpxPortStr = "COM4";
                break;
        }
        if (plcPortStr.equals("") || bpxPortStr.equals("")) {
            return false;
        }
        List<String> mCommList = BaseUSBListener.findPorts();
        if (mCommList.size() < 2) {
            System.out.println("没有搜索到有效串口！");
            return false;
        } else {
            try {
                BaseUSBReader.portPLC  = BaseUSBListener.openPort(plcPortStr, 9600);
                BaseUSBReader.portBPX  = BaseUSBListener.openPort(bpxPortStr, 9600);
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
        return true;
    }

    public static void StartRead() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
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
                int i = 0;
                for (byte d : data1) {
                    rData.append(String.format("%02x ", d));
                    i++;
                    if (i >= 13) {
                        break;
                    }
                }
                AnalyzePLCData(rData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void AnalyzeBPXData(StringBuffer data) {
        UsbConfig usbConfig = ConfigUtil.GetUsbConfig();
        String[] arr = data.toString().split("\r\n");
        boolean hasR = false;
        double deg = 0;
        double cylinder = 0;
        double endFace = 0;
        Map<String, String> usbPortMapping = new HashMap<>();
        usbPortMapping.put("A", "0x00");
        usbPortMapping.put("B", "0x01");
        usbPortMapping.put("C", "0x02");
        usbPortMapping.put("D", "0x03");
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
                String cylinderPortStr = usbPortMapping.get(usbConfig.getCylinder());
                String endFacePortStr = usbPortMapping.get(usbConfig.getEndFace());
                if (portStr.equals(cylinderPortStr)) {
                    cylinder = data10x * 1000;
                }
                if (portStr.equals(endFacePortStr)) {
                    endFace = data10x * 1000;
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
        if (nArr[0].equals("01") && nArr[1].equals("03") && nArr[2].equals("08")) {
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
    }

    public static boolean Link(boolean adc) {
        if (adc) {
            float cylinder1 = 300;
            float endFace1 = 300;
            new Thread(() -> {
                while (true) {
                    for (int i = 0; i <= 4096; i++) {
                        try {
                            Thread.sleep(5);
                            degData = (double)i * 360d / 4096d;
                            double data1 = (cylinder1 * Math.sin(Math.toRadians(degData) + (Math.random() * cylinder1 / 800 - cylinder1 / 1600)));
                            double data2 = (endFace1 * Math.sin(Math.toRadians(degData) + (Math.random() * endFace1 / 800 - endFace1 / 1600)));
                            cylinderData = data1;
                            endFaceData = data2;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            return true;
        } else {
            return Link();
        }
    }
}
