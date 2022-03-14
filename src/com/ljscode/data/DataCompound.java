package com.ljscode.data;

import java.util.LinkedList;
import java.util.Queue;

public class DataCompound {

    private final Queue<Double[]> dataQueue; // deg cylinder endFace
//    private final Queue<Double[]> plcQueue; // time deg
    private final Queue<Double[]> bpxQueue; // time cylinder endFace
    private final Queue<Double[]> degQueue; // time deg
    private boolean isIn;

    public DataCompound() {
        isIn = false;
        dataQueue = new LinkedList<>();
//        plcQueue = new LinkedList<>();
        bpxQueue = new LinkedList<>();
        degQueue = new LinkedList<>();
        compound();
    }

    public void send(double time, double deg) {
//        plcQueue.offer(new Double[]{time, deg});
        degQueue.offer(new Double[]{time, deg});
    }

    public void send(double time, double cylinder, double endFace) {
        bpxQueue.offer(new Double[]{time, cylinder, endFace});
    }

    // 开始合成数据
    public void compound() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5);
                    if (degQueue.size() > 10 && bpxQueue.size() > 10) { // 当两个队列里的元素都超过10个的时候
                        Double plcTime = degQueue.peek()[0];
                        Double bpxTime = bpxQueue.peek()[0];
                        if (Math.abs(plcTime - bpxTime) < 10) { // 比较两个时间戳 相差在10ms内就算做同一时刻
                            Double[] plcData = degQueue.poll();
                            Double[] bpxData = bpxQueue.poll();
                            if (plcData != null && bpxData != null && isIn) {
                                dataQueue.offer(new Double[]{plcData[1], bpxData[1], bpxData[2]});
                            }
                        } else if (plcTime > bpxTime) { // 不在同一时刻的情况下 删掉小的那一个
                            bpxQueue.poll();
                        } else {
                            degQueue.poll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 读取一个数据
    public Double[] read() {
        return dataQueue.poll();
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        isIn = in;
    }
}
