package com.ljscode.bean;

public class RadiusConfig extends BaseBean {

    private double r;
    private double insideR;

    public RadiusConfig() {
    }

    public RadiusConfig(double r, double insideR) {
        this.r = r;
        this.insideR = insideR;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getInsideR() {
        return insideR;
    }

    public void setInsideR(double insideR) {
        this.insideR = insideR;
    }
}
