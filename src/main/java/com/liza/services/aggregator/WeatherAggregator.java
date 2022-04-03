package com.liza.services.aggregator;

import com.liza.LocationProperties;
import com.liza.dao.Weather;
import com.liza.dao.WeatherRepository;
import com.liza.services.fetchers.WeatherResource;
import com.liza.services.fetchers.WeatherResponse;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class WeatherAggregator {

    //    @Autowired
    private LocationProperties props;

    //    @Autowired
    private List<WeatherResource> resources;

    //    @Autowired
    WeatherRepository repository;

    public WeatherAggregator(LocationProperties props, List<WeatherResource> resources, WeatherRepository repository) {
        this.props = props;
        this.resources = resources;
        this.repository = repository;
    }

    @Scheduled(cron = "${cron.expression}")
    public void calculateWeather() {
        props.getCountries().forEach(country -> {
            country.getCities().forEach(city -> {
                resources.stream()
                        .map(resource -> resource.getWeather(city, country.getName()))
                        .filter(weather -> weather.getStatus() == HttpStatus.OK)
                        .mapToDouble(WeatherResponse::getValue)
                        .average()
                        .ifPresent(avg -> {
                            repository.save(new Weather(city, country.getName(), Timestamp.from(Instant.now()), avg));
                        });
            });
        });
    }
}
