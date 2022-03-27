package com.liza.service;

import com.liza.LocationProperties;
import com.liza.dao.Weather;
import com.liza.dao.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class WeatherAggregator {
    @Autowired
    private LocationProperties props;

    @Autowired
    private List<WeatherResource> resources;

    @Autowired
    WeatherRepository repository;

    @Scheduled(cron = "0 * * * * *")
    public void calculateWeather() {
        props.getCountries().forEach(country -> {
            country.getCities().forEach(city -> {
                resources.stream()
                        .mapToDouble( resource -> resource.getWeather(city, country.getName()))
                        .average()
                .ifPresent(avg -> {
                    repository.save(new Weather(city, country.getName(), Timestamp.from(Instant.now()) , avg));
                });
            });
        });
    };
}
