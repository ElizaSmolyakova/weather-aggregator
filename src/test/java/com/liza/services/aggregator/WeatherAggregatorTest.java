package com.liza.services.aggregator;

import com.liza.LocationProperties;
import com.liza.dao.Weather;
import com.liza.dao.WeatherRepository;
import com.liza.services.fetchers.WeatherResource;
import com.liza.services.fetchers.WeatherResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherAggregatorTest {
    //test data
    //todo can we create location props for test
    LocationProperties props = new LocationProperties();

    //first weather resource mock
    WeatherResource resource1 = mock(WeatherResource.class);

    //second weather resource mock
    WeatherResource resource2 = mock(WeatherResource.class);

    List<WeatherResource> resources = Arrays.asList(resource1, resource2);

    //repository mock to check calculated result
    WeatherRepository repository = mock(WeatherRepository.class);


    @Before
    public void setUp() {
        props.setCountries(Arrays.asList(new LocationProperties.Country("RU", Arrays.asList("Moscow"))));
    }

    @Test
    public void shouldCalculateResult() {
        WeatherAggregator calculator = new WeatherAggregator(props, resources, repository);
        when(resource1.getWeather("Moscow","RU")).thenReturn(new WeatherResponse(HttpStatus.OK,23.5));
        when(resource2.getWeather("Moscow","RU")).thenReturn(new WeatherResponse(HttpStatus.OK,26.5));
        calculator.calculateWeather();
        verify(repository).save(new Weather("Moscow","RU", any(), 25.0 ));
    }

    @Test
    public void shouldCalculateWhenOneResourceIsOff() {
        WeatherAggregator calculator = new WeatherAggregator(props, resources, repository);
        when(resource1.getWeather("Moscow","RU")).thenReturn(new WeatherResponse(HttpStatus.OK,23.5));
        when(resource2.getWeather("Moscow","RU")).thenReturn(new WeatherResponse(HttpStatus.NO_CONTENT,null));
        calculator.calculateWeather();
        verify(repository).save(new Weather("Moscow","RU", any(), 23.5 ));

    }

    @Test
    public void shouldNotCalculateWhenAllResourcesAreOff() {
        WeatherAggregator calculator = new WeatherAggregator(props, resources, repository);
        when(resource1.getWeather("Moscow","RU")).thenReturn(new WeatherResponse(HttpStatus.NO_CONTENT,null));
        when(resource2.getWeather("Moscow","RU")).thenReturn(new WeatherResponse(HttpStatus.NO_CONTENT,null));
        calculator.calculateWeather();
        verify(repository, never()).save(new Weather("Moscow","RU", any(), 25.0 ));
    }
}
