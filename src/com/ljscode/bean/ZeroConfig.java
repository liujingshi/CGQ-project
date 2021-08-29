package com.ljscode.bean;

public class ZeroConfig extends BaseBean {

    private double cylinder;
    private double endFace;

    public ZeroConfig() {
    }

    public ZeroConfig(double cylinder, double endFace) {
        this.cylinder = cylinder;
        this.endFace = endFace;
    }

    public double getCylinder() {
        return cylinder;
    }

    public void setCylinder(double cylinder) {
        this.cylinder = cylinder;
    }

    public double getEndFace() {
        return endFace;
    }

    public void setEndFace(double endFace) {
        this.endFace = endFace;
    }
}
