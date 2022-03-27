package com.liza.dao;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "weather")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "time")
    private Timestamp time;
    @Column(name = "temperature")
    private double temperature;

    public Weather() {
    }

    public Weather(String city, String country, Timestamp time, double temperature) {
        this.city = city;
        this.country = country;
        this.time = time;
        this.temperature = temperature;
    }

    public long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Timestamp getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Weather in " + city + "/" + country + " is " + temperature + " at " + time;
    }
}