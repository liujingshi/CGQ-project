package com.ljscode.main;

import com.ljscode.base.BaseUSBListener;
import com.ljscode.frame.MainFrame;

import javax.usb.*;

/**
 * 程序入口
 */
public class Main {

    /**
     * 程序入口
     * @param args 参数
     */
    public static void main(String[] args) {
//        MainFrame mainFrame = new MainFrame(); // 创建主窗体对象
//        mainFrame.showMe(); // 显示主窗体
        try {
            UsbDevice device = BaseUSBListener.findMissileLauncher(UsbHostManager.getUsbServices().getRootUsbHub());
            if (device != null) {
                UsbConfiguration configuration = device.getActiveUsbConfiguration();
                if (configuration.getUsbInterfaces().size() > 0) {
                    UsbInterface iFace = configuration.getUsbInterface((byte) 1);
                    if (iFace.isClaimed()){
                        iFace.release();
                    }
                    iFace.claim((usbInterface -> true));
                    UsbEndpoint receivedUsbEndpoint;
                    UsbEndpoint sendUsbEndpoint = (UsbEndpoint)iFace.getUsbEndpoints().get(0);
                    if (!sendUsbEndpoint.getUsbEndpointDescriptor().toString().contains("OUT")) {
                        receivedUsbEndpoint = sendUsbEndpoint;
                        sendUsbEndpoint = (UsbEndpoint)iFace.getUsbEndpoints().get(1);
                    } else {
                        receivedUsbEndpoint = (UsbEndpoint)iFace.getUsbEndpoints().get(1);
                    }
                    //发送：
                    UsbPipe sendUsbPipe = sendUsbEndpoint.getUsbPipe();
                    //打开发送通道
                    if (sendUsbPipe.isOpen()){
                        sendUsbPipe.close();
                    }
                    sendUsbPipe.open();
                    //接收
                    final UsbPipe receivedUsbPipe =  receivedUsbEndpoint.getUsbPipe();
                    //打开接收通道
                    if (!receivedUsbPipe.isOpen()){
                        receivedUsbPipe.open();
                    }
                    new Thread(() -> {
                        byte[] b = new byte[64];
                        try {
                            final UsbIrp irp = receivedUsbPipe.asyncSubmit(b);
                            for (int i = 0; i < irp.getActualLength(); i++) {
                                System.out.print(Byte.toUnsignedInt(b[i])+" ");
                            }
                        } catch (UsbException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        } catch (UsbException e) {
            e.printStackTrace();
        }
    }
}
