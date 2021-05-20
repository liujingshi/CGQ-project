package com.ljscode.base;

import javax.usb.*;
import java.util.List;

public class BaseUSBListener {

    private static final short VENDOR_ID = 0x03eb;
    private static final short PRODUCT_ID = 0x2018;
    private static UsbPipe pipe81, pipe01;

    private String cylinderCOM;
    private String endFaceCOM;
    private String degCOM;

    public UsbDevice findDevice(UsbHub hub, short vendorId, short productId)
    {
        for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices())
        {
            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
            if (desc.idVendor() == vendorId && desc.idProduct() == productId) return device;
            if (device.isUsbHub())
            {
                device = findDevice((UsbHub) device, vendorId, productId);
                if (device != null) return device;
            }
        }
        return null;
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
                if (desc.idVendor() == VENDOR_ID
                        && desc.idProduct() == PRODUCT_ID) {
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
                    UsbInterface iFace = configuration.getUsbInterface((byte) 0);
                    if (iFace.isClaimed()){
                        iFace.release();
                    }
                    iFace.claim((usbInterface -> true));
                    UsbEndpoint receivedUsbEndpoint;
                    UsbEndpoint sendUsbEndpoint = (UsbEndpoint)iFace.getUsbEndpoints().get(0);
                    if (!sendUsbEndpoint.getUsbEndpointDescriptor().toString().contains("OUT")) {
                        receivedUsbEndpoint = sendUsbEndpoint;
                        sendUsbEndpoint = (UsbEndpoint)iFace.getUsbEndpoints().get(0);
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
