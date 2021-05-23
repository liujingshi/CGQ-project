package com.ljscode.bean;

public class PortConfig extends BaseBean {

    private String port;
    private int id;
    private String name;
    private String device;

    public PortConfig() {
    }

    public PortConfig(String port, int id, String name, String device) {
        this.port = port;
        this.id = id;
        this.name = name;
        this.device = device;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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
