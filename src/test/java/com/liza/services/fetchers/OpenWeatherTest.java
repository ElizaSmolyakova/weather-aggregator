package com.liza.services.fetchers;

import com.liza.services.fetchers.openWeather.RecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherTest {
    @Mock
    RestTemplate restTemplate;

    @Autowired
    RecordService openWeather;

    //test json, can be moved in separate file

//    @Before
//   public void init(){
//        Mockito.when(restTemplate.getForEntity(any(), Response.class)).thenReturn(new ResponseEntity<Response>(
//                , HttpStatus.OK
//        ));
//    }

    @Test
    public void readWeatherFromJson() {
        openWeather.getWeather("Moscow", "RU");

    }
}
