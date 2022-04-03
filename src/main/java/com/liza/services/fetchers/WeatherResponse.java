package com.liza.services.fetchers;

import org.springframework.http.HttpStatus;

public class WeatherResponse  {
    HttpStatus status;
    Double value;

    public WeatherResponse(HttpStatus status, Double value) {
        this.status = status;
        this.value = value;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Double getValue() {
        return value;
    }
}
