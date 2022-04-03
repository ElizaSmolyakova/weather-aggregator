package com.liza.services.fetchers;

public interface WeatherResource {

    WeatherResponse getWeather(String city, String country);
}
