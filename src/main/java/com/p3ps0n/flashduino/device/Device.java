package com.p3ps0n.flashduino.device;

public class Device {
    private String name;
    private String id;
    private String baudrate;
    private String programmer;

    public Device() {
    }

    public Device(String name, String id, String baudrate, String programmer) {
        this.name = name;
        this.id = id;
        this.baudrate = baudrate;
        this.programmer = programmer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(String baudrate) {
        this.baudrate = baudrate;
    }

    public String getProgrammer() {
        return programmer;
    }

    public void setProgrammer(String programmer) {
        this.programmer = programmer;
    }

    @Override
    public String toString() {
        return getName();
    }
}
