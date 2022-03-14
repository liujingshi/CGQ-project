package com.ljscode.data;

import java.util.Date;

/**
 * 时钟类 记录运行时间
 */
public class Clock {

    // 开始的事件
    private Date startTime = null;

    public Clock() {}

    // 开始
    public void start() {
        startTime = new Date();
    }

    // 获取毫秒数
    public double get() {
        return ((new Date()).getTime() - startTime.getTime());
    }

}
