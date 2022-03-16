package com.ljscode.data;


public class DEGReader {

    private double startTime; // 开始时间戳
    private final Clock clock; // 缓存队列
    private final DataCompound dataCompound; // 数据合成者
    private double value; // 值
    private double timestamp; // 时间戳

    public DEGReader(DataCompound dataCompound, Clock clock) {
        this.clock = clock;
        this.dataCompound = dataCompound;
        startTime = -1;
        timestamp = -1;
    }

    public boolean clearStartTime() {
        this.startTime = this.timestamp;
        return this.timestamp > 0;
    }

    public void send(int data) {
        sendDataCompound(data);
    }

    public void sendDataCompound(int data) {
        timestamp = clock.get();
        data = data % 448512;
        value = (double)data * 360.0 / 448512.0;
        dataCompound.send(timestamp - startTime, value);
    }

}
