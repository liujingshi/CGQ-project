package com.ljscode.bean;

public class ComConfig extends BaseBean {

    private String com;
    private int id;
    private String name;
    private String device;

    public ComConfig() {
    }

    public ComConfig(String com, int id, String name, String device) {
        this.com = com;
        this.id = id;
        this.name = name;
        this.device = device;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
