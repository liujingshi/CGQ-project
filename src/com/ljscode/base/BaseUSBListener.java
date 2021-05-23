package com.ljscode.base;

import com.ljscode.bean.PortConfig;
import com.ljscode.util.BaseArrayUtil;
import com.ljscode.util.ConfigUtil;
import com.ljscode.util.ParseSystemUtil;
import gnu.io.*;

import javax.usb.*;
import javax.usb.event.UsbPipeDataEvent;
import javax.usb.event.UsbPipeErrorEvent;
import javax.usb.event.UsbPipeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public abstract class BaseUSBListener {

    private static final short VENDOR_ID = 0x03eb;

    private static float cylinder1 = 5;
    private static float cylinder2 = 5;
    private static float endFace1 = 3;
    private static float endFace2 = 3;

    private static double degData;
    private static double cylinderData;
    private static double endFaceData;

    private static SerialPort port;
    private static BaseReadUSBData event;
    private static boolean Lock = false;

    private final double currentDeg = 0;

    public static UsbDevice findMissileLauncher(UsbHub hub) {
        UsbDevice launcher = null;

        for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices()) {
            if (device.isUsbHub()) {
                launcher = findMissileLauncher((UsbHub) device);
                if (launcher != null)
                    return launcher;
            } else {
                UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
                if (desc.idVendor() == VENDOR_ID) {
                    System.out.println("找到设备：" + device);
                    return device;
                }
            }
        }
        return null;
    }

    public static void TestRead() {
        try {
            UsbDevice device = findMissileLauncher(UsbHostManager.getUsbServices().getRootUsbHub());
            if (device != null) {
                UsbConfiguration configuration = device.getActiveUsbConfiguration();
                if (configuration.getUsbInterfaces().size() > 0) {
                    UsbInterface iFace = configuration.getUsbInterface((byte) 1);
                    if (iFace.isClaimed()) {
                        System.out.println("已经Claim，释放");
                        iFace.release();
                    }
                    System.out.println("开始Claim");
                    iFace.claim((usbInterface -> true));
                    UsbEndpoint receivedUsbEndpoint = iFace.getUsbEndpoint((byte) 0x83);
                    UsbEndpoint sendUsbEndpoint = iFace.getUsbEndpoint((byte) 0x04);

                    UsbPipe receivedUsbPipe = receivedUsbEndpoint.getUsbPipe();
                    receivedUsbPipe.open();
                    UsbPipe sendUsbPipe = sendUsbEndpoint.getUsbPipe();
                    sendUsbPipe.open();

                    receivedUsbPipe.addUsbPipeListener(new UsbPipeListener() {
                        @Override
                        public void errorEventOccurred(UsbPipeErrorEvent usbPipeErrorEvent) {
                            System.out.println(usbPipeErrorEvent);
                        }

                        @Override
                        public void dataEventOccurred(UsbPipeDataEvent usbPipeDataEvent) {
                            byte[] data = usbPipeDataEvent.getData();
                            int length = usbPipeDataEvent.getActualLength();
                            if (length > 0) {
                                System.out.println(length);
                                for (int i = 0; i < length; i++) {
                                    System.out.print(data[i]);
                                }
                                System.out.println();
                            } else {
                                System.out.print("NoData ");
                            }
                        }
                    });

                    new Thread(() -> {
                        while (true) {
                            try {
                                Thread.sleep(200);
                                byte[] bytesToRead = new byte[1];
                                bytesToRead[0] = '?';
                                byte[] b = new byte[16];
                                sendUsbPipe.asyncSubmit(bytesToRead);
                                receivedUsbPipe.asyncSubmit(b);
                            } catch (InterruptedException | UsbException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            } else {
                System.out.println("未找到设备");
            }
        } catch (UsbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找所有可用端口
     *
     * @return 可用端口名称列表
     */
    public static final ArrayList<String> findPorts() {
        // 获得当前所有可用串口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<String>();
        // 将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }
        return portNameList;
    }

    /**
     * 打开串口
     *
     * @param portName 端口名称
     * @param baudrate 波特率
     * @return 串口对象
     * @throws PortInUseException 串口已被占用
     */
    public static final SerialPort openPort(String portName, int baudrate) throws PortInUseException {
        try {
            // 通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            // 打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(portName, 2000);
            // 判断是不是串口
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                try {
                    // 设置一下串口的波特率等参数
                    // 数据位：8
                    // 停止位：1
                    // 校验位：None
                    serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                } catch (UnsupportedCommOperationException e) {
                    e.printStackTrace();
                }
                return serialPort;
            }
        } catch (NoSuchPortException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭串口
     *
     * @param serialPort 待关闭的串口对象
     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
        }
    }

    /**
     * 往串口发送数据
     *
     * @param serialPort 串口对象
     * @param order      待发送数据
     */
    public static void sendToPort(SerialPort serialPort, byte[] order) {
        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
            System.out.println("getOutput");
            out.write(order);
            System.out.println("write");
            out.flush();
            System.out.println("flush");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从串口读取数据
     *
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     */
    public static byte[] readFromPort(SerialPort serialPort) {
        InputStream in = null;
        byte[] bytes = {};
        try {
            in = serialPort.getInputStream();
            // 缓冲区大小为一个字节
            byte[] readBuffer = new byte[1];
            int bytesNum = in.read(readBuffer);
            while (bytesNum > 0) {
                bytes = BaseArrayUtil.concat(bytes, readBuffer);
                bytesNum = in.read(readBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * 添加监听器
     *
     * @param serialPort 串口对象
     * @param listener   串口存在有效数据监听
     */
    public static void addListener(SerialPort serialPort, DataAvailableListener listener) {
        try {
            // 给串口添加监听器
            serialPort.addEventListener(new SerialPortListener(listener));
            // 设置当有数据到达时唤醒监听接收线程
            serialPort.notifyOnDataAvailable(true);
            // 设置当通信中断时唤醒中断线程
            serialPort.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }

    public static void ReadUSBData(BaseReadUSBData event) {
//        if (Math.abs(degData - (int) degData) <= 0.1) {
//            event.ReadUSBData(-1, -1, -1);
//        } else {
//            event.ReadUSBData((int) degData, cylinderData, endFaceData);
//        }
        new Thread(() -> {
            while (Lock) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Lock = true;
            BaseUSBListener.event = event;
            String order = "r,0,0x02,0x28,0xFF\r";
            sendToPort(port, order.getBytes());
        }).start();
    }

    public static void AnalogReceivedData() {
        new Thread(() -> {
            while (true) {
                for (double i = 0; i <= 360; i += 0.05) {
                    try {
                        Thread.sleep(1);
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
    }

    public static void RotateCylinder(int num, boolean isRight) {
        switch (num) {
            case 1:
                cylinder1 = cylinder1 + (isRight ? 0.2F : -0.2F);
                break;
            case 2:
                cylinder2 = cylinder2 + (isRight ? 0.2F : -0.2F);
                break;
        }
    }

    public static void RotateEndFace(int num, boolean isRight) {
        switch (num) {
            case 1:
                endFace1 = endFace1 + (isRight ? 0.2F : -0.2F);
                break;
            case 2:
                endFace2 = endFace2 + (isRight ? 0.2F : -0.2F);
                break;
        }
    }

    public static void AnalyzeData(StringBuffer data) {
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
                                cylinder = data10x;
                                break;
                            case BaseConfig.EndFace:
                                endFace = data10x;
                                break;
                        }
                        break;
                    }
                }
            }
        }
        if (hasR && event != null)
            event.ReadUSBData(deg, cylinder, endFace);
    }

    public static void InitUSELister() {
        BaseUSBListener.addListener(port, () -> {
            try {
                byte[] data1 = BaseUSBListener.readFromPort(port);
                StringBuffer rData = new StringBuffer();
                for (byte d : data1) {
                    rData.append((char) Byte.toUnsignedInt(d));
                }
                AnalyzeData(rData);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Lock = false;
            }
        });
    }

    public static void LinkBPX() {
        List<String> mCommList = findPorts();
        System.out.println("findPort");
        System.out.println(mCommList.size());
        if (mCommList.size() < 1) {
            System.out.println("没有搜索到有效串口！");
        } else {
            List<String> orders = new ArrayList<>();
            orders.add("v\r");
            orders.add("s,0\r");
            orders.add("c\r");
            try {
                port = openPort(mCommList.get(0), 9600);
                System.out.println("opened");
                if (port != null) {
                    InitUSELister();
                    for (String order : orders) {
                        sendToPort(port, order.getBytes());
                        System.out.println("sended");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (PortInUseException e) {
                e.printStackTrace();
            }
        }
    }

    public UsbDevice findDevice(UsbHub hub, short vendorId, short productId) {
        for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices()) {
            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
            if (desc.idVendor() == vendorId && desc.idProduct() == productId) return device;
            if (device.isUsbHub()) {
                device = findDevice((UsbHub) device, vendorId, productId);
                if (device != null) return device;
            }
        }
        return null;
    }

    /**
     * 串口存在有效数据监听
     */
    public interface DataAvailableListener {
        /**
         * 串口存在有效数据
         */
        void dataAvailable();
    }

    /**
     * 串口监听
     */
    public static class SerialPortListener implements SerialPortEventListener {

        private final DataAvailableListener mDataAvailableListener;

        public SerialPortListener(DataAvailableListener mDataAvailableListener) {
            this.mDataAvailableListener = mDataAvailableListener;
        }

        public void serialEvent(SerialPortEvent serialPortEvent) {
            switch (serialPortEvent.getEventType()) {
                case SerialPortEvent.DATA_AVAILABLE: // 1.串口存在有效数据
                    if (mDataAvailableListener != null) {
                        mDataAvailableListener.dataAvailable();
                    }
                    break;

                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2.输出缓冲区已清空
                    break;

                case SerialPortEvent.CTS: // 3.清除待发送数据
                    break;

                case SerialPortEvent.DSR: // 4.待发送数据准备好了
                    break;

                case SerialPortEvent.RI: // 5.振铃指示
                    break;

                case SerialPortEvent.CD: // 6.载波检测
                    break;

                case SerialPortEvent.OE: // 7.溢位（溢出）错误
                    break;

                case SerialPortEvent.PE: // 8.奇偶校验错误
                    break;

                case SerialPortEvent.FE: // 9.帧错误
                    break;

                case SerialPortEvent.BI: // 10.通讯中断
                    System.out.println("与串口设备通讯中断");
                    break;

                default:
                    break;
            }
        }
    }
}
