package com.ljscode.base;

import com.ljscode.data.DataCompound;

public abstract class BaseSimulate {

    public static DataCompound dataCompound;
    public static double deg;
    public static double cylinder;
    public static double endFace;
    public static double time;
    public static boolean isRun;

    static {
        deg = 0;
        cylinder = 0;
        endFace = 0;
        time = 0;
        isRun = false;
    }

    public static void rotate() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    if (isRun) {
                        double d = deg + Math.random() * 0.5;
                        if (d > 360) {
                            deg = 0;
                        } else {
                            deg = d;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void start() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(40);
                    time += 40;
                    cylinder = 40 * Math.sin(Math.toRadians(deg) + (Math.random() * 1 - 0.5));
                    endFace = 30 * Math.sin(Math.toRadians(deg) + (Math.random() * 1 - 0.5));
                    dataCompound.send(time, deg);
                    dataCompound.send(time, cylinder, endFace);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        rotate();
    }
}