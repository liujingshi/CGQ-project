package com.ljscode.base;

import Automation.BDaq.DaqException;
import Automation.BDaq.DeviceInformation;
import Automation.BDaq.EventCounterCtrl;
import com.ljscode.bean.UsbConfig;
import com.ljscode.data.*;
import com.ljscode.util.ConfigUtil;
import com.ljscode.util.ParseSystemUtil;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseUSBReader {

    private static final byte[] PLCOrder =  new byte[]{(byte)0x01, (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x04, (byte)0x44, (byte)0x09};
    private static final String[] BPXLinkOrder = new String[] { "h\r", "s,0\r", "c\r", "r,0x2000,5,0x24,0x00,0x00,0x00,0x28\r" };
    private static final String BPXOrder = "r,0,0x02,0x28,0xFF\r";
    private static final String BPXOrders = "r,0x2000,5,0x24,0xFF,0xFF,0x00,0x28\r";

    private static int degData;
    private static double cylinderData;
    private static double endFaceData;

    private static SerialPort portBPX = null;
    private static SerialPort portPLC = null;
    private static EventCounterCtrl eventCounterCtrl = null;

    private static Clock clock;

    private static DataCompound dataCompound;
    private static BPXReader bpxReader;
    private static PLCReader plcReader;
    private static DEGReader degReader;

    public static void ReadUSBData(BaseReadUSBData event) {
//        if (BaseUSBReader.portBPX != null && BaseUSBReader.portPLC != null && BaseUSBReader.degData != 0) {
//            event.ReadUSBData(BaseUSBReader.degData, BaseUSBReader.cylinderData, BaseUSBReader.endFaceData);
//        }
        if (dataCompound != null) {
            if (!dataCompound.isIn()) {
                dataCompound.setIn(true);
            }
            Double[] readValue = dataCompound.read();
            if (readValue != null) {
                event.ReadUSBData((int)Math.abs(readValue[0]), readValue[1], readValue[2]);
            }
        }
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
            case "USB5":
                plcPortStr = "COM5";
                break;
            case "USB6":
                plcPortStr = "COM6";
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
            case "USB5":
                bpxPortStr = "COM5";
                break;
            case "USB56":
                bpxPortStr = "COM6";
                break;
        }
        if (plcPortStr.equals("") || bpxPortStr.equals("")) {
            return false;
        }
        List<String> mCommList = BaseUSBListener.findPorts();
        if (mCommList.size() < 1) {
            System.out.println("??????????????????????????????");
            return false;
        } else {
            dataCompound = new DataCompound();
            clock = new Clock();
            clock.start();
            try {
//                BaseUSBReader.portPLC  = BaseUSBListener.openPort(plcPortStr, 9600);
                BaseUSBReader.portBPX  = BaseUSBListener.openPort(bpxPortStr, 9600);

                BaseUSBReader.eventCounterCtrl = new EventCounterCtrl();
                BaseUSBReader.eventCounterCtrl.setSelectedDevice(new DeviceInformation(""));
                BaseUSBReader.eventCounterCtrl.setChannelCount(1);
                BaseUSBReader.eventCounterCtrl.setChannelStart(0);
                BaseUSBReader.eventCounterCtrl.setEnabled(true);

                InitBPXListener();
//                InitPLCListener();
                InitDEGListener();
                for (String order : BaseUSBReader.BPXLinkOrder) {
                    BaseUSBListener.sendToPort(BaseUSBReader.portBPX, order.getBytes());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (PortInUseException | DaqException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void BpxStart() {
        BaseUSBListener.sendToPort(BaseUSBReader.portBPX, BPXOrders.getBytes());
        new Thread(() -> {
            try {
                Thread.sleep(1500);
//                while (!(plcReader.clearStartTime() && bpxReader.clearStartTime())) {}
                while (!(degReader.clearStartTime() && bpxReader.clearStartTime())) {}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void InitBPXListener() {
        bpxReader = new BPXReader(dataCompound);
        AtomicLong prevTime = new AtomicLong(System.currentTimeMillis());
        AtomicLong nowTime = new AtomicLong(System.currentTimeMillis());
        new Thread(() -> {
            while (true) {
                nowTime.set(System.currentTimeMillis());
                try {
                    Thread.sleep(200);
                    if (nowTime.get() - prevTime.get() > 3000) {
                        BpxStart();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        BaseUSBListener.addListener(BaseUSBReader.portBPX, () -> {
            prevTime.set(System.currentTimeMillis());
            try {
                byte[] data1 = BaseUSBListener.readFromPort(BaseUSBReader.portBPX);
//                StringBuffer rData = new StringBuffer();
                for (byte d : data1) {
                    if (Byte.toUnsignedInt(d) != 0) {
                        bpxReader.send((char) Byte.toUnsignedInt(d));
//                        rData.append((char) Byte.toUnsignedInt(d));
                    }
                }
//                AnalyzeBPXData(rData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void InitPLCListener() {
        plcReader = new PLCReader(dataCompound, 27, '\n');
        BaseUSBListener.addListener(BaseUSBReader.portPLC, () -> {
            try {
                byte[] data1 = BaseUSBListener.readFromPort(BaseUSBReader.portPLC);
                for (byte d : data1) {
                    if (Byte.toUnsignedInt(d) != 0) {
                        plcReader.send((char) Byte.toUnsignedInt(d));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void InitDEGListener() {
        degReader = new DEGReader(dataCompound, clock);

        USB4750Listener usb4750Listener = new USB4750Listener(eventCounterCtrl, degReader);
        // ?????????????????? send
        new Timer(2000, usb4750Listener).start();
    }

    public static boolean Link(boolean adc) {
        if (adc) {
            dataCompound = new DataCompound();
            BaseSimulate.dataCompound = dataCompound;
            BaseSimulate.start();
            BaseSimulate.isRun = true;
            return true;
        } else {
            return Link();
        }
    }


}

class USB4750Listener implements ActionListener {

    private final EventCounterCtrl eventCounterCtrl;
    private final DEGReader degReader;

    public USB4750Listener(EventCounterCtrl eventCounterCtrl, DEGReader degReader) {
        this.eventCounterCtrl = eventCounterCtrl;
        this.degReader = degReader;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] x = {0};
        eventCounterCtrl.Read(1, x);
        degReader.send(x[0]);
    }
}
