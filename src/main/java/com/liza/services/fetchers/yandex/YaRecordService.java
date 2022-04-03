package com.liza.services.fetchers.yandex;

import com.liza.services.fetchers.WeatherResource;
import com.liza.services.fetchers.WeatherResponse;
import com.liza.services.fetchers.openWeather.Response;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class YaRecordService implements WeatherResource {

    //can be moved to property file
    private static final String WEATHER_URL =
            "https://api.weather.yandex.ru/v2/informers?lat=55.75396&lon=37.620393";
            //"https://api.weather.yandex.ru/v2/informers?lat={lat}&lon={lon}}";
    private final String apiKey = "test";

    public YaRecordService() {
    }

    public WeatherResponse getWeather(String city, String country) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Yandex-API-Key", apiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>( headers);
        URI url = new UriTemplate(WEATHER_URL).expand();
        ResponseEntity<Response> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Response.class);

        if (result.getStatusCode() == HttpStatus.OK)
            return new WeatherResponse(HttpStatus.OK, result.getBody().getMain().getTemp());
        else
            return new WeatherResponse(HttpStatus.NO_CONTENT, null);


    }

}
