package com.softblxgenesis.yuvti.SaveLocation;

public class model
{
    String name, city, state, country, pin, locality;


    public model(String name, String city, String state, String country, String pin, String locality) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pin = pin;
        this.locality = locality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}