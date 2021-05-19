package com.ljscode.data;

public class UnitData {

    private int deg;
    private float cylinder;
    private float endFace;

    public UnitData() {
    }

    public UnitData(int deg, float cylinder, float endFace) {
        this.deg = deg;
        this.cylinder = cylinder;
        this.endFace = endFace;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public float getCylinder() {
        return cylinder;
    }

    public void setCylinder(float cylinder) {
        this.cylinder = cylinder;
    }

    public float getEndFace() {
        return endFace;
    }

    public void setEndFace(float endFace) {
        this.endFace = endFace;
    }

}
