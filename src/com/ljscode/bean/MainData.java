package com.ljscode.bean;

import java.util.Date;

public class MainData extends BaseBean {

    private String id;
    private String name;
    private Date time;

    public MainData() {
    }

    public MainData(String id, String name, Date time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
