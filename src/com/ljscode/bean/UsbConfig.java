package com.ljscode.bean;

public class UsbConfig extends BaseBean {

    private String cylinder;
    private String endFace;
    private String bpx;
    private String deg;

    public UsbConfig() {
    }

    public UsbConfig(String cylinder, String endFace, String bpx, String deg) {
        this.cylinder = cylinder;
        this.endFace = endFace;
        this.bpx = bpx;
        this.deg = deg;
    }

    public String getCylinder() {
        return cylinder;
    }

    public void setCylinder(String cylinder) {
        this.cylinder = cylinder;
    }

    public String getEndFace() {
        return endFace;
    }

    public void setEndFace(String endFace) {
        this.endFace = endFace;
    }

    public String getBpx() {
        return bpx;
    }

    public void setBpx(String bpx) {
        this.bpx = bpx;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }
}
