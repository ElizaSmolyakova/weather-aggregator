package com.liza.services.fetchers.openWeather;

import com.liza.services.fetchers.WeatherResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class RecordService implements WeatherResource {

    //can be moved to property file
    private static final String WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}";
    private final String apiKey= "43a31d72b4cd0759874d230f0d92f8a9";

    public RecordService() {}

    public Double getWeather(String city, String country) {

        RestTemplate restTemplate = new RestTemplate();
        URI url = new UriTemplate(WEATHER_URL).expand(city, country, this.apiKey);
        ResponseEntity<Response> result = restTemplate.getForEntity(url, Response.class);

        //todo fix npe
        return result.getBody().getMain().getTemp();
    }

}
