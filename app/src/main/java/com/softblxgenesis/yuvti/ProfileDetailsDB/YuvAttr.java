package com.softblxgenesis.yuvti.ProfileDetailsDB;

public class YuvAttr {
    int id;
    String name;
    int pin;
    String message;

    public YuvAttr(int i, String string, int parseInt, String cursorString){
        this.id = i;
        this.name = string;
        this.pin = parseInt;
        this.message = cursorString;
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

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}