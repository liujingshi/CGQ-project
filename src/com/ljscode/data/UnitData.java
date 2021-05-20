package com.ljscode.data;

import java.util.List;

public class UnitData {

    private double deg;
    private double cylinder;
    private double endFace;

    public UnitData() {
    }

    public UnitData(double deg, double cylinder, double endFace) {
        this.deg = deg;
        this.cylinder = cylinder;
        this.endFace = endFace;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
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

    public static UnitData FindByDeg(List<UnitData> dataset, double deg) {
        UnitData result = null;
        for (UnitData data : dataset) {
            if (Math.abs(data.deg - deg) <= 0.0001) {
                result = data;
            }
        }
        return result;
    }
}
