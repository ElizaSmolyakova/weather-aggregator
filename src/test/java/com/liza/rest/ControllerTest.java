package com.liza.rest;

import com.liza.dao.Weather;
import com.liza.dao.WeatherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    WeatherRepository repository = mock(WeatherRepository.class);
    Controller rest = new Controller(repository);

    //test data
    Weather weather1 = new Weather("Moscow", "RU", Timestamp.from(Instant.now()), 25.0);
    //test data
    Weather weather2 = new Weather("Moscow", "RU", Timestamp.from(Instant.now()), 27.0);

    @Test
    public void shouldReturnLatestData() {
        when(repository.findTop1ByCityAndCountryOrderByTimeDesc("Moscow", "RU")).thenReturn(weather1);
        ResponseEntity<List<Weather>> result = rest.getCurrent("Moscow", "RU", null);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().size(), 1);
        //todo override equals method for Weather
        assertEquals(result.getBody().get(0).getCity(), "Moscow");
        assertEquals(result.getBody().get(0).getCountry(), "RU");
        //todo compare in Double
        assertEquals(result.getBody().get(0).getTemperature(), 25.0, 0.01);
    }

    @Test
    public void shouldReturnAllDayData() {
        //todo check date format
        Date date =  new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        when(repository.getAllForDate("Moscow", "RU", formatter.format(date))).thenReturn(Arrays.asList(weather1, weather2));
        ResponseEntity<List<Weather>> result = rest.getCurrent("Moscow", "RU", date);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().size(), 2);
        assertEquals(result.getBody().get(0).getCity(), "Moscow");
        assertEquals(result.getBody().get(0).getCountry(), "RU");
        assertEquals(result.getBody().get(0).getTemperature(), 25.0, 0.01);

        assertEquals(result.getBody().get(1).getCity(), "Moscow");
        assertEquals(result.getBody().get(1).getCountry(), "RU");
        assertEquals(result.getBody().get(1).getTemperature(), 27.0, 0.01);
    }

    @Test
    public void shouldReturnNoContent() {
        //todo check date format
        Date date =  new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        ResponseEntity<List<Weather>> result = rest.getCurrent("Moscow", "RU", date);
        assertEquals(result.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldReturnErrorWhenException() {
        when(repository.findTop1ByCityAndCountryOrderByTimeDesc("Moscow", "RU")).thenThrow(new RuntimeException("test exception"));
        ResponseEntity<List<Weather>> result = rest.getCurrent("Moscow", "RU", null);
        assertEquals(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
