package com.ljscode.bean;

public class RangeConfig extends BaseBean {

    /**
     * 柱面理论期间开始
     */
    private double cylinderStart;
    /**
     * 柱面理论期间结束
     */
    private double cylinderEnd;
    /**
     * 端面理论期间开始
     */
    private double endFaceStart;
    /**
     * 端面理论期间结束
     */
    private double endFaceEnd;
    /**
     * 圆度理论区间开始
     */
    private double roundnessStart;
    /**
     * 圆度理论区间结束
     */
    private double roundnessEnd;
    /**
     * 平面度理论区间开始
     */
    private double flatnessStart;
    /**
     * 平面度理论区间结束
     */
    private double flatnessEnd;
    /**
     * 同轴度理论区间开始
     */
    private double axisFromStart;
    /**
     * 同轴度理论区间结束
     */
    private double axisFromEnd;
    /**
     * 平行度理论区间开始
     */
    private double parallelismStart;
    /**
     * 平行度理论区间结束
     */
    private double parallelismEnd;

    public RangeConfig() {
    }

    public RangeConfig(double cylinderStart, double cylinderEnd, double endFaceStart, double endFaceEnd, double roundnessStart, double roundnessEnd, double flatnessStart, double flatnessEnd, double axisFromStart, double axisFromEnd, double parallelismStart, double parallelismEnd) {
        this.cylinderStart = cylinderStart;
        this.cylinderEnd = cylinderEnd;
        this.endFaceStart = endFaceStart;
        this.endFaceEnd = endFaceEnd;
        this.roundnessStart = roundnessStart;
        this.roundnessEnd = roundnessEnd;
        this.flatnessStart = flatnessStart;
        this.flatnessEnd = flatnessEnd;
        this.axisFromStart = axisFromStart;
        this.axisFromEnd = axisFromEnd;
        this.parallelismStart = parallelismStart;
        this.parallelismEnd = parallelismEnd;
    }

    public double getCylinderStart() {
        return cylinderStart;
    }

    public void setCylinderStart(double cylinderStart) {
        this.cylinderStart = cylinderStart;
    }

    public double getCylinderEnd() {
        return cylinderEnd;
    }

    public void setCylinderEnd(double cylinderEnd) {
        this.cylinderEnd = cylinderEnd;
    }

    public double getEndFaceStart() {
        return endFaceStart;
    }

    public void setEndFaceStart(double endFaceStart) {
        this.endFaceStart = endFaceStart;
    }

    public double getEndFaceEnd() {
        return endFaceEnd;
    }

    public void setEndFaceEnd(double endFaceEnd) {
        this.endFaceEnd = endFaceEnd;
    }

    public double getRoundnessStart() {
        return roundnessStart;
    }

    public void setRoundnessStart(double roundnessStart) {
        this.roundnessStart = roundnessStart;
    }

    public double getRoundnessEnd() {
        return roundnessEnd;
    }

    public void setRoundnessEnd(double roundnessEnd) {
        this.roundnessEnd = roundnessEnd;
    }

    public double getFlatnessStart() {
        return flatnessStart;
    }

    public void setFlatnessStart(double flatnessStart) {
        this.flatnessStart = flatnessStart;
    }

    public double getFlatnessEnd() {
        return flatnessEnd;
    }

    public void setFlatnessEnd(double flatnessEnd) {
        this.flatnessEnd = flatnessEnd;
    }

    public double getAxisFromStart() {
        return axisFromStart;
    }

    public void setAxisFromStart(double axisFromStart) {
        this.axisFromStart = axisFromStart;
    }

    public double getAxisFromEnd() {
        return axisFromEnd;
    }

    public void setAxisFromEnd(double axisFromEnd) {
        this.axisFromEnd = axisFromEnd;
    }

    public double getParallelismStart() {
        return parallelismStart;
    }

    public void setParallelismStart(double parallelismStart) {
        this.parallelismStart = parallelismStart;
    }

    public double getParallelismEnd() {
        return parallelismEnd;
    }

    public void setParallelismEnd(double parallelismEnd) {
        this.parallelismEnd = parallelismEnd;
    }
}
