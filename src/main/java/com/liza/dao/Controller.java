package com.liza.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    WeatherRepository weatherRepository;

    @GetMapping("/weathers")
    public ResponseEntity<List<Weather>> getCurrent(@RequestParam(required = true) String city, @RequestParam(required = true) String country, @RequestParam("date")
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            List<Weather> result = new ArrayList<Weather>();
            if (date == null)
                result.add(weatherRepository.findTop1ByCityAndCountryOrderByTimeDesc(city, country));
            else
                weatherRepository.getAllForDate(city, country, new SimpleDateFormat("yyyy-MM-dd").format(date)).forEach(result::add);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            //todo clean result, leave only temp and time
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
