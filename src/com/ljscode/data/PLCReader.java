package com.ljscode.data;

import java.util.ArrayList;
import java.util.List;

public class PLCReader {

    private double startTime; // 开始时间戳
    private final int limitBit; // 限制位数
    private final char splitSymbol; // 分隔符
    private final List<Character> cacheQueue; // 缓存队列
    private final DataCompound dataCompound; // 数据合成者
    private boolean isStart; // 是否开始
    private double timestamp; // 时间戳
    private double value; // 值

    public PLCReader(DataCompound dataCompound, int limitBit, char splitSymbol) {
        this.dataCompound = dataCompound;
        this.limitBit = limitBit;
        this.splitSymbol = splitSymbol;
        cacheQueue = new ArrayList<>();
        isStart = false;
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
            cacheQueue.add(symbol);
            if (cacheQueue.size() >= limitBit) {
                if (symbol == splitSymbol) {
                    StringBuilder data = new StringBuilder();
                    for (Character item : cacheQueue) {
                        data.append(item);
                    }
                    cacheQueue.clear();
                    sendDataCompound(data.toString());
                } else {
                    isStart = false;
                    cacheQueue.clear();
                }
            }
        } else {
            if (symbol == splitSymbol) {
                isStart = true;
            }
        }
    }

    public void sendDataCompound(String data) {
        // 解析data 分解出 time deg
        String[] dataArr = data.split(",");
        if (dataArr.length == 2) {
            String value = dataArr[0].trim();
            String timestamp = dataArr[1].replace('\r', ' ').replace('\n', ' ').trim();
            double dTimestamp = Double.parseDouble(timestamp);
            double dValue = Double.parseDouble(value);
            this.timestamp = dTimestamp;
            this.value = dValue;
            // 发送给数据合成者
            if (startTime > 0) {
                dataCompound.send(dTimestamp - startTime, dValue);
            }
        }
    }

}
