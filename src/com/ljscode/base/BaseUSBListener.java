package com.ljscode.base;

import com.ljscode.bean.PortConfig;
import com.ljscode.data.LeastSquareMethod;
import com.ljscode.util.BaseArrayUtil;
import com.ljscode.util.ConfigUtil;
import com.ljscode.util.MathUtil;
import com.ljscode.util.ParseSystemUtil;
import gnu.io.*;

import javax.usb.*;
import javax.usb.event.UsbPipeDataEvent;
import javax.usb.event.UsbPipeErrorEvent;
import javax.usb.event.UsbPipeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.*;

public abstract class BaseUSBListener {

    private static final short VENDOR_ID = 0x03eb;

    private static float cylinder1 = 5;
    private static float cylinder2 = 0;
    private static float endFace1 = 3;
    private static float endFace2 = 0;

    private static double degData;
    private static double cylinderData;
    private static double endFaceData;

    private static SerialPort port;
    private static SerialPort portPLC;
    private static BaseReadUSBData event;
    private static boolean Lock = false;

    private final double currentDeg = 0;

    public static void TestNice() {
        List<Double> rawData = new ArrayList<>();
        for (int i = 0; i < 360; i++) {
            double data = cylinder2 + cylinder1 * Math.sin(Math.toRadians(i) + (Math.random() * cylinder1 / 8 - cylinder1 / 16));
            rawData.add(data);
        }
        LeastSquareMethod leastSquareMethod = MathUtil.GetLeastSquareMethod(rawData);
        for (double item : leastSquareMethod.getCoefficient()) {
            System.out.println(item * 100000);
        }
    }

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
                    System.out.println("???????????????" + device);
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
                        System.out.println("??????Claim?????????");
                        iFace.release();
                    }
                    System.out.println("??????Claim");
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
                System.out.println("???????????????");
            }
        } catch (UsbException e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????????????????
     *
     * @return ????????????????????????
     */
    public static final ArrayList<String> findPorts() {
        // ??????????????????????????????
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<String>();
        // ???????????????????????????List????????????List
        while (portList.hasMoreElements()) {
            CommPortIdentifier element = portList.nextElement();
            String portName = element.getName();
            portNameList.add(portName);
        }
        return portNameList;
    }

    /**
     * ????????????
     *
     * @param portName ????????????
     * @param baudrate ?????????
     * @return ????????????
     * @throws PortInUseException ??????????????????
     */
    public static final SerialPort openPort(String portName, int baudrate) throws PortInUseException {
        try {
            // ???????????????????????????
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            // ??????????????????????????????????????????timeout?????????????????????????????????
            CommPort commPort = portIdentifier.open(portName, 2000);
            // ?????????????????????
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                try {
                    // ???????????????????????????????????????
                    // ????????????8
                    // ????????????1
                    // ????????????None
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
     * ????????????
     *
     * @param serialPort ????????????????????????
     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
        }
    }

    /**
     * ?????????????????????
     *
     * @param serialPort ????????????
     * @param order      ???????????????
     */
    public static void sendToPort(SerialPort serialPort, byte[] order) {
        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();
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
     * ?????????????????????
     *
     * @param serialPort ????????????????????????SerialPort??????
     * @return ??????????????????
     */
    public static byte[] readFromPort(SerialPort serialPort) {
        InputStream in = null;
//        byte[] bytes = {};
        byte[] bytes = new byte[255];
        try {
            in = serialPort.getInputStream();
            // ??????????????????????????????
//            byte[] readBuffer = new byte[1];
//            int bytesNum = in.read(readBuffer);
            in.read(bytes);
//            while (bytesNum > 0) {
//                bytes = BaseArrayUtil.concat(bytes, readBuffer);
//                bytesNum = in.read(readBuffer);
//            }
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
     * ???????????????
     *
     * @param serialPort ????????????
     * @param listener   ??????????????????????????????
     */
    public static void addListener(SerialPort serialPort, DataAvailableListener listener) {
        try {
            // ????????????????????????
            serialPort.addEventListener(new SerialPortListener(listener));
            // ???????????????????????????????????????????????????
            serialPort.notifyOnDataAvailable(true);
            // ??????????????????????????????????????????
            serialPort.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }

    public static void ReadUSBData(BaseReadUSBData event) {
//        event.ReadUSBData(degData, cylinderData, endFaceData);
//        new Thread(() -> {
//            while (Lock) {
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            Lock = true;
//            BaseUSBListener.event = event;
//            String order = "r,0,0x02,0x28,0xFF\r";
//            sendToPort(port, order.getBytes());
//        }).start();
    }

    public static void AnalogReceivedData() {
        new Thread(() -> {
            while (true) {
                for (double i = 0; i <= 360; i += 0.01) {
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
                String data0x = nArr[5].substring(2) + nArr[6].substring(2) + nArr[7].substring(2); // ??????16??????
                String data2x = ParseSystemUtil.hexString2binaryString(data0x); // ??????
                String data2xY = ""; // ??????
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
        if (hasR && event != null) {}
//            event.ReadUSBData(deg, cylinder, endFace);
    }

    public static void InitUSELister() {
        BaseUSBListener.addListener(port, () -> {
            try {
                byte[] data1 = BaseUSBListener.readFromPort(port);
                StringBuffer rData = new StringBuffer();
                for (byte d : data1) {
                    if (Byte.toUnsignedInt(d) != 0)
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
        if (mCommList.size() < 1) {
            System.out.println("??????????????????????????????");
        } else {
            List<String> orders = new ArrayList<>();
            orders.add("v\r");
            orders.add("s,0\r");
            orders.add("c\r");
            try {
                port = openPort(mCommList.get(0), 9600);
                if (port != null) {
                    InitUSELister();
                    for (String order : orders) {
                        sendToPort(port, order.getBytes());
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

    public static void AnalyzePLCData(StringBuffer data) {
        String[] nArr = data.toString().split(" ");
        String data0x = nArr[3] + nArr[4] + nArr[5] + nArr[6];
        String data2x = ParseSystemUtil.hexString2binaryString(data0x); // ??????
        String data2xY = ""; // ??????
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
        if (data10x1 <= 4096 && data10x1 >= -0.96)
            System.out.println(data10x1);
        if (event != null) {}
//            event.ReadUSBData(deg, cylinder, endFace);
    }

    public static void InitPLCLister() {
        BaseUSBListener.addListener(portPLC, () -> {
            try {
                byte[] data1 = BaseUSBListener.readFromPort(portPLC);
                StringBuffer rData = new StringBuffer();
                for (byte d : data1) {
//                    if (Byte.toUnsignedInt(d) != 0)
                        rData.append(String.format("%02x ", d));
                }
                AnalyzePLCData(rData);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Lock = false;
            }
        });
    }

    public static void LinkPLC() {
        List<String> mCommList = findPorts();
        if (mCommList.size() < 1) {
            System.out.println("??????????????????????????????");
        } else {
            try {
                portPLC = openPort(mCommList.get(0), 9600);
//                String order = "0103000000044409";
                byte[] order = new byte[]{(byte)0x01, (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x04, (byte)0x44, (byte)0x09};
                if (portPLC != null) {
                    InitPLCLister();
                    new Thread(() -> {
                        while (true) {
                            try {
                                Thread.sleep(1000);
                                sendToPort(portPLC, order);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
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
     * ??????????????????????????????
     */
    public interface DataAvailableListener {
        /**
         * ????????????????????????
         */
        void dataAvailable();
    }

    /**
     * ????????????
     */
    public static class SerialPortListener implements SerialPortEventListener {

        private final DataAvailableListener mDataAvailableListener;

        public SerialPortListener(DataAvailableListener mDataAvailableListener) {
            this.mDataAvailableListener = mDataAvailableListener;
        }

        public void serialEvent(SerialPortEvent serialPortEvent) {
            switch (serialPortEvent.getEventType()) {
                case SerialPortEvent.DATA_AVAILABLE: // 1.????????????????????????
                    if (mDataAvailableListener != null) {
                        mDataAvailableListener.dataAvailable();
                    }
                    break;

                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2.????????????????????????
                    break;

                case SerialPortEvent.CTS: // 3.?????????????????????
                    break;

                case SerialPortEvent.DSR: // 4.???????????????????????????
                    break;

                case SerialPortEvent.RI: // 5.????????????
                    break;

                case SerialPortEvent.CD: // 6.????????????
                    break;

                case SerialPortEvent.OE: // 7.????????????????????????
                    break;

                case SerialPortEvent.PE: // 8.??????????????????
                    break;

                case SerialPortEvent.FE: // 9.?????????
                    break;

                case SerialPortEvent.BI: // 10.????????????
                    System.out.println("???????????????????????????");
                    break;

                default:
                    break;
            }
        }
    }
}
