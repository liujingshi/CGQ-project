package com.ljscode.base;

import gnu.io.*;

import javax.usb.*;
import javax.usb.event.UsbPipeDataEvent;
import javax.usb.event.UsbPipeErrorEvent;
import javax.usb.event.UsbPipeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class BaseUSBListener {

    private static final short VENDOR_ID = 0x08ee;
    //    private static final short PRODUCT_ID = 0xb100;
    private static UsbPipe pipe81, pipe01;

    private static double cylinderData;
    private static double endFaceData;
    private static double degData;
    private static float scale = 1;

    private String cylinderCOM;
    private String endFaceCOM;
    private String degCOM;

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
                    UsbEndpoint receivedUsbEndpoint = iFace.getUsbEndpoint((byte) 0x01);
                    //接收
                    UsbPipe receivedUsbPipe = receivedUsbEndpoint.getUsbPipe();
                    receivedUsbPipe.open();

                    receivedUsbPipe.addUsbPipeListener(new UsbPipeListener() {
                        @Override
                        public void errorEventOccurred(UsbPipeErrorEvent usbPipeErrorEvent) {
                            System.out.println(usbPipeErrorEvent);
                        }

                        @Override
                        public void dataEventOccurred(UsbPipeDataEvent usbPipeDataEvent) {
                            byte[] data = usbPipeDataEvent.getData();
                            int length = usbPipeDataEvent.getActualLength();
                            System.out.println(length);
                            for (int i = 0; i < length; i++) {
                                System.out.print(Byte.toUnsignedInt(data[i]));
                            }
                            System.out.println();
                        }
                    });

                    new Thread(() -> {
                        while (true) {
                            try {
                                Thread.sleep(2000);

                                byte[] bytesToRead = new byte[64]; //Going to read 3 bytes into here
                                UsbIrp irp = null;
                                try {
                                    irp = receivedUsbPipe.asyncSubmit(bytesToRead);
//                                    irp.waitUntilComplete(1000);
//                                    int length = irp.getActualLength();
//                                    System.out.println(length);
//                                    for (int i = 0; i < length; i++) {
//                                        System.out.print(Byte.toUnsignedInt(bytesToRead[i]));
//                                    }
//                                    System.out.println();
                                } catch (UsbException e) {
                                    e.printStackTrace();
                                }

//                                UsbIrp irpRead = receivedUsbPipe.createUsbIrp();
//                                irpRead.setData(bytesToRead);
//                                try {
//                                    receivedUsbPipe.asyncSubmit(irpRead); //Read some bytes
//                                    irpRead.waitUntilComplete(1000); //Wait up to 1 second
//                                    int length = irpRead.getLength();
//                                    System.out.println(length);
//                                } catch (UsbException e) {
//                                    e.printStackTrace();
//                                }
                            } catch (InterruptedException e) {
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
     * 查找电脑上所有可用 com 端口
     *
     * @return 可用端口名称列表，没有时 列表为空
     */
    public static ArrayList<String> findSystemAllComPort() {
        /**
         *  getPortIdentifiers：获得电脑主板当前所有可用串口
         */
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<>();

        /**
         *  将可用串口名添加到 List 列表
         */
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();//名称如 COM1、COM2....
            System.out.println(portName);
            portNameList.add(portName);
        }
        return portNameList;
    }

    /**
     * 打开电脑上指定的串口
     *
     * @param portName 端口名称，如 COM1，为 null 时，默认使用电脑中能用的端口中的第一个
     * @param b        波特率(baudrate)，如 9600
     * @param d        数据位（datebits），如 SerialPort.DATABITS_8 = 8
     * @param s        停止位（stopbits），如 SerialPort.STOPBITS_1 = 1
     * @param p        校验位 (parity)，如 SerialPort.PARITY_NONE = 0
     * @return 打开的串口对象，打开失败时，返回 null
     */
    public static SerialPort openComPort(String portName, int b, int d, int s, int p) {
        CommPort commPort = null;
        try {
            //当没有传入可用的 com 口时，默认使用电脑中可用的 com 口中的第一个
            if (portName == null || "".equals(portName)) {
                List<String> comPortList = findSystemAllComPort();
                if (comPortList.size() > 0) {
                    portName = comPortList.get(0);
                }
            }

            //通过端口名称识别指定 COM 端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

            /**
             * open(String TheOwner, int i)：打开端口
             * TheOwner 自定义一个端口名称，随便自定义即可
             * i：打开的端口的超时时间，单位毫秒，超时则抛出异常：PortInUseException if in use.
             * 如果此时串口已经被占用，则抛出异常：gnu.io.PortInUseException: Unknown Application
             */
            commPort = portIdentifier.open(portName, 5000);

            /**
             * 判断端口是不是串口
             * public abstract class SerialPort extends CommPort
             */
            if (commPort instanceof SerialPort serialPort) {
                /**
                 * 设置串口参数：setSerialPortParams( int b, int d, int s, int p )
                 * b：波特率（baudrate）
                 * d：数据位（datebits），SerialPort 支持 5,6,7,8
                 * s：停止位（stopbits），SerialPort 支持 1,2,3
                 * p：校验位 (parity)，SerialPort 支持 0,1,2,3,4
                 * 如果参数设置错误，则抛出异常：gnu.io.UnsupportedCommOperationException: Invalid Parameter
                 * 此时必须关闭串口，否则下次 portIdentifier.open 时会打不开串口，因为已经被占用
                 */
                serialPort.setSerialPortParams(b, d, s, p);
                return serialPort;
            }
        } catch (NoSuchPortException | PortInUseException e) {
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
            //此时必须关闭串口，否则下次 portIdentifier.open 时会打不开串口，因为已经被占用
            commPort.close();
        }
        return null;
    }

    /**
     * 往串口发送数据
     *
     * @param serialPort 串口对象
     * @param orders      待发送数据
     */
    public static void sendDataToComPort(SerialPort serialPort, byte[] orders) {
        OutputStream outputStream = null;
        try {
            if (serialPort != null) {
                outputStream = serialPort.getOutputStream();
                outputStream.write(orders);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**

     * 读取数据

     *

     * @return 字节ArrayList

     */

    public byte[] readFromPort(InputStream inStream) {

        byte[] bytes = null;
        try {
            while (true) {
                //获取buffer里的数据长度
                int bufflenth = inStream.available();
                if (0 == bufflenth) {
                    break;
                }
                bytes = new byte[bufflenth];
                inStream.read(bytes);
                for (byte b : bytes) {
                    System.out.print(Byte.toUnsignedInt(b));
                }
                System.out.println();
            }

        } catch (IOException ignored) {

        }

        return bytes;

    }

    /**
     * 关闭串口
     *
     * @param serialPort 待关闭的串口对象
     */
    public static void closeComPort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
        }
    }

    /**
     * 16进制字符串转十进制字节数组
     * 这是常用的方法，如某些硬件的通信指令就是提供的16进制字符串，发送时需要转为字节数组再进行发送
     *
     * @param strSource 16进制字符串，如 "455A432F5600"，每两位对应字节数组中的一个10进制元素
     *                  默认会去除参数字符串中的空格，所以参数 "45 5A 43 2F 56 00" 也是可以的
     * @return 十进制字节数组, 如 [69, 90, 67, 47, 86, 0]
     */
    public static byte[] hexString2Bytes(String strSource) {
        if (strSource == null || "".equals(strSource.trim())) {
            System.out.println("hexString2Bytes 参数为空，放弃转换.");
            return null;
        }
        strSource = strSource.replace(" ", "");
        int l = strSource.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(strSource.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    public static void ReadUSBData(BaseReadUSBData event) {
        if (Math.abs(degData - (int) degData) <= 0.1) {
            event.ReadUSBData(-1, -1, -1);
        } else {
            event.ReadUSBData((int) degData, cylinderData, endFaceData);
        }
    }

    public static void AnalogReceivedData() {
        new Thread(() -> {
            while (true) {
                for (double i = 0; i <= 360; i += 0.05) {
                    try {
                        Thread.sleep(5);
                        double data1 = (scale * Math.sin(Math.toRadians(i) + (Math.random() * scale / 8 - scale / 16)));
                        double data2 = (scale * Math.sin(Math.toRadians(i) + (Math.random() * scale / 8 - scale / 16)));
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

    public static void Rotate(boolean isRight) {
        scale = scale + (isRight ? 0.1F : -0.1F);
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

}
