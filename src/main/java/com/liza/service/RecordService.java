package com.liza.service;

import com.liza.domain.Record;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.sql.Timestamp;

@Service //@Component
public class RecordService {

    private static final String WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}";
    private final String apiKey= "43a31d72b4cd0759874d230f0d92f8a9";
    private final String city = "London";
    private final String country = "GB";

    public RecordService() { }

    public Record getWeather() {
        System.out.println("Requesting current weather for " + this.country + this.city);

        RestTemplate restTemplate = new RestTemplate();
        URI url = new UriTemplate(WEATHER_URL).expand(this.city, this.country, this.apiKey);
        //TODO which time to use? Data from schedule?
        Timestamp now = new Timestamp(System.currentTimeMillis());
        ResponseEntity<Response> result = restTemplate.getForEntity(url, Response.class);

        //TODO which timestamp to use
        Record record = new Record(1, this.city, this.country, now, result.getBody().getMain().getTemp());
        return record;
    }

}
