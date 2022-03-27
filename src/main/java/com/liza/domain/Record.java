package com.liza.domain;

import java.sql.Timestamp;

public class Record {
    private int id;
    private String city ;
    private String country;
    private Timestamp time;
    //TODO discuss type and format, degree (C or K)
    private double temperature;

    public Record(int id, String city, String country, Timestamp time, double temperature) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.time = time;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getTemperature() {
        return temperature;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Temperature is " + getTemperature() + " at time " + getTime();
    }
}
