package com.ljscode.data;

import com.ljscode.base.BaseUSBReader;
import com.ljscode.bean.UsbConfig;
import com.ljscode.util.ConfigUtil;
import com.ljscode.util.ParseSystemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BPXReader {

    private double startTime; // 开始时间戳
    private final List<Character> cacheQueue; // 缓存队列
    private final List<String> cacheResultQueue; // 缓存队列
    private final DataCompound dataCompound; // 数据合成者
    private boolean isStart; // 是否开始
    private boolean isStartAll; // 是否开始全部
    private double timestamp; // 时间戳
    private double cylinder; // 柱面值
    private double endFace; // 端面值

    public BPXReader(DataCompound dataCompound) {
        this.dataCompound = dataCompound;
        cacheQueue = new ArrayList<>();
        cacheResultQueue = new ArrayList<>();
        isStart = false;
        isStartAll = false;
        startTime = -1;
        timestamp = -1;
    }

    public boolean clearStartTime() {
        this.startTime = this.timestamp;
        return this.timestamp > 0;
    }

    // 发数据给我就是当前读到的字符
    public void send(char symbol) {
        if (isStart) {
            // r 结束
            if (symbol == 'r') {
                // 拿到一行
                StringBuilder data = new StringBuilder();
                for (Character item : cacheQueue) {
                    data.append(item);
                }
                cacheQueue.clear();
                cacheQueue.add(symbol);
                if (data.toString().split(" ")[0].split(",").length == 10) {
                    String local = data.toString().split(",")[4];
                    if (isStartAll) {
                        cacheResultQueue.add(data.toString().replace('\r', ' ').replace('\n', ' ').trim());
                        // 拿到四行 D 接口结束
                        if (local.equals("0x03")) {
                            sendDataCompound();
                        }
                    } else {
                        // D 接口开始
                        if (local.equals("0x03")) {
                            isStartAll = true;
                        }
                    }
                }
            } else {
                cacheQueue.add(symbol);
            }
        } else {
            // r 开始
            if (symbol == 'r') {
                isStart = true;
                cacheQueue.add(symbol);
            }
        }
    }

    public void sendDataCompound() {
        UsbConfig usbConfig = ConfigUtil.GetUsbConfig();
        double timestamp = 0;
        double cylinder = 0;
        double endFace = 0;
        Map<String, String> usbPortMapping = new HashMap<>();
        usbPortMapping.put("A", "0x00");
        usbPortMapping.put("B", "0x01");
        usbPortMapping.put("C", "0x02");
        usbPortMapping.put("D", "0x03");
        for (String nStr : cacheResultQueue) {
            String[] nArr = nStr.split(" ")[0].split(",");
            if (nArr.length != 10) {
                cacheResultQueue.clear();
                return;
            }
            timestamp = (double)Integer.parseInt(nArr[1].substring(2), 16);
            String portStr = nArr[4];
            String data0x = nArr[7].substring(2) + nArr[8].substring(2) + nArr[9].substring(2); // 补码16进制
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
        this.timestamp = timestamp;
        this.cylinder = cylinder;
        this.endFace = endFace;
        cacheResultQueue.clear();
        if (startTime > 0) {
            dataCompound.send(timestamp - startTime, cylinder, endFace);
        }
    }
}
