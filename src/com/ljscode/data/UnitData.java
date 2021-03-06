package com.ljscode.data;

import java.util.List;

/**
 * 一个数据
 */
public class UnitData {

    /**
     * 角度
     */
    private double deg;
    /**
     * 柱面数据
     */
    private double cylinder;
    /**
     * 端面数据
     */
    private double endFace;

    public UnitData() {
    }

    public UnitData(double deg, double cylinder, double endFace) {
        this.deg = deg;
        this.cylinder = cylinder;
        this.endFace = endFace;
    }

    public static UnitData FindByDeg(List<UnitData> dataset, double deg) {
        UnitData result = null;
        try {
            for (UnitData data : dataset) {
                if (Math.abs(data.getDeg() - deg) <= 0.0001) {
                    result = data;
                }
            }
        } catch (Exception e) {
            if (dataset.size() > 0) {
                result = dataset.get(0);
            } else {
                result = new UnitData(deg, 0, 0);
            }
        }
        return result;
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

    public UnitData cloneMe() throws CloneNotSupportedException {
        return (UnitData) super.clone();
    }
}
